package org.nop.eshop.service;

import org.apache.commons.io.IOUtils;
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

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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

    public String saveImage(String imgUrl, String path, String filename) throws MalformedURLException, InterruptedException {
        String extension = imgUrl.substring(imgUrl.lastIndexOf('.'));
        URL url = new URL(imgUrl);
        InputStream is = null;
        int delay = 1;
        while (true) {
            try {
                Thread.sleep(100 * delay);
                delay += 50;
                is = url.openStream();
                break;
            } catch (IOException e) {
                if (delay > 500) {
                    LOG.error("Too much time waiting for response for image. Shutting down...");
                    if (is != null) {
                        IOUtils.closeQuietly(is);
                    }
                    return null;
                }
            }
        }
        OutputStream os = null;
        try {
            os = new FileOutputStream(path + filename + extension);
            IOUtils.copy(is, os);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null)
                IOUtils.closeQuietly(is);
            if (os != null)
                IOUtils.closeQuietly(os);
        }
        return extension;
    }

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
        for (int i = 0; i < 2; i++) {
            //int i = 0;
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
                String ext = null;
                String filename = username.replaceAll("\\s","");
                try {
                    ext = saveImage(userPic, USER_IMG_HOME, filename);
                } catch (Exception e) {
                    continue;
                }
                if (ext != null) {
                    u.setImage(filename + ext);
                }
                userDAO.createUser(u);

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
        String ext = null;
        String filename = basicMovie.getImdbId().replaceAll("\\s","");
        try {
            ext = saveImage(imgURL, MOVIE_IMG_HOME, filename);
            if (ext != null)
                basicMovie.setImageURL(filename + ext);
        } catch (Exception e) {
        }

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
        int delay = 1;
        while (true) {
            try {
                Thread.sleep(100 * delay);
                delay += 50;
                result = Jsoup.connect(URL).get();
                break;
            } catch (Exception e) {
                LOG.error("Error getting URL + " + URL);
                if (delay > 500) {
                    LOG.error("Too much time waiting for response from [" + URL + "]. Shutting down...");
                    return null;
                }
            }
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

        bi = person.select("td#img_primary img");
        if ( bi != null && bi.size() > 0) {
            String imgURL = bi.get(0).attr("src");
            try {
                String filename = p.getImdbId().replaceAll("\\s","");
                String ext = saveImage(imgURL, PERSON_IMG_HOME, filename);
                if (ext != null)
                    p.setPhotoURL(filename + ext);
            } catch (Exception e) {}
        }

        return p;
    }
}
