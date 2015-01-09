package org.nop.eshop.service;

import org.nop.eshop.model.User;
import org.nop.eshop.web.model.UserWeb;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {

    User getUser(String login);

    List<User> getAll();

    void updateUserImage(List<MultipartFile> images, Long id, String ptype) throws IOException;

    void createUser(String fullname, String email, String password);

    UserWeb getUserInfo(Authentication authentication);

    void updateUser(UserWeb userInfo);
}
