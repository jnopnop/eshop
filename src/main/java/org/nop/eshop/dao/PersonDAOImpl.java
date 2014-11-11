package org.nop.eshop.dao;


import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.nop.eshop.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PersonDAOImpl implements PersonDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public List<Person> getAll() {
        return getCurrentSession().createQuery("from Person").list();
    }

    @Override
    public Person getById(Long id) {
        return (Person) getCurrentSession().get(Person.class, id);
    }

    @Override
    public Person getByIMDBId(String imdbId) {
        Query q = getCurrentSession().createQuery("from Person p where p.imdbId = :imdbId");
        q.setParameter("imdbId", imdbId);
        Person result = (Person)q.uniqueResult();
        return result;
    }

    @Override
    public Person getByTitle(String title) {
        return null;
    }

    @Override
    public void save(Person person) {
        getCurrentSession().save(person);
    }
}
