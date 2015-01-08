package org.nop.eshop.dao;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.nop.eshop.model.News;
import org.nop.eshop.web.model.PagerResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NewsDAOImpl implements NewsDAO {
    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public List<News> get() {
        return getCurrentSession().createQuery("from News").list();
    }

    @Override
    public News get(Long id) {
        return (News)getCurrentSession().load(News.class, id);
    }

    @Override
    public List<News> search(String q, int page, Integer pageSize, PagerResult<?> pager) {
        FullTextSession fullTextSession = Search.getFullTextSession(getCurrentSession());
        QueryBuilder queryBuilder = fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(News.class).get();

        org.apache.lucene.search.Query luceneQuery;
        if (StringUtils.isNotBlank(q)) {
            luceneQuery = queryBuilder
                    .keyword()
                    .fuzzy()
                    .withThreshold(0.7f)
                    .onFields("title", "contents")
                    .matching(q)
                    .createQuery();
        } else {
            List<News> results = (List<News>)getCurrentSession().createQuery("from News order by createdOn DESC").list();
            pager.setMaxResults(results.size());
            return results;
            //TODO: uncomment following line
            //luceneQuery = queryBuilder.all().createQuery();
        }

        FullTextQuery query = fullTextSession.createFullTextQuery(luceneQuery, News.class);
        pager.setMaxResults(query.getResultSize());
        return (List<News>) query.setFirstResult(page).setMaxResults(pageSize).list();
    }

    @Override
    public void save(News n) {
        getCurrentSession().saveOrUpdate(n);
    }

    @Override
    public void update(News p) {
        getCurrentSession().saveOrUpdate(p);
    }

    @Override
    public void delete(Long id) {
        Object toDelete = getCurrentSession().load(News.class, id);
        getCurrentSession().delete(toDelete);
    }
}
