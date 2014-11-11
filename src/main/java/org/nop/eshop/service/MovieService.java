package org.nop.eshop.service;


import org.nop.eshop.web.model.MovieWeb;
import org.nop.eshop.web.model.PagerResult;

import java.util.List;
import java.util.Map;

public interface MovieService {
    public static final String PERSON_DIRECTOR = "DIRECTOR";
    public static final String PERSON_WRITER = "WRITER";
    public static final String PERSON_ACTOR = "ACTOR";

    List<MovieWeb> search(String q);
    List<MovieWeb> search(int startPage, int pageSize);
    List<MovieWeb> getAll();

    MovieWeb getById(long id);

    Map<Long, String> getGenres();
    Map<Long, String> getCategories();
    Map<Long, String> getCountries();
    Map<Long, String> getPersons();

    void createMovie(MovieWeb movie);

    PagerResult<MovieWeb> getPaginated(Integer p);
}
