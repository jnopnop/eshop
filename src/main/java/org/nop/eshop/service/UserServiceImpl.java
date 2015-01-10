package org.nop.eshop.service;

import org.nop.eshop.dao.RoleDAO;
import org.nop.eshop.dao.UserDAO;
import org.nop.eshop.model.Image;
import org.nop.eshop.model.Role;
import org.nop.eshop.model.User;
import org.nop.eshop.utils.GMConverter;
import org.nop.eshop.web.model.PageInfo;
import org.nop.eshop.web.model.PagerResult;
import org.nop.eshop.web.model.PagingResult;
import org.nop.eshop.web.model.UserWeb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private RoleDAO roleDAO;

    @Autowired
    private ImageService imageService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public User getUser(String login) {
        return userDAO.getUser(login);
    }

    @Transactional
    public List<User> getAll() {
        return userDAO.getAll();
    }

    @Override
    @Transactional
    public void updateUserImage(List<MultipartFile> images, Authentication authentication, String ptype) throws IOException {
        User user = getUserFromAuthentication(authentication);
        Assert.notNull(user, "Current user was not found!");

        if (ImageService.IMAGE_TYPE_PRIMARY.equals(ptype)) {
            Image i = imageService.upload(images.get(0).getBytes(), ImageService.ENTITY_USER, ImageService.IMAGE_TYPE_PRIMARY);
            user.getImages().clear();
            user.getImages().add(i);
        } else {
            throw new UnsupportedOperationException("Can not save carousel image for user!");
        }

        userDAO.update(user);
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
        return GMConverter.toUserWeb(getUserFromAuthentication(authentication));
    }

    @Override
    @Transactional
    public void updateUser(Authentication authentication, UserWeb userInfo) {
        User toUpdate = getUserFromAuthentication(authentication);
        Assert.notNull(toUpdate, "This user does not exist!");

        toUpdate.setFullname(userInfo.getFullname());
        toUpdate.setEmail(userInfo.getEmail());
        toUpdate.setPassword(userInfo.getPassword());
        userDAO.update(toUpdate);

        Authentication request = new UsernamePasswordAuthenticationToken(toUpdate.getEmail(), toUpdate.getPassword());
        Authentication result = authenticationManager.authenticate(request);
        SecurityContextHolder.getContext().setAuthentication(result);
    }

    @Override
    @Transactional
    public PagerResult<UserWeb> search(String q, Integer p) {
        p = currentPage(p);
        PagerResult<UserWeb> pager = new PagerResult<>();
        pager.setCurrPage(p);
        pager.setResults(GMConverter.toWebUsers(userDAO.search(q, (p - 1) * PagerResult.PAGE_SIZE, PagerResult.PAGE_SIZE, pager)));
        pager.setLastPage(lastPage(userDAO.count()));
        return pager;
    }

    @Override
    @Transactional
    public PagingResult<UserWeb> search(PageInfo pageInfo) {
        PagingResult<UserWeb> pagingResult = new PagingResult<>(pageInfo);
        return userDAO.search(pagingResult);
    }

    @Override
    @Transactional
    public void setAdmin(Long id, boolean admin) {
        User user = userDAO.get(id);
        Assert.notNull(user, "This user does not exist!");

        Role role_admin = getRole(ROLE_ADMIN);
        if (admin && !user.getRoles().contains(role_admin)) {
            user.getRoles().add(role_admin);
            userDAO.update(user);
        } else if (!admin && user.getRoles().contains(role_admin)) {
            user.getRoles().remove(role_admin);
            userDAO.update(user);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userDAO.delete(id);
    }


//    @Transactional
//    public PagerResult<UserWeb> search(String q, Integer p, String sortingField) {
//        p = currentPage(p);
//        PagerResult<UserWeb> pager = new PagerResult<>();
//        pager.setCurrPage(p);
//        pager.setResults(GMConverter.toWebUsers(userDAO.search(q, (p - 1) * PagerResult.PAGE_SIZE, PagerResult.PAGE_SIZE, pager)));
//        //SortType
//        pager.setLastPage(lastPage(pager.getMaxResults()));
//        return pager;
//    }

    private Role getRole(String role) {
        return roleDAO.getRole(role);
    }

    private User getUserFromAuthentication(Authentication authentication) {
        try {
            org.springframework.security.core.userdetails.User u = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
            return userDAO.getUser(u.getUsername());
        } catch (Exception e) {
            //User either isn't logged in or doesn't exist in DB
            return null;
        }
    }

    private Integer currentPage(Integer p) {
        return  p != null ? Math.max(1, p) : 1;
    }

    private Integer lastPage(Long total) {
        return (int)Math.ceil(total / Float.valueOf(PagerResult.PAGE_SIZE));
    }
}
