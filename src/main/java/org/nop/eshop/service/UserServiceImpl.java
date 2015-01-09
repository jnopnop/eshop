package org.nop.eshop.service;

import org.nop.eshop.dao.RoleDAO;
import org.nop.eshop.dao.UserDAO;
import org.nop.eshop.model.Role;
import org.nop.eshop.model.User;
import org.nop.eshop.utils.GMConverter;
import org.nop.eshop.web.model.UserWeb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private RoleDAO roleDAO;

    public User getUser(String login) {
        return userDAO.getUser(login);
    }

    @Transactional
    public List<User> getAll() {
        return userDAO.getAll();
    }

    @Override
    @Transactional
    public void updateUserImage(List<MultipartFile> images, Long id, String ptype) throws IOException {
        //TODO: Remove old image if exists
    }

    @Override
    @Transactional
    public void createUser(String fullname, String email, String password) {
        Assert.isTrue(email != null && !email.trim().isEmpty(), "Email should not be empty");
        Assert.isTrue(password != null && !password.trim().isEmpty(), "Password should not be empty");

        User u = new User();
        u.setFullname(fullname);
        u.setEmail(email);
        u.setPassword(password);
        u.getRoles().add(getRole(ROLE_USER));
        userDAO.save(u);
    }

    @Override
    @Transactional(readOnly = true)
    public UserWeb getUserInfo(Authentication authentication) {
        org.springframework.security.core.userdetails.User u = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        User ssUser = userDAO.getUser(u.getUsername());
        return GMConverter.toUserWeb(ssUser);
    }

    @Override
    @Transactional
    public void updateUser(UserWeb userInfo) {
        User toUpdate = userDAO.get(userInfo.getId());
        Assert.notNull(toUpdate, "This user does not exist!");

        toUpdate.setFullname(userInfo.getFullname());
        toUpdate.setEmail(userInfo.getEmail());
        toUpdate.setPassword(userInfo.getPassword());
        userDAO.update(toUpdate);
    }

    private Role getRole(String role) {
        return roleDAO.getRole(role);
    }
}
