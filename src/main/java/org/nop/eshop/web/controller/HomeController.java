package org.nop.eshop.web.controller;

import org.apache.commons.lang.StringUtils;
import org.jboss.logging.Logger;
import org.nop.eshop.service.ImageService;
import org.nop.eshop.service.MovieService;
import org.nop.eshop.web.model.MovieWeb;
import org.nop.eshop.web.model.PagerResult;
import org.nop.eshop.web.model.PersonWeb;
import org.nop.eshop.web.model.SearchMovieForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

@Controller
public class HomeController {

    private static Logger LOG = Logger.getLogger(HomeController.class);

    @Autowired
    private MovieService movieService;

    @Autowired
    private ImageService imageService;

    @RequestMapping(value = { "/", "/index", "/movies"}, method = RequestMethod.GET)
    public String getMovies(@RequestParam(value = "p", required = false) Integer page,
                            @RequestParam(value = "q", required = false) String q,
                            ModelMap model) {
        PagerResult<MovieWeb> result;
        if (StringUtils.isNotEmpty(q)) {
            result = movieService.search(q, page);
            model.addAttribute("query", q);
        } else {
            result = movieService.getPaginated(page);
        }
        if (page != null && page > result.getLastPage()) {
            result.setCurrPage(result.getLastPage());
        }

        model.addAttribute("movies", result);
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
        return "admin";
    }

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String accessDenied() {
        return "403";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String advancedSearch() {
        return "search";
    }

    @RequestMapping(value = "/fulltextsearch", method = RequestMethod.GET)
    public String fullTextSearch(ModelMap model) {
        Map<String, String> years = new TreeMap<>();
        years.put("", "select year...");
        for (int i = 1980; i < 2015; i++) years.put(String.valueOf(i), String.valueOf(i));
        model.addAttribute("years", years);

        Set<String> countries = new TreeSet<>(movieService.getCountries().values());
        countries.add("");
        model.addAttribute("countries", countries);
        model.addAttribute("ageCategories", movieService.getCategories());
        model.addAttribute("genres", movieService.getGenres().values());

        model.addAttribute("sm", new SearchMovieForm());
        return "search";
    }

    @RequestMapping(value = "/movies/search", method = RequestMethod.GET)
    public String advancedSearch(
            @RequestParam(value = "p", required = false) Integer page,
            @ModelAttribute SearchMovieForm sm, ModelMap model) throws ParseException {
        PagerResult<MovieWeb> result = movieService.search(sm, page);
        if (page != null && page > result.getLastPage()) {
            result.setCurrPage(result.getLastPage());
        }

        Set<String> years = new TreeSet<>();
        years.add("");
        for (int i = 1990; i < 2015; i++) years.add(String.valueOf(i));
        model.addAttribute("years", years);

        Set<String> countries = new TreeSet<>(movieService.getCountries().values());
        countries.add("");
        model.addAttribute("countries", countries);
        model.addAttribute("ageCategories", movieService.getCategories());
        model.addAttribute("genres", movieService.getGenres().values());
        model.addAttribute("advanced", true);
        model.addAttribute("movies", result);
        model.addAttribute("sm", sm);
        return "index";
    }

    @RequestMapping(value = "/movie/{id}.json", method = RequestMethod.GET)
    public @ResponseBody MovieWeb getMovie(@PathVariable("id") long id) {
        return movieService.getById(id);
    }

    @RequestMapping(value = "/movie/addmovie", method = RequestMethod.GET)
    public String addNewMovie() {
        return "403";
    }

    @RequestMapping(value = "/pic/{etype}/{name}", method = RequestMethod.GET)
    @ResponseBody
    public byte[] getImage(@PathVariable("etype") String etype, @PathVariable("name") String name) throws IOException {
        return imageService.getImage(etype, name);
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

    @RequestMapping(value = "/person/{id}", method = RequestMethod.GET)
    public String personPage(@PathVariable("id") long id, ModelMap model) {
        PersonWeb person = movieService.getPerson(id);
        if (person == null) {
            return "redirect:/404";
        }
        model.addAttribute("person", person);
        return "person";
    }
}