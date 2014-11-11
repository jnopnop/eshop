package org.nop.eshop.dao;

import org.nop.eshop.model.Person;

import java.util.List;

/**
 * Created by nop on 30/09/14.
 */
public interface PersonDAO {
    List<Person> getAll();
    Person getById(Long id);
    Person getByIMDBId(String imdbId);
    Person getByTitle(String title);
    void save(Person t);
}
