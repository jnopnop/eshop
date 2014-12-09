package org.nop.eshop.dao;

import org.nop.eshop.model.Movie;
import org.nop.eshop.search.SearchEntry;
import org.nop.eshop.web.model.PagerResult;

import java.util.List;
import java.util.Set;


public interface MovieDAO {
    List<Movie> getAll();
    Movie getById(Long id);
    Movie getByIMDBId(String imdbId);
    Movie getByTitle(String title);

    void update(Movie movie);
    void deleteById(Long id);

    void save(Movie t);
    List<Movie> search(String q, String[] fields);
    Set<Movie> search(int start, int max, PagerResult<?> pager);
    Set<String> getAgeCategories();
    Long count();
    List<Movie> fullTextSearch(String q, int start, int max, PagerResult<?> pager);
    List<Movie> advancedQuery(List<SearchEntry<?>> searchMap, int start, int max, PagerResult<?> pager);
}
