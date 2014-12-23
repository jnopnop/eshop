package org.nop.eshop.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.nop.eshop.model.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ImageDAOImpl implements ImageDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Image get(Long id) {
        return (Image) getCurrentSession().load(Image.class, id);
    }

    @Override
    public List<Image> get() {
        return (List) getCurrentSession().createQuery("from Image").list();
    }

    @Override
    public void saveOrUpdate(Image i) {
        getCurrentSession().saveOrUpdate(i);
    }
}
