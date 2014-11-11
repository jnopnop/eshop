package org.nop.eshop.service;

import org.apache.commons.lang.StringUtils;
import org.jboss.logging.Logger;
import org.nop.eshop.dao.*;
import org.nop.eshop.model.*;
import org.nop.eshop.utils.GMConverter;
import org.nop.eshop.web.model.MovieWeb;
import org.nop.eshop.web.model.PagerResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
public class MovieServiceImpl implements MovieService {

    Logger log = Logger.getLogger(MovieServiceImpl.class);

    @Autowired
    private MovieDAO movieDAO;

    @Autowired
    private GenreDAO genreDAO;

    @Autowired
    private AgeCategoryDAO ageCategoryDAO;

    @Autowired
    private CountryDAO countryDAO;

    @Autowired
    private PersonDAO personDAO;

    @Autowired
    private CareerDAO careerDAO;

    @Transactional
    public List<MovieWeb> search(String q) {
        return GMConverter.toWebModels(movieDAO.search(q, new String[] { "title" }));
    }

    @Transactional
    public List<MovieWeb> search(int startPage, int pageSize) {
        return GMConverter.toWebModels(movieDAO.search((startPage - 1) * pageSize, pageSize));
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
    public Map<Long, String> getCategories() {
        return GMConverter.toWebAgeCategories(ageCategoryDAO.getAll());
    }

    @Transactional
    public Map<Long, String> getCountries() {
        return GMConverter.toWebCountries(countryDAO.getAll());
    }

    @Transactional
    public Map<Long, String> getPersons() {
        return GMConverter.toWebPersons(personDAO.getAll());
    }

    @Transactional
    public void createMovie(MovieWeb model) {
        if (model == null) {
            log.error("Can\'t save an empty movie!");
            return;
        }

        if (model.getImdbId() != null && model.getImdbId().trim().length() > 0
                && movieDAO.getByIMDBId(model.getImdbId()) != null) {
            log.error("Movie with the same IMDB ID already exists!");
            return;
        }

        Movie movie = new Movie();
        //Base params
        movie.setTitle(model.getTitle());
        movie.setDescription(model.getDescription());
        movie.setDuration(model.getDuration());
        movie.setRating(model.getRating());
        movie.setReleaseDate(model.getReleaseDate());
        movie.setImdbId(StringUtils.isEmpty(model.getImdbId()) ? getRandomImdbId() : model.getImdbId());
        movie.setImageURL(model.getImageURL());

        //AgeCategory
        AgeCategory ageCategory = ageCategoryDAO.getById(Long.valueOf(model.getAgeCategory().getId()));
        movie.setAgeCategory(ageCategory);

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
        Map<String, Career> careers = new HashMap<>();
        for (Career c : careerDAO.getAll()) {
            careers.put(c.getTitle(), c);
        }
        for (Long id: model.getDirectors().keySet()) {
            Person p = personDAO.getById(id);
            MoviePerson mp = new MoviePerson();
            mp.setPerson(p);
            mp.setCareer(careers.get(MovieService.PERSON_DIRECTOR));
            mp.setMovie(movie);
            mpc.add(mp);
        }
        for (Long id: model.getWriters().keySet()) {
            Person p = personDAO.getById(id);
            MoviePerson mp = new MoviePerson();
            mp.setPerson(p);
            mp.setCareer(careers.get(MovieService.PERSON_WRITER));
            mp.setMovie(movie);
            mpc.add(mp);
        }
        for (Long id: model.getActors().keySet()) {
            Person p = personDAO.getById(id);
            MoviePerson mp = new MoviePerson();
            mp.setPerson(p);
            mp.setCareer(careers.get(MovieService.PERSON_ACTOR));
            mp.setMovie(movie);
            mpc.add(mp);
        }
        movie.setPersons(mpc);
        movieDAO.save(movie);
    }

    @Transactional
    public PagerResult<MovieWeb> getPaginated(Integer p) {
        p = currentPage(p);
        PagerResult<MovieWeb> pager = new PagerResult<>();
        pager.setCurrPage(p);
        pager.setResults(GMConverter.toWebModels(movieDAO.search((p - 1)*PagerResult.PAGE_SIZE, PagerResult.PAGE_SIZE)));
        pager.setLastPage(lastPage(movieDAO.count()));
        return pager;
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
