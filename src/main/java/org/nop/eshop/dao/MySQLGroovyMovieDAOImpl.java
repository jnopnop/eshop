package org.nop.eshop.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MySQLGroovyMovieDAOImpl implements MySQLGroovyMovieDAO {
    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public <T> List<T> getAll(T t, Class<T> clazz) {
        return new ArrayList<T>(getCurrentSession().createQuery("from " + clazz.getSimpleName()).list());
    }
}
