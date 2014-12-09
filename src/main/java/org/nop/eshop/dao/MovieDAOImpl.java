package org.nop.eshop.dao;

import org.apache.lucene.search.BooleanQuery;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.nop.eshop.model.Movie;
import org.nop.eshop.search.SearchEntry;
import org.nop.eshop.service.LuceneQueryBuilder;
import org.nop.eshop.web.model.PagerResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Repository
public class MovieDAOImpl implements MovieDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private LuceneQueryBuilder luceneQueryBuilder;

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
                .onFields("title", "description", "genres.title", "countries.title") //fields
                .matching(q)
                .createQuery();
        return (List<Movie>)fullTextSession.createFullTextQuery(luceneQuery, Movie.class).list();
    }

    @Override
    public Set<Movie> search(int start, int max, PagerResult<?> pager) {
        Criteria c = getCurrentSession().createCriteria(Movie.class)
                .add(Restrictions.in("id", getPaginatedIds(start, max, pager)))
                .addOrder(Order.asc("id"));

        return new TreeSet<Movie>(c.list());
    }

    @Override
    public Set<String> getAgeCategories() {
        Criteria c = getCurrentSession().createCriteria(Movie.class)
                .setProjection(Projections.groupProperty("ageCategory"));
        return new HashSet<String>(c.list());
    }

    @Override
    public Long count() {
        Query q = getCurrentSession().createQuery("select count(m.id) from Movie m");
        return (Long)q.uniqueResult();
    }

    private List<Long> getPaginatedIds(int start, int max, PagerResult<?> pager) {
        //Issue total count of records
        Number total = (Number) getCurrentSession().createCriteria(Movie.class).
                setProjection(Projections.rowCount()).uniqueResult();

        pager.setMaxResults((Long)total);

        Criteria c = getCurrentSession().createCriteria(Movie.class)
                .setProjection(Projections.groupProperty("id"))
                .addOrder(Order.asc("id"))
                .setFirstResult(start)
                .setMaxResults(max);
        return (List<Long>)c.list();
    }

    @Override
    public Movie getByIMDBId(String imdbId) {
        Query q = getCurrentSession().createQuery("from Movie p where p.imdbId = :imdbId");
        q.setParameter("imdbId", imdbId);
        Movie result = (Movie)q.uniqueResult();
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Movie> fullTextSearch(String q, int start, int max, PagerResult<?> pager) {
        FullTextSession fullTextSession = Search.getFullTextSession(getCurrentSession());
        QueryBuilder queryBuilder = fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(Movie.class).get();
        org.apache.lucene.search.Query luceneQuery = queryBuilder
                .keyword()
                .fuzzy()
                .withThreshold(0.7f)
                .onFields("title", "description") //fields
                .matching(q)
                .createQuery();
        FullTextQuery query = fullTextSession.createFullTextQuery(luceneQuery, Movie.class);
        pager.setMaxResults(query.getResultSize());
        return (List<Movie>) query.setFirstResult(start).setMaxResults(max).list();
    }

    public List<Movie> advancedQuery(List<SearchEntry<?>> searchEntries, int start, int max, PagerResult<?> pager) {
        FullTextSession fullTextSession = Search.getFullTextSession(getCurrentSession());
        QueryBuilder queryBuilder = fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(Movie.class).get();
        BooleanQuery booleanQuery = luceneQueryBuilder.buildQueryFor(searchEntries, queryBuilder);
        FullTextQuery query = fullTextSession.createFullTextQuery(booleanQuery, Movie.class);
        pager.setMaxResults(query.getResultSize());
        return (List<Movie>) query.setFirstResult(start).setMaxResults(max).list();
    }

    @Override
    public Movie getByTitle(String title) {
        return null;
    }

    @Override
    public void update(Movie movie) {
        getCurrentSession().merge(movie);
    }

    @Override
    public void deleteById(Long id) {
        Object toDelete = getCurrentSession().load(Movie.class, id);
        getCurrentSession().delete(toDelete);
    }

    @Override
    public void save(Movie movie) {
        getCurrentSession().save(movie);
    }
}
