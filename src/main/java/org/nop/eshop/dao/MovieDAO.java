package org.nop.eshop.dao;

import org.nop.eshop.model.Movie;

import java.util.List;
import java.util.Set;


public interface MovieDAO {
    List<Movie> getAll();
    Movie getById(Long id);
    Movie getByIMDBId(String imdbId);
    Movie getByTitle(String title);
    void save(Movie t);
    List<Movie> search(String q, String[] fields);
    Set<Movie> search(int start, int max);

    Long count();
}
