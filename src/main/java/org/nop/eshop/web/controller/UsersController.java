package org.nop.eshop.web.controller;

import org.nop.eshop.service.UserService;
import org.nop.eshop.web.model.AjaxResult;
import org.nop.eshop.web.model.PagerResult;
import org.nop.eshop.web.model.UserWeb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
public class UsersController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String loginPage(@RequestParam(value = "fullname", required = true) String fullname,
                            @RequestParam(value = "email", required = true) String email,
                            @RequestParam(value = "password", required = true) String password,
                            ModelMap model) {
        try {
            userService.createUser(fullname, email, password);
        } catch (Exception e) {
            return "redirect:/register?error="+e.getMessage();
        }

        return "redirect:/login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerPage() {
        return "register";
    }

    @Secured(UserService.ROLE_USER)
    @RequestMapping(value = "/users/me", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult getUserInfo() {
        return new AjaxResult(userService.getUserInfo(getAuthentication()), true);
    }

    @Secured(UserService.ROLE_USER)
    @RequestMapping(value = "/users/me", method = RequestMethod.PUT)
    @ResponseBody
    public AjaxResult updateUserInfo(@RequestBody UserWeb userInfo) {
        userService.updateUser(getAuthentication(), userInfo);
        return new AjaxResult(true);
    }

    @Secured(UserService.ROLE_ADMIN)
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String searchPersons(@RequestParam(value = "p", required = false) Integer page,
                                @RequestParam(value = "q", required = false) String q,
                                ModelMap model) {
        //PagingResult<UserWeb>   result = userService.search(pageInfo);
        PagerResult<UserWeb> result = userService.search(q, page);
        model.addAttribute("query", q);
        if (page != null && page > result.getLastPage()) {
            result.setCurrPage(result.getLastPage());
        } else if (page != null && page < 1) {
            result.setCurrPage(1);
        } else if (page != null) {
            result.setCurrPage(page);
        }
        model.addAttribute("users", result);
        return "users";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public AjaxResult editPerson(@PathVariable("id") Long id, @RequestParam(value = "admin", required = true) boolean admin) {
        userService.setAdmin(id, admin);
        return new AjaxResult(true);
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public AjaxResult deletePerson(@PathVariable("id") Long id) {
        userService.delete(id);
        return new AjaxResult(true);
    }

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
