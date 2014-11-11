package org.nop.eshop.dao;

import org.nop.eshop.model.Career;

import java.util.List;

/**
 * Created by nop on 30/09/14.
 */
public interface CareerDAO {
    List<Career> getAll();
    Career getById(Long id);
    Career getByIMDBId(Long imdbId);
    Career getByTitle(String title);
    void save(Career t);
}
