package org.nop.eshop.dao;

import org.nop.eshop.model.User;

public interface UserDAO {
    User getUser(String login);
    void createUser(final User user);
}
