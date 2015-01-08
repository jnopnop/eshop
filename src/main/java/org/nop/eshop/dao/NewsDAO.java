package org.nop.eshop.dao;

import org.nop.eshop.model.News;
import org.nop.eshop.web.model.PagerResult;

import java.util.List;


public interface NewsDAO {
    List<News> get();

    News get(Long id);

    List<News> search(String q, int page, Integer pageSize, PagerResult<?> pager);

    void save(News t);

    void update(News p);

    void delete(Long id);
}
