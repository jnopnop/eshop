package org.nop.eshop.service;


import org.nop.eshop.web.model.MovieWeb;
import org.nop.eshop.web.model.PagerResult;
import org.nop.eshop.web.model.PersonWeb;
import org.nop.eshop.web.model.SearchMovieForm;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface MovieService {
    public static final String PERSON_DIRECTOR = "DIRECTOR";
    public static final String PERSON_WRITER = "WRITER";
    public static final String PERSON_ACTOR = "ACTOR";

    public static final String FIELD_TITLE = "title";
    public static final String FIELD_YEAR = "releaseDate";
    public static final String FIELD_COUNTRY = "countries.title";
    public static final String FIELD_GENRES = "genres.title";
    public static final String FIELD_AGE_CATEGORY = "ageCategory";

    MovieWeb getById(long id);
    PagerResult<MovieWeb> search(String q, Integer p);
    PagerResult<MovieWeb> search(SearchMovieForm values, Integer p) throws ParseException;

    List<MovieWeb> getAll();
    void createMovie(MovieWeb movie);
    void updateMovie(MovieWeb movie);
    void deleteMovie(Long id);

    Map<Long, String> getGenres();
    Set<String> getCategories();
    Map<Long, String> getCountries();
    Map<Long, String> getPersons();

    PagerResult<MovieWeb> getPaginated(Integer p);

    PersonWeb getPerson(long id);

    void deleteComment(Long id);

    PagerResult<PersonWeb> searchPersons(String q, Integer page);
}
