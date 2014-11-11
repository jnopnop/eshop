package org.nop.eshop.dao;


import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.nop.eshop.model.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GenreDAOImpl implements GenreDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public List<Genre> getAll() {
        Query q = getCurrentSession().createQuery("from Genre");
        return q.list();
    }

    @Override
    public Genre getById(Long id) {
        return (Genre)getCurrentSession().get(Genre.class, id.intValue());
    }

    @Override
    public Genre getByIMDBId(Long imdbId) {
        return null;
    }

    @Override
    public Genre getByTitle(String title) {
        Query q = getCurrentSession().createQuery("from Genre g where g.title = :title");
        q.setParameter("title", title);
        Genre result = (Genre)q.uniqueResult();
        return result;
    }

    @Override
    public void save(Genre genre) {
        getCurrentSession().save(genre);
    }
}
