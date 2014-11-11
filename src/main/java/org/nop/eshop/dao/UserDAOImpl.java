package org.nop.eshop.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.nop.eshop.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    public User getUser(String login) {
        Query query = getCurrentSession().createQuery("from User u where u.email = :login");
        query.setParameter("login", login);
        List<User> userList = query.list();
        if (userList.size() > 0)
            return userList.get(0);
        else
            return null;
    }


    public List<User> getAll() {
        return getCurrentSession().createQuery("from User").list();
    }

    public void createUser(User user) {
        getCurrentSession().save(user);
    }
}
