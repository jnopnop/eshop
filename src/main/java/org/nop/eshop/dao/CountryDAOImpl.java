package org.nop.eshop.dao;


import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.nop.eshop.model.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CountryDAOImpl implements CountryDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public List<Country> getAll() {
        return getCurrentSession().createQuery("from Country").list();
    }

    @Override
    public Country getById(Long id) {
        return (Country)getCurrentSession().get(Country.class, id.intValue());
    }

    @Override
    public Country getByIMDBId(Long imdbId) {
        return null;
    }

    @Override
    public Country getByTitle(String title) {
        Query q = getCurrentSession().createQuery("from Country c where c.title = :title");
        q.setParameter("title", title);
        Country result = (Country)q.uniqueResult();
        return result;
    }

    @Override
    public void save(Country country) {
        getCurrentSession().save(country);
    }
}
