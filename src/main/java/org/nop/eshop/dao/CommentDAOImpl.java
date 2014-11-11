package org.nop.eshop.dao;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.nop.eshop.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentDAOImpl implements CommentDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public List<Comment> getAll() {
        return null;
    }

    @Override
    public Comment getById(Long id) {
        return null;
    }

    @Override
    public Comment getByIMDBId(Long imdbId) {
        return null;
    }

    @Override
    public Comment getByTitle(String title) {
        return null;
    }

    @Override
    public void save(Comment comment) {
        getCurrentSession().save(comment);
    }
}
