package org.nop.eshop.dao;

import java.util.List;

public interface MySQLGroovyMovieDAO {
    <T> List<T> getAll(T t, Class<T> clazz);
}
