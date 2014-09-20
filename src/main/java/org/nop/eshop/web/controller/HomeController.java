package org.nop.eshop.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

	@RequestMapping(value = { "/", "/index", "/logout" }, method = RequestMethod.GET)
	public String home(ModelMap model) {
        model.addAttribute("message", "Welcome to eshop!");
        return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            ModelMap model) {

        if (error != null) {
            model.addAttribute("error", "Invalid username and password!");
        }
        if (logout != null) {
            model.addAttribute("msg", "You\'ve been successfully logged out");
        }

        return "login";
    }

    @RequestMapping(value = "/admin**", method = RequestMethod.GET)
    public String adminHome(ModelMap model) {
        model.addAttribute("information", "This page should be visible only to administration!");
        return "admin";
    }

}