package org.nop.eshop.dao;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.nop.eshop.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Repository
public class MovieDAOImpl implements MovieDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public List<Movie> getAll() {
        return (List<Movie>) getCurrentSession().createQuery("from Movie").list();
    }

    @Override
    public Movie getById(Long id) {
        return (Movie) getCurrentSession().get(Movie.class, id);
    }

    @Override
    public List<Movie> search(String q, String[] fields) {
        FullTextSession fullTextSession = Search.getFullTextSession(getCurrentSession());
        QueryBuilder queryBuilder = fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(Movie.class).get();
        org.apache.lucene.search.Query luceneQuery = queryBuilder
                .keyword()
                .fuzzy()
                .withThreshold(0.7f)
                .onField("title")
                .matching(q)
                .createQuery();
        return (List<Movie>)fullTextSession.createFullTextQuery(luceneQuery, Movie.class).list();
    }

    @Override
    public Set<Movie> search(int start, int max) {
        Criteria c = getCurrentSession().createCriteria(Movie.class)
                .add(Restrictions.in("id", getPaginatedIds(start, max)))
                .addOrder(Order.asc("id"));
        return new TreeSet<Movie>(c.list());
    }

    @Override
    public Long count() {
        Query q = getCurrentSession().createQuery("select count(m.id) from Movie m");
        return (Long)q.uniqueResult();
    }

    private List<Long> getPaginatedIds(int start, int max) {
        Criteria c = getCurrentSession().createCriteria(Movie.class)
                .setProjection(Projections.groupProperty("id"))
                .addOrder(Order.asc("id"))
                .setFirstResult(start)
                .setMaxResults(max);
                //.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        //c.setFirstResult(start);
        //c.setMaxResults(max);
        return (List<Long>)c.list();
//        Criteria c = getCurrentSession().createCriteria(Movie.class)
//                .setProjection(Projections.id())
//                .addOrder(Order.asc("id"))
//                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
//        c.setFirstResult(start);
//        c.setMaxResults(max);
//        return (List<Long>)c.list();
    }

    @Override
    public Movie getByIMDBId(String imdbId) {
        Query q = getCurrentSession().createQuery("from Movie p where p.imdbId = :imdbId");
        q.setParameter("imdbId", imdbId);
        Movie result = (Movie)q.uniqueResult();
        return result;
    }

    @Override
    public Movie getByTitle(String title) {
        return null;
    }

    @Override
    public void save(Movie movie) {
        getCurrentSession().saveOrUpdate(movie);
    }
}
