package org.nop.eshop.dao;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.nop.eshop.model.Career;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CareerDAOImpl implements CareerDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public List<Career> getAll() {
        return (List<Career>)getCurrentSession().createQuery("from Career").list();
    }

    @Override
    public Career getById(Long id) {
        return null;
    }

    @Override
    public Career getByIMDBId(Long imdbId) {
        return null;
    }

    @Override
    public Career getByTitle(String title) {
        return null;
    }

    @Override
    public void save(Career career) {
        getCurrentSession().save(career);
    }
}
