package org.nop.eshop.dao;

import org.nop.eshop.model.Person;
import org.nop.eshop.web.model.PagerResult;
import org.nop.eshop.web.model.PersonWeb;

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

    void update(Person p);

    List<Person> fullTextSearch(String q, int page, Integer pageSize, PagerResult<PersonWeb> pager);

    List<Person> search(int start, Integer pageSize, PagerResult<PersonWeb> pager);

    void deleteById(Long id);
}
