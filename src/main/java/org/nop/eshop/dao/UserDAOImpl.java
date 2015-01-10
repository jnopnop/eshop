package org.nop.eshop.dao;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.nop.eshop.model.User;
import org.nop.eshop.web.model.PagerResult;
import org.nop.eshop.web.model.PagingResult;
import org.nop.eshop.web.model.UserWeb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
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

    @Override
    public Long count() {
        Query q = getCurrentSession().createQuery("select count(u.id) from User u");
        return (Long)q.uniqueResult();
    }


    public List<User> getAll() {
        return getCurrentSession().createQuery("from User").list();
    }

    public void createUser(User user) {
        getCurrentSession().save(user);
    }

    @Override
    public void save(User u) {
        getCurrentSession().save(u);
    }

    @Override
    public void update(User u) {
        getCurrentSession().saveOrUpdate(u);
    }

    @Override
    public User get(Long id) {
        return (User) getCurrentSession().load(User.class, id);
    }

    @Override
    public Collection<User> search(String q, int i, Integer pageSize, PagerResult<UserWeb> pager) {
        Criteria c = getCurrentSession().createCriteria(User.class);

        if (StringUtils.isNotBlank(q)) {
            c.add(Restrictions.like("email", q));
        }
        c.addOrder(Order.asc("email")).setMaxResults(pageSize).setFirstResult(i);
        return new ArrayList<>(c.list());
    }

    @Override
    public PagingResult<UserWeb> search(PagingResult<UserWeb> pagingResult) {
//        PageInfo pageInfo = pagingResult.getPageInfo();
//        Criteria c = getCurrentSession().createCriteria(Movie.class);
//        if (pageInfo.get)
//        return pagingResult;
        return null;
    }

    @Override
    public void delete(Long id) {
        Object toDelete = getCurrentSession().load(User.class, id);
        getCurrentSession().delete(toDelete);
    }
}
