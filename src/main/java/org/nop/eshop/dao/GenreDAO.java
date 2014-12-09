package org.nop.eshop.dao;

import org.nop.eshop.model.Genre;

import java.util.List;

/**
 * Created by nop on 30/09/14.
 */
public interface GenreDAO {
    List<Genre> getAll();
    Genre getById(Long id);
    Genre getByIMDBId(Long imdbId);
    Genre getByTitle(String title);
    void save(Genre t);
    void update(Genre d0);
}
