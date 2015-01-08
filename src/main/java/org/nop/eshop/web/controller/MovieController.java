package org.nop.eshop.web.controller;

import org.apache.commons.lang.StringUtils;
import org.nop.eshop.model.Movie;
import org.nop.eshop.service.MovieService;
import org.nop.eshop.web.model.AjaxResult;
import org.nop.eshop.web.model.MovieWeb;
import org.nop.eshop.web.model.PagerResult;
import org.nop.eshop.web.model.SearchMovieForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

@Controller
public class MovieController {

    public static final String GENRES = "genres";
    public static final String CATEGORIES = "categories";
    public static final String COUNTRIES = "countries";
    public static final String PERSONS = "persons";

    @Autowired
    private MovieService movieService;

    @RequestMapping(value = { "/movies"}, method = RequestMethod.GET)
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
        return "movies";
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
        return "movies";
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

    @RequestMapping(value = "/movie/{id}.json", method = RequestMethod.GET)
    public @ResponseBody MovieWeb getMovie(@PathVariable("id") long id) {
        return movieService.getById(id);
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/admin/movies/{id}", method = RequestMethod.GET)
    public @ResponseBody AjaxResult getMovie(@PathVariable("id") Long id) {
        return new AjaxResult(movieService.getById(id), true);
    }

    @RequestMapping(value = "/admin/movies/info", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> fullMovieInfo() {
        Map<String, Object> result = new HashMap<>();
        result.put(GENRES, movieService.getGenres());
        result.put(CATEGORIES, movieService.getCategories());
        result.put(COUNTRIES, movieService.getCountries());
        result.put(PERSONS, movieService.getPersons());
        return result;
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/admin/movies", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult createMovie(@RequestBody MovieWeb movieWeb) {
        Movie movie = movieService.createMovie(movieWeb);
        if (movie == null) {
            return new AjaxResult(false);
        }

        AjaxResult result = new AjaxResult(true);
        Map<String, String> responseData = new HashMap<>();
        responseData.put("redirect", "/movie/"+movie.getId());
        result.setData(responseData);
        return result;
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/admin/movies", method = RequestMethod.PUT)
    public @ResponseBody AjaxResult editMovie(@RequestBody MovieWeb m) {
        movieService.updateMovie(m);
        return new AjaxResult(true);
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/admin/movies/{id}", method = RequestMethod.DELETE)
    public @ResponseBody AjaxResult deleteMovie(@PathVariable("id") Long id) {
        movieService.deleteMovie(id);
        return new AjaxResult(true);
    }
}
