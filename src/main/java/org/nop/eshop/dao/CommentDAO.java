package org.nop.eshop.dao;

import org.nop.eshop.model.Comment;

import java.util.List;

/**
 * Created by nop on 30/09/14.
 */
public interface CommentDAO {
    List<Comment> getAll();
    Comment getById(Long id);
    Comment getByIMDBId(Long imdbId);
    Comment getByTitle(String title);
    void save(Comment t);
    void deleteById(Long id);
}
