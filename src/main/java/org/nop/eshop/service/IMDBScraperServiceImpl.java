package org.nop.eshop.service;

import org.jboss.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.nop.eshop.dao.*;
import org.nop.eshop.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class IMDBScraperServiceImpl implements IMDBScraperService {
    private static Logger LOG = Logger.getLogger(IMDBScraperServiceImpl.class);


    private static final String IMDB_SEARCH = "http://www.imdb.com/search/title?year=2013,2013&start=%START%&title_type=feature&sort=moviemeter,asc";
    private static final String START_ITEM = "%START%";
    private static final String IMDB_HOME = "http://www.imdb.com";
    private static final SimpleDateFormat IMDB_SDF = new SimpleDateFormat("yyyy-MM-dd");

    public static final String MOVIE_IMG_HOME = "/var/www/eshop/img/movies/";
    public static final String PERSON_IMG_HOME = "/var/www/eshop/img/persons/";
    public static final String USER_IMG_HOME = "/var/www/eshop/img/users/";

    @Autowired
    private MovieDAO movieDAO;

    @Autowired
    private CommentDAO commentDAO;

    @Autowired
    private CountryDAO countryDAO;

    @Autowired
    private GenreDAO genreDAO;

    @Autowired
    private PersonDAO personDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private RoleDAO roleDAO;

    @Autowired
    private ImageService imageService;

    private Movie scrapBasicMovieInfo(Element e) {
        Movie movie = new Movie();

        //Title
        Element title = e.getElementsByTag("a").get(0);
        movie.setTitle(title.text().trim());

        //IMDB Id
        movie.setImdbId(title.attr("href").replaceAll("\\D", ""));

        //Rating
        Float rating = Float.valueOf(e.select("span.rating-rating>span.value").text());
        movie.setRating(rating);

        //Genres
        Elements gg = e.select("span.genre>a");
        Set<Genre> genres = new HashSet<>();
        for (Element ee: gg) {
            String genreName = ee.text().trim();
            Genre g = genreDAO.getByTitle(genreName);
            if (g == null) {
                g = new Genre(genreName);
                genreDAO.save(g);
            }
            genres.add(g);
        }
        movie.setGenres(genres);

        //Age Category
        String age = e.select("span.certificate>span").attr("title");
        if (age == null) age = "NONE";
        movie.setAgeCategory(age);

        //Duration
        Integer runtime = Integer.valueOf(e.select("span.runtime").text().replaceAll("\\D", ""));
        movie.setDuration(runtime);

        return movie;
    }

    private String imdbMovieURL(String Iid) {
        return "http://www.imdb.com/title/tt" + Iid + "/";
    }

    private String imdbMovieCommentsURL(String id) {
        return imdbMovieURL(id) + "reviews";
    }

    //@Scheduled(fixedDelay = 3600000L)
    @Transactional
    public synchronized void execute () throws IOException, ParseException {
        for (int i = 0; i < 3; i++) {
            Document pageI = getIMDBDocument(IMDB_SEARCH.replace(START_ITEM, String.valueOf(50 * i + 1)));
            Elements searchResults = pageI.select("tr.detailed>td.title");
            for (Element e: searchResults) {
                scrapMovie(e);
            }
        }
        LOG.info("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^Scraping done!^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
    }

    public void scrapMovie(Element e) {
        try {
            Movie basicMovie = scrapBasicMovieInfo(e);
            if (movieDAO.getByIMDBId(basicMovie.getImdbId()) != null) {
                return;
            }
            Movie fullMovie = scrapComments(scrapFullInfo(basicMovie));
            movieDAO.save(fullMovie);
            LOG.info("Movie [" + fullMovie.getTitle() + "] has been successfully saved.");
        } catch (Exception ex) {
            LOG.error("Error getting movie", ex);
        }
    }

    private Movie scrapComments(Movie movie) throws IOException {
        movieDAO.save(movie);
        Document commentsPage = getIMDBDocument(imdbMovieCommentsURL(movie.getImdbId()));

        Elements eComments = commentsPage.select("div#tn15content>div:not(.yn)");
        if (eComments.isEmpty())
            return movie;

        Role role_user = roleDAO.getRole(2);
        int size = eComments.size();
        if (size > 3)
            size = 3;
        for (int i = 0; i < size; i++) {
            Element commentHeader = eComments.get(i);

            //User
            Elements userLinks = commentHeader.select("a[href^=/user/");
            String username = userLinks.get(1).text().trim();
            User u = userDAO.getUser(username + "@eshop.org");
            if (u == null) {
                //new User
                u = new User();
                u.setEmail(username + "@eshop.org");
                u.getRoles().add(role_user);
                u.setPassword(username);
                u.setFullname(username);

                String userPic = userLinks.select("img").attr("src");
                Image image = new Image(ImageService.IMAGE_TYPE_PRIMARY);
                u.getImages().add(image);
                userDAO.createUser(u);
                imageService.upload(userPic, DEFAULT_CONNECTION_TIMEOUT, ImageService.ENTITY_USER, image);
            }

            //Comment
            Comment c = new Comment();
            String title = "";
            if (!commentHeader.select("h2").isEmpty()) {
                title = commentHeader.select("h2").get(0).text();
            }
            c.setTitle(title);

            Element commentTxt = commentHeader.nextElementSibling();
            c.setText(commentTxt.html());

            c.setUser(u);

            c.setMovie(movie);

            movie.getComments().add(c);
            commentDAO.save(c);
        }

        return movie;
    }

    private Movie scrapFullInfo(Movie basicMovie) throws IOException, ParseException {
        String titleURL = imdbMovieURL(basicMovie.getImdbId());
        Document moviePage = getIMDBDocument(titleURL);

        //Year .replaceAll("\\D", "");
        Date releaseDate = IMDB_SDF.parse(moviePage.select("meta[itemprop=datePublished]").attr("content"));
        basicMovie.setReleaseDate(releaseDate);

        String imgURL = moviePage.select("#img_primary img").attr("src");
        //#####################
        Map<String, Image> imagesToUpload = new HashMap<>();
        Image image = new Image(ImageService.IMAGE_TYPE_PRIMARY);
        imagesToUpload.put(imgURL, image);

        Elements carouselImgs = moviePage.select("div.mediastrip a[itemprop=thumbnailUrl]");
        for (Element currImage: carouselImgs) {
            Document currImagePage = getIMDBDocument(IMDB_HOME+currImage.attr("href"));
            if (currImagePage == null)
                continue;
            Elements bi = currImagePage.select("#primary-img");
            if ( bi != null && bi.size() > 0) {
                String nimgURL = bi.get(0).attr("src");
                Image nimage = new Image(ImageService.IMAGE_TYPE_CAROUSEL);
                imagesToUpload.put(nimgURL, nimage);
            }
        }
        basicMovie.getImages().addAll(imagesToUpload.values());
        movieDAO.save(basicMovie);
        imageService.upload(imagesToUpload, DEFAULT_CONNECTION_TIMEOUT, ImageService.ENTITY_MOVIE);
        //#####################

        Elements countries = moviePage.select("a[itemprop=url][href^=/country/]");
        if (!countries.isEmpty()) {
            Set<Country> countrySet = new HashSet<>();
            for (Element a: countries) {
                String title = a.text().trim();
                Country c = countryDAO.getByTitle(title);
                if (c == null) {
                    c = new Country(title);
                    countryDAO.save(c);
                }
                countrySet.add(c);
            }
            basicMovie.setCountries(countrySet);
        }

        String description = moviePage.select("#titleStoryLine>div[itemprop=description]").get(0).text();
        basicMovie.setDescription(description);

        Set<MoviePerson> mpc = new HashSet<>();

        Elements directors = moviePage.select("div.txt-block[itemprop=director]>a");
        for (Element dir: directors) {
            String personURL = IMDB_HOME + dir.attr("href");
            if (personURL.contains("fullcredits"))
                continue;
            String personIid = personURL.replaceAll("\\D", "");
            Person p = personDAO.getByIMDBId(personIid);
            if (p == null) {
                p = loadPerson(personURL);
                personDAO.save(p);
            }
            MoviePerson mp = new MoviePerson();
            mp.setPerson(p);
            mp.setCareer("DIRECTOR");
            mp.setMovie(basicMovie);
            mpc.add(mp);
        }

        Elements creators = moviePage.select("div.txt-block[itemprop=creator]>a");
        for (Element dir: creators) {
            String personURL = IMDB_HOME + dir.attr("href");
            String personIid = personURL.replaceAll("\\D", "");
            if (personURL.contains("fullcredits"))
                continue;
            Person p = personDAO.getByIMDBId(personIid);
            if (p == null) {
                p = loadPerson(personURL);
                personDAO.save(p);
            }
            MoviePerson mp = new MoviePerson();
            mp.setPerson(p);
            mp.setCareer("WRITER");
            mp.setMovie(basicMovie);
            mpc.add(mp);
        }

        Elements actors = moviePage.select("div.txt-block[itemprop=actors]>a");
        for (Element dir: actors) {
            String personURL = IMDB_HOME + dir.attr("href");
            if (personURL.contains("fullcredits"))
                continue;
            String personIid = personURL.replaceAll("\\D", "");
            Person p = personDAO.getByIMDBId(personIid);
            if (p == null) {
                p = loadPerson(personURL);
                personDAO.save(p);
            }
            MoviePerson mp = new MoviePerson();
            mp.setPerson(p);
            mp.setCareer("ACTOR");
            mp.setMovie(basicMovie);
            mpc.add(mp);
        }


        basicMovie.setPersons(mpc);
        return basicMovie;
    }

    private Document getIMDBDocument(String URL) {
        Document result = null;
        try {
            result = Jsoup.connect(URL).timeout(DEFAULT_CONNECTION_TIMEOUT).get();
        } catch (Exception e) {
            LOG.error("Error getting URL + " + URL);
            return null;

        }
        return result;
    }

    public Person loadPerson(String personURL) throws IOException, ParseException {
        Document person = getIMDBDocument(personURL);
        Person p = new Person();
        String fullname = person.select("span.itemprop[itemprop=name]").text().trim();
        p.setFullname(fullname);
        p.setImdbId(personURL.replaceAll("\\D", ""));

        Elements bi = person.select("div#name-born-info");
        Element bornInfo = null;
        if ((bi != null) && (bi.size() > 0)) {
            bornInfo = person.select("div#name-born-info").get(0);
            if (!bornInfo.select("time[itemprop=birthDate]").isEmpty()) {
                Element time = bornInfo.select("time[itemprop=birthDate]").get(0);
                String dateStr = time.attr("datetime");
                Date birthDate = IMDB_SDF.parse(dateStr);
                p.setBirthdate(birthDate);
            }
        }

        Map<String, Image> imagesToUpload = new HashMap<>();
        bi = person.select("td#img_primary img");
        if ( bi != null && bi.size() > 0) {
            String imgURL = bi.get(0).attr("src");
            Image image = new Image(ImageService.IMAGE_TYPE_PRIMARY);
            imagesToUpload.put(imgURL, image);
        }

        Elements carouselImgs = person.select("div.mediastrip a[itemprop=thumbnailUrl]");
        for (Element currImage: carouselImgs) {
            //primary-img
            Document currImagePage = getIMDBDocument(IMDB_HOME+currImage.attr("href"));
            if (currImagePage == null)
                continue;
            bi = currImagePage.select("#primary-img");
            if ( bi != null && bi.size() > 0) {
                String imgURL = bi.get(0).attr("src");
                Image image = new Image(ImageService.IMAGE_TYPE_CAROUSEL);
                imagesToUpload.put(imgURL, image);
            }
        }

        p.getImages().addAll(imagesToUpload.values());
        personDAO.save(p);
        imageService.upload(imagesToUpload, DEFAULT_CONNECTION_TIMEOUT, ImageService.ENTITY_PERSON);
        return p;
    }
}
