package org.nop.eshop.service;

import org.nop.eshop.model.User;

import java.util.List;

public interface UserService {

    User getUser(String login);

    List<User> getAll();
    
}
