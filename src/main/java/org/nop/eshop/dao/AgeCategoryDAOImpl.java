package org.nop.eshop.dao;


import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.nop.eshop.model.AgeCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class AgeCategoryDAOImpl implements AgeCategoryDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    @Transactional
    public List<AgeCategory> getAll() {
        return (List<AgeCategory>)getCurrentSession().createQuery("from AgeCategory").list();
    }

    @Override
    public AgeCategory getById(Long id) {
        return (AgeCategory)getCurrentSession().load(AgeCategory.class, id.intValue());
    }

    @Override
    public AgeCategory getByTitle(String title) {
        Query q = getCurrentSession().createQuery("from AgeCategory ac where ac.category = :category");
        q.setParameter("category", title);
        AgeCategory result = (AgeCategory)q.uniqueResult();
        return result;
    }

    @Override
    public void save(AgeCategory ageCategory) {
        getCurrentSession().save(ageCategory);
    }
}
