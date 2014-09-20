package org.nop.eshop.service;

import org.nop.eshop.model.User;

public interface UserService {

    User getUser(String login);
    
}
