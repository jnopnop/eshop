package org.nop.eshop.service;


import org.nop.eshop.model.Movie;
import org.nop.eshop.model.Person;
import org.nop.eshop.web.model.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    Movie createMovie(MovieWeb movie);
    void updateMovie(MovieWeb movie);
    void deleteMovie(Long id);

    Map<Long, String> getGenres();
    Set<String> getCategories();
    Map<Long, String> getCountries();

    Map<Long, String> getPersons();
    PagerResult<MovieWeb> getPaginated(Integer p);
    PersonWeb getPerson(long id);
    PagerResult<PersonWeb> searchPersons(String q, Integer page);
    void updatePerson(PersonWeb pw);
    void deletePerson(Long id);

    void addComment(CommentWeb commentWeb, Authentication authentication);
    void deleteComment(Long id);

    NewsWeb getNewsById(Long id);
    PagerResult<NewsWeb> searchNews(String q, Integer page);
    void createNews(NewsWeb nw) throws IOException;
    void updateNews(NewsWeb nw);
    void deleteNews(Long id);

    void addMovieImages(List<MultipartFile> images, long id, String ptype) throws IOException;

    void addNewsImage(List<MultipartFile> images, long id, String ptype) throws IOException;

    void addPersonImages(List<MultipartFile> images, Long id, String ptype) throws IOException;

    Person createPerson(PersonWeb personWeb);
}
