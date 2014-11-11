package org.nop.eshop.dao;

import org.nop.eshop.model.Country;

import java.util.List;

/**
 * Created by nop on 30/09/14.
 */
public interface CountryDAO {
    List<Country> getAll();
    Country getById(Long id);
    Country getByIMDBId(Long imdbId);
    Country getByTitle(String title);
    void save(Country t);
}
