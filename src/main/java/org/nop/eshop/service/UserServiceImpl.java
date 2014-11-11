package org.nop.eshop.service;

import org.nop.eshop.dao.UserDAO;
import org.nop.eshop.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    public User getUser(String login) {
        return userDAO.getUser(login);
    }

    @Transactional
    public List<User> getAll() {
        return userDAO.getAll();
    }
}
