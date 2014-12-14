package org.nop.eshop.dao;


import org.hibernate.*;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.nop.eshop.model.Person;
import org.nop.eshop.web.model.PagerResult;
import org.nop.eshop.web.model.PersonWeb;
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
        Person p = (Person) getCurrentSession().get(Person.class, id);
        Hibernate.initialize(p);
        return p;
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

    @Override
    public void update(Person p) {
        getCurrentSession().saveOrUpdate(p);
    }

    @Override
    public List<Person> fullTextSearch(String q, int start, Integer pageSize, PagerResult<PersonWeb> pager) {
        FullTextSession fullTextSession = Search.getFullTextSession(getCurrentSession());
        QueryBuilder queryBuilder = fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(Person.class).get();
        org.apache.lucene.search.Query luceneQuery = queryBuilder
                .keyword()
                .fuzzy()
                .withThreshold(0.7f)
                .onField("fullname") //fields
                .matching(q)
                .createQuery();
        FullTextQuery query = fullTextSession.createFullTextQuery(luceneQuery, Person.class);
        pager.setMaxResults(query.getResultSize());
        return (List<Person>) query.setFirstResult(start).setMaxResults(pageSize).list();
    }

    @Override
    public List<Person> search(int start, Integer pageSize, PagerResult<PersonWeb> pager) {
        Criteria c = getCurrentSession().createCriteria(Person.class)
                .add(Restrictions.in("id", getPaginatedIds(start, pageSize, pager)))
                .addOrder(Order.asc("fullname"));

        return c.list();
    }

    private List<Long> getPaginatedIds(int start, int max, PagerResult<?> pager) {
        //Issue total count of records
        Number total = (Number) getCurrentSession().createCriteria(Person.class).
                setProjection(Projections.rowCount()).uniqueResult();

        pager.setMaxResults((Long)total);

        Criteria c = getCurrentSession().createCriteria(Person.class)
                .setProjection(Projections.groupProperty("id"))
                .addOrder(Order.asc("fullname"))
                .setFirstResult(start)
                .setMaxResults(max);
        return (List<Long>)c.list();
    }
}
