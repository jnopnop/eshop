package org.nop.eshop.service;

import org.nop.eshop.model.User;
import org.nop.eshop.web.model.PageInfo;
import org.nop.eshop.web.model.PagerResult;
import org.nop.eshop.web.model.PagingResult;
import org.nop.eshop.web.model.UserWeb;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {

    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";

    User getUser(String login);

    List<User> getAll();

    void updateUserImage(List<MultipartFile> images, Authentication authentication, String ptype) throws IOException;

    void createUser(String fullname, String email, String password);

    UserWeb getUserInfo(Authentication authentication);

    void updateUser(Authentication authentication, UserWeb userInfo);

    PagerResult<UserWeb> search(String q, Integer page);

    PagingResult<UserWeb> search(PageInfo pageInfo);

    void setAdmin(Long id, boolean admin);

    void delete(Long id);
}
