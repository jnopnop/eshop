package org.nop.eshop.web.controller;

import org.jboss.logging.Logger;
import org.nop.eshop.service.MovieService;
import org.nop.eshop.web.model.MovieWeb;
import org.nop.eshop.web.model.PagerResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@Controller
public class HomeController {

    private static Logger LOG = Logger.getLogger(HomeController.class);

    @Autowired
    private MovieService movieService;

	@RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
	public String home(@RequestParam(value = "q", required = false) String query, ModelMap model) throws IOException, ParseException {
        List<MovieWeb> result = movieService.search(1, 20);
        model.addAttribute("currPage", 1);
        model.addAttribute("movies", result);
        LOG.info("###################################We have [" + result.size() + "] movies");
        return "index";
    }

    @RequestMapping(value = "/test.html", method = RequestMethod.GET)
    public String scraper() {
        return "test";
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

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String accessDenied(ModelMap model) {
        return "403";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String advancedSearch() {
        return "search";
    }

//    @RequestMapping(value = "/movies", method = RequestMethod.GET)
//    public String globalSearch(@RequestParam(value = "q", required = false) String query,
//                               ModelMap model) {
//        //List<MovieWeb> searchResults = movieService.fullTextSearch(query);
//        //model.addAttribute("searchResults", searchResults);
//        return "search";
//    }

    @RequestMapping(value = "/movies", method = RequestMethod.GET)
    public String getMovies(@RequestParam(value = "p", required = false) Integer p, ModelMap model) {
        PagerResult<MovieWeb> result = movieService.getPaginated(p);
        model.addAttribute("movies", result);
        return "index";
    }

    @RequestMapping(value = "/movie/{id}", method = RequestMethod.GET)
    public String moviePage(@PathVariable("id") long id, ModelMap model) {
        MovieWeb movie = movieService.getById(id);
        if (movie == null) {
            return "redirect:/404";
        }

        model.addAttribute("movie", movie);
        return "movie";
    }
}