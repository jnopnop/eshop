package org.nop.eshop.web.controller;

import org.nop.eshop.service.UserService;
import org.nop.eshop.web.model.AjaxResult;
import org.nop.eshop.web.model.UserWeb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

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

    @Secured("ROLE_USER")
    @RequestMapping(value = "/users/me", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult getUserInfo() {
        return new AjaxResult(userService.getUserInfo(getAuthentication()), true);
    }

    @RequestMapping(value = "/users/${id}", method = RequestMethod.PUT)
    @ResponseBody
    public AjaxResult updateUserInfo(@PathVariable(value = "id") Long id, @RequestBody UserWeb userInfo) {
        userInfo.setId(id);
        userService.updateUser(userInfo);
        return new AjaxResult(true);
    }

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
