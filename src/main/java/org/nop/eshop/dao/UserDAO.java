package org.nop.eshop.dao;

import org.nop.eshop.model.User;

import java.util.List;

public interface UserDAO {
    User getUser(String login);
    List<User> getAll();
    void createUser(final User user);
    void save(User u);
    void update(User u);

    User get(Long id);
}
