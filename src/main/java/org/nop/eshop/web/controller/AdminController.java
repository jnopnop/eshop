package org.nop.eshop.web.controller;

import org.jboss.logging.Logger;
import org.nop.eshop.service.MovieService;
import org.nop.eshop.web.model.AjaxResult;
import org.nop.eshop.web.model.MovieWeb;
import org.nop.eshop.web.model.PagerResult;
import org.nop.eshop.web.model.SearchMovieForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

@Controller
public class AdminController {
    private static Logger LOG = Logger.getLogger(AdminController.class);
    public static final String GENRES = "genres";
    public static final String CATEGORIES = "categories";
    public static final String COUNTRIES = "countries";
    public static final String PERSONS = "persons";

    @Autowired
    private MovieService movieService;

    @RequestMapping(value = "/admin/movies/alldata", method = RequestMethod.GET)
    public @ResponseBody Map<String, Object> fullMovieData() {
        Map<String, Object> result = new HashMap<>();
        result.put(GENRES, movieService.getGenres());
        result.put(CATEGORIES, movieService.getCategories());
        result.put(COUNTRIES, movieService.getCountries());
        result.put(PERSONS, movieService.getPersons());
        return result;
    }

    @RequestMapping(value = "/admin/delete/movie/{id}.json", method = RequestMethod.DELETE)
    public @ResponseBody AjaxResult deleteMovie(@PathVariable("id") Long id) {
        AjaxResult result = new AjaxResult();
        try {
            movieService.deleteMovie(id);
        } catch (Exception e) {
            result.setSuccess(false);
        }
        return result;
    }

    @RequestMapping(value = "/admin/edit/movie", method = RequestMethod.PUT, consumes="application/json")
    public @ResponseBody AjaxResult editMovie(@RequestBody MovieWeb m) {
        AjaxResult result = new AjaxResult();
        try {
            movieService.updateMovie(m);
        } catch (Exception e) {
            result.setSuccess(false);
        }
        return result;
    }

    @RequestMapping(value = "/admin/movie/{id}", method = RequestMethod.GET)
    public @ResponseBody MovieWeb getMovie(@PathVariable("id") Long id) {
        return movieService.getById(id);
    }

    @RequestMapping(value = "/movie/add", method = RequestMethod.POST, consumes="application/json")
    public void insertMovie(@RequestBody MovieWeb movie) {
        movieService.createMovie(movie);
    }

    @RequestMapping(value = "/admin/search/movies", method = RequestMethod.GET)
    public String searchMovies(@RequestParam(value = "p", required = false) Integer page,
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
        model.addAttribute("movies", result);
        model.addAttribute("sm", sm);
        model.addAttribute("m", new MovieWeb());
        return "admin";
    }

    //"{"title":"ttt","description":"ddd","duration":450,"releaseDate":"10/10/2014","rating":"8.3","imageURL":"sadsads.dsd","imdbId":"5465657","ageCategory":{"id":26,"category":"category"},"genres":{"53":"53","56":"56"},"countries":{"1":"1","2":"2"},"directors":{"187":"187"},"writers":{"189":"189"},"actors":{"192":"192","194":"194"}}"
}
