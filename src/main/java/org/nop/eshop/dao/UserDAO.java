package org.nop.eshop.dao;

import org.nop.eshop.model.User;
import org.nop.eshop.web.model.PagerResult;
import org.nop.eshop.web.model.PagingResult;
import org.nop.eshop.web.model.UserWeb;

import java.util.Collection;
import java.util.List;

public interface UserDAO {
    User getUser(String login);
    Long count();
    List<User> getAll();
    void createUser(final User user);
    void save(User u);
    void update(User u);

    User get(Long id);

    Collection<User> search(String q, int i, Integer pageSize, PagerResult<UserWeb> pager);

    PagingResult<UserWeb> search(PagingResult<UserWeb> pagingResult);

    void delete(Long id);
}
