package org.nop.eshop.service;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.jboss.logging.Logger;
import org.nop.eshop.dao.*;
import org.nop.eshop.model.*;
import org.nop.eshop.search.SearchEntry;
import org.nop.eshop.utils.GMConverter;
import org.nop.eshop.web.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class MovieServiceImpl implements MovieService {

    Logger log = Logger.getLogger(MovieServiceImpl.class);

    @Autowired
    private MovieDAO movieDAO;

    @Autowired
    private GenreDAO genreDAO;

    @Autowired
    private CountryDAO countryDAO;

    @Autowired
    private PersonDAO personDAO;

    @Autowired
    private RoleDAO roleDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private CommentDAO commentDAO;

    @Autowired NewsDAO newsDAO;

    @Autowired
    ImageService imageService;

    @Override
    @Transactional
    public PagerResult<MovieWeb> search(String q, Integer p) {
        p = currentPage(p);
        PagerResult<MovieWeb> pager = new PagerResult<>();
        pager.setCurrPage(p);
        pager.setResults(GMConverter.toWebModels(movieDAO.fullTextSearch(q, (p - 1) * PagerResult.PAGE_SIZE, PagerResult.PAGE_SIZE, pager)));
        pager.setLastPage(lastPage(pager.getMaxResults()));
        return pager;
    }

    @Override
    @Transactional
    public PagerResult<MovieWeb> search(SearchMovieForm values, Integer p) {
        List<SearchEntry<?>> searchMap = new ArrayList<>();
        if (StringUtils.isNotBlank(values.getTitle())) {
            searchMap.add(new SearchEntry<>(FIELD_TITLE, values.getTitle()));
        }
        if (StringUtils.isNotBlank(values.getCountry())) {
            searchMap.add(new SearchEntry<>(FIELD_COUNTRY, values.getCountry()));
        }
        if (StringUtils.isNotBlank(values.getAgeCategory())) {
            searchMap.add(new SearchEntry<>(FIELD_AGE_CATEGORY, values.getAgeCategory()));
        }
        if (StringUtils.isNotBlank(values.getYear_start()) || StringUtils.isNotBlank(values.getYear_end())) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            Date year1 = null;
            Date year2 = null;
            try {
                year1 = sdf.parse(values.getYear_start());
            } catch (Exception e) {}

            try {
                year2 = sdf.parse(values.getYear_end());
            } catch (ParseException e) {}
            searchMap.add(new SearchEntry<>(FIELD_YEAR, year1, year2));
        }
        if (values.getGenres() != null && values.getGenres().length > 0) {
            searchMap.add(new SearchEntry<>(FIELD_GENRES, values.getGenres()));
        }

        if (searchMap.size() < 1) {
            return searchAll(p);
        }
        p = currentPage(p);
        PagerResult<MovieWeb> pager = new PagerResult<>();
        pager.setCurrPage(p);
        pager.setResults(GMConverter.toWebModels(movieDAO.advancedQuery(searchMap, (p - 1) * PagerResult.PAGE_SIZE, PagerResult.PAGE_SIZE, pager)));
        pager.setLastPage(lastPage(pager.getMaxResults()));
        return pager;
    }

    private PagerResult<MovieWeb> searchAll(Integer p) {
        p = currentPage(p);
        PagerResult<MovieWeb> pager = new PagerResult<>();
        pager.setCurrPage(p);
        pager.setResults(GMConverter.toWebModels(movieDAO.search((p-1)*PagerResult.PAGE_SIZE, PagerResult.PAGE_SIZE, pager)));
        pager.setLastPage(lastPage(pager.getMaxResults()));
        return pager;
    }

    @Transactional(readOnly = true)
    public List<MovieWeb> getAll() {
        return GMConverter.toWebModels(movieDAO.getAll());
    }

    @Transactional
    public MovieWeb getById(long id) {
        return GMConverter.toWebModel(movieDAO.getById(id));
    }

    @Transactional
    public Map<Long, String> getGenres() {
        return GMConverter.toWebGenres(genreDAO.getAll());
    }

    @Transactional
    public Set<String> getCategories() {
        return movieDAO.getAgeCategories();
    }

    @Transactional
    public Map<Long, String> getCountries() {
        return GMConverter.toSimpleWebCountries(countryDAO.getAll());
    }

    @Transactional
    public Map<Long, String> getPersons() {
        return GMConverter.toWebPersons(personDAO.getAll());
    }

    @Transactional
    public Movie createMovie(MovieWeb model) {
        if (model == null) {
            log.error("Can\'t save an empty movie!");
            return null;
        }

        if (model.getImdbId() != null && model.getImdbId().trim().length() > 0
                && movieDAO.getByIMDBId(model.getImdbId()) != null) {
            log.error("Movie with the same IMDB ID already exists!");
            return null;
        }

        Movie movie = new Movie();
        //Base params
        movie.setTitle(model.getTitle());
        movie.setDescription(model.getDescription());
        movie.setDuration(model.getDuration());
        movie.setRating(model.getRating());
        movie.setReleaseDate(model.getReleaseDate());
        movie.setImdbId(StringUtils.isEmpty(model.getImdbId()) ? getRandomImdbId() : model.getImdbId());
        ///movie.getImages().addAll(model.getImages());
        movie.setAgeCategory(model.getAgeCategory());

        //TODO: remove redundant save
        movieDAO.save(movie);

        //Countries
        Set<Country> countrySet = new HashSet<>();
        for (Long id: model.getCountries().keySet()) {
            Country c = countryDAO.getById(id);
            countrySet.add(c);
        }
        movie.setCountries(countrySet);

        //Genres
        Set<Genre> genres = new HashSet<>();
        for (Long id: model.getGenres().keySet()) {
            Genre c = genreDAO.getById(id);
            genres.add(c);
        }
        movie.setGenres(genres);

        //Persons
        Set<MoviePerson> mpc = new HashSet<>();
        for (Long id: model.getDirectors().keySet()) {
            Person p = personDAO.getById(id);
            MoviePerson mp = new MoviePerson();
            mp.setPerson(p);
            mp.setCareer(MovieService.PERSON_DIRECTOR);
            mp.setMovie(movie);
            mpc.add(mp);
        }
        for (Long id: model.getWriters().keySet()) {
            Person p = personDAO.getById(id);
            MoviePerson mp = new MoviePerson();
            mp.setPerson(p);
            mp.setCareer(MovieService.PERSON_WRITER);
            mp.setMovie(movie);
            mpc.add(mp);
        }
        for (Long id: model.getActors().keySet()) {
            Person p = personDAO.getById(id);
            MoviePerson mp = new MoviePerson();
            mp.setPerson(p);
            mp.setCareer(MovieService.PERSON_ACTOR);
            mp.setMovie(movie);
            mpc.add(mp);
        }
        movie.setPersons(mpc);
        movieDAO.save(movie);

        return movie;
    }


    @Transactional
    public void updateMovie(MovieWeb model) {
        if (model == null) {
            throw new IllegalArgumentException("Can\'t update an empty movie!");
        }

        Movie movie = movieDAO.getById(model.getId());
        if (movie == null) {
            throw new IllegalArgumentException("Update failed: movie with id [\" + model.getId() + \"] does not exist.");
        }

        //Base params
        movie.setTitle(model.getTitle());
        movie.setDescription(model.getDescription());
        movie.setDuration(model.getDuration());
        movie.setRating(model.getRating());
        movie.setReleaseDate(model.getReleaseDate());
        movie.setImdbId(StringUtils.isEmpty(model.getImdbId()) ? getRandomImdbId() : model.getImdbId());
        //movie.setImageURL(model.getImageURL());
        movie.setAgeCategory(model.getAgeCategory());

        //Countries
        Set<Country> countrySet = new HashSet<>();
        for (Long id: model.getCountries().keySet()) {
            Country c = countryDAO.getById(id);
            countrySet.add(c);
        }
        movie.setCountries(countrySet);

        //Genres
        Set<Long> sameDeps = new HashSet<>();
        Set<Genre> toRemove = new HashSet<>();
        Iterator<Genre> diter = movie.getGenres().iterator();
        while (diter.hasNext()) {
            Genre d0 = diter.next();
            if (model.getGenres().containsKey(d0.getId())) {
                sameDeps.add(new Long(d0.getId()));
            } else {
                toRemove.add(d0);
            }
        }
        for (Genre d0: toRemove) {
            movie.getGenres().remove(d0);
            d0.getMovies().remove(movie);
        }

        Set<Long> newItems = new HashSet<Long>(CollectionUtils.disjunction(model.getGenres().keySet(), sameDeps));
        for (Long l: newItems) {
            Genre d = genreDAO.getById(l);
            d.getMovies().add(movie);
            movie.getGenres().add(d);
        }

        //Persons
        Set<MoviePerson> newPersons = new HashSet<>();
        newPersons.addAll(updatePersons(movie, model.getDirectors(), MovieService.PERSON_DIRECTOR));
        newPersons.addAll(updatePersons(movie, model.getWriters(), MovieService.PERSON_WRITER));
        newPersons.addAll(updatePersons(movie, model.getActors(), MovieService.PERSON_ACTOR));
        movie.getPersons().addAll(newPersons);

        movieDAO.update(movie);
    }

    private Set<MoviePerson> updatePersons(Movie movie, Map<Long, String> persons, String role) {
        Set<MoviePerson> mpc = new HashSet<>();
        Set<Long> samePersons = new HashSet<>();
        Set<MoviePerson> toDelete = new HashSet<>();
        for (MoviePerson mp: movie.getPersons()) {
            if (mp.getCareer().equalsIgnoreCase(role)) {
                if (persons.containsKey(mp.getPerson().getId())) {
                    samePersons.add(mp.getPerson().getId());
                } else {
                    toDelete.add(mp);
                }
            }
        }
        for (MoviePerson p0: toDelete) {
            movie.getPersons().remove(p0);
            movieDAO.deleteMPRelation(p0);
        }
        Set<Long> newItems = new HashSet<>(CollectionUtils.disjunction(persons.keySet(), samePersons));
        for (Long id: newItems) {
            Person p = personDAO.getById(id);
            MoviePerson mp = new MoviePerson();
            mp.setPerson(p);
            mp.setCareer(role);
            mp.setMovie(movie);
            personDAO.update(p);
            mpc.add(mp);
        }

        return mpc;
    }

    @Override
    @Transactional
    public void deleteMovie(Long id) {
        movieDAO.deleteById(id);
    }

    @Transactional
    public PagerResult<MovieWeb> getPaginated(Integer p) {
        p = currentPage(p);
        PagerResult<MovieWeb> pager = new PagerResult<>();
        pager.setCurrPage(p);
        pager.setResults(GMConverter.toWebModels(movieDAO.search((p - 1)*PagerResult.PAGE_SIZE, PagerResult.PAGE_SIZE, pager)));
        pager.setLastPage(lastPage(movieDAO.count()));
        return pager;
    }

    @Override
    @Transactional
    public PersonWeb getPerson(long id) {
        return GMConverter.toWebPerson(personDAO.getById(id));
    }

    @Override
    @Transactional
    public void deleteComment(Long id) {
        commentDAO.deleteById(id);
    }

    @Override
    @Transactional
    public NewsWeb getNewsById(Long id) {
        return GMConverter.toWebNews(newsDAO.get(id));
    }

    @Override
    @Transactional
    public PagerResult<NewsWeb> searchNews(String q, Integer page) {
        page = currentPage(page);
        PagerResult<NewsWeb> pager = new PagerResult<>();
        pager.setCurrPage(page);
        pager.setResults(GMConverter.toWebNews(newsDAO.search(q, (page - 1) * PagerResult.PAGE_SIZE, PagerResult.PAGE_SIZE, pager)));
        pager.setLastPage(lastPage(pager.getMaxResults()));
        return pager;
    }

    @Override
    @Transactional
    public void createNews(NewsWeb nw) throws IOException {
        News n = new News();
        n.setTitle(nw.getTitle());
        n.setContents(nw.getContents());
        Image mainImage = imageService.upload(nw.getMainImageFile().getBytes(), ImageService.ENTITY_NEWS, ImageService.IMAGE_TYPE_PRIMARY);
        n.getImages().add(mainImage);
        newsDAO.save(n);
    }

    @Override
    @Transactional
    public void updateNews(NewsWeb nw) {
        News toUpdate = newsDAO.get(nw.getId());
        if (toUpdate == null) {
            return;
        }
        toUpdate.setTitle(nw.getTitle());
        toUpdate.setContents(nw.getContents());
        newsDAO.save(toUpdate);
    }

    @Override
    @Transactional
    public void deleteNews(Long id) {
        newsDAO.delete(id);
    }

    @Override
    @Transactional
    public void addMovieImages(List<MultipartFile> images, long id, String ptype) throws IOException {
        Movie movie = movieDAO.getById(id);
        if (movie == null)
            return;

        if (ImageService.IMAGE_TYPE_CAROUSEL.equals(ptype)) {
            Map<Image, byte[]> imagesData = new LinkedHashMap<>();
            for (MultipartFile img: images) {
                try {
                    Image i = imageService.upload(img.getBytes(), ImageService.ENTITY_MOVIE, ImageService.IMAGE_TYPE_CAROUSEL);
                    movie.getImages().add(i);
                } catch (Exception e) {
                    log.error("Cannot save image!", e);
                }
            }
        } else if (ImageService.IMAGE_TYPE_PRIMARY.equals(ptype)) {
            Image i = imageService.upload(images.get(0).getBytes(), ImageService.ENTITY_MOVIE, ImageService.IMAGE_TYPE_PRIMARY);
            movie.getImages().add(i);
        } else {
            return;
        }

        movieDAO.save(movie);
    }

    @Override
    @Transactional
    public void addNewsImage(List<MultipartFile> images, long id, String ptype) throws IOException {
        News news = newsDAO.get(id);
        if (news == null) {
            return;
        }

        if (!ImageService.IMAGE_TYPE_PRIMARY.equals(ptype)) {
            return;
        }

        Image i = imageService.upload(images.get(0).getBytes(), ImageService.ENTITY_NEWS, ImageService.IMAGE_TYPE_PRIMARY);
        news.getImages().add(i);
        newsDAO.save(news);
    }

    @Override
    @Transactional
    public void addPersonImages(List<MultipartFile> images, Long id, String ptype) throws IOException {
        Person person = personDAO.getById(id);
        if (person == null)
            return;

        if (ImageService.IMAGE_TYPE_CAROUSEL.equals(ptype)) {
            Map<Image, byte[]> imagesData = new LinkedHashMap<>();
            for (MultipartFile img: images) {
                try {
                    Image i = imageService.upload(img.getBytes(), ImageService.ENTITY_PERSON, ImageService.IMAGE_TYPE_CAROUSEL);
                    person.getImages().add(i);
                } catch (Exception e) {
                    log.error("Cannot save image!", e);
                }
            }
        } else if (ImageService.IMAGE_TYPE_PRIMARY.equals(ptype)) {
            Image i = imageService.upload(images.get(0).getBytes(), ImageService.ENTITY_PERSON, ImageService.IMAGE_TYPE_PRIMARY);
            person.getImages().add(i);
        } else {
            return;
        }

        personDAO.update(person);
    }

    @Override
    @Transactional
    public Person createPerson(PersonWeb personWeb) {
        Person person = new Person();
        person.setBirthdate(personWeb.getBirthdate());
        person.setFullname(personWeb.getFullname());
        person.setImdbId(personWeb.getImdbId());
        personDAO.save(person);

        return person;
    }

    @Override
    @Transactional
    public PagerResult<PersonWeb> searchPersons(String q, Integer page) {
        page = currentPage(page);
        PagerResult<PersonWeb> pager = new PagerResult<>();
        pager.setCurrPage(page);

        if (StringUtils.isNotBlank(q)) {
            pager.setResults(GMConverter.toWebPersonModels(personDAO.fullTextSearch(q, (page - 1) * PagerResult.PAGE_SIZE, PagerResult.PAGE_SIZE, pager)));
        } else {
            pager.setResults(GMConverter.toWebPersonModels(personDAO.search((page - 1) * PagerResult.PAGE_SIZE, PagerResult.PAGE_SIZE, pager)));
        }

        pager.setLastPage(lastPage(pager.getMaxResults()));
        return pager;
    }

    @Override
    @Transactional
    public void updatePerson(PersonWeb model) {
        if (model == null) {
            throw new IllegalArgumentException("Can\'t update an empty person!");
        }

        Person person = personDAO.getById(model.getId());
        if (person == null) {
            throw new IllegalArgumentException("Update failed: person with id [\" + model.getId() + \"] does not exist.");
        }

        person.setFullname(model.getFullname());
        person.setImdbId(model.getImdbId());
        person.setBirthdate(model.getBirthdate());
        //person.setPhotoURL(model.getPhotoURL());

        personDAO.update(person);
    }

    @Override
    @Transactional
    public void deletePerson(Long id) {
        personDAO.deleteById(id);
    }

    @Override
    @Transactional
    public void addComment(CommentWeb commentWeb, Authentication authentication) {
        Assert.isTrue(authentication.isAuthenticated());

        org.springframework.security.core.userdetails.User u = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        User ssUser = userDAO.getUser(u.getUsername());
        Assert.notNull(ssUser, "User does not exist...");

        Movie movie = movieDAO.getById(commentWeb.getMovie().getId());
        Assert.notNull(movie, "Movie does not exist...");

        Comment comment = new Comment();
        comment.setTitle(commentWeb.getTitle());
        comment.setText(commentWeb.getText());
        comment.setUser(ssUser);
        comment.setMovie(movie);

        movie.getComments().add(comment);

        commentDAO.save(comment);
    }

    private Integer currentPage(Integer p) {
        return  p != null ? Math.max(1, p) : 1;
    }

    private Integer lastPage(Long total) {
        return (int)Math.ceil(total / Float.valueOf(PagerResult.PAGE_SIZE));
    }

    private String getRandomImdbId() {
        return String.valueOf(UUID.randomUUID().getMostSignificantBits()).substring(0, 5);
    }
}
