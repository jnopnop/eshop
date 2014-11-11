package org.nop.eshop.web.controller;

import org.jboss.logging.Logger;
import org.nop.eshop.service.MovieService;
import org.nop.eshop.web.model.MovieWeb;
import org.nop.eshop.web.model.SimpleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class AdminController {
    private static Logger LOG = Logger.getLogger(AdminController.class);

    @Autowired
    private MovieService movieService;

    @RequestMapping(value = "/admin/genres", method = RequestMethod.GET)
    public @ResponseBody Map<Long, String> allGenres() {
        Map<Long, String> result = movieService.getGenres();
        return result;
    }

    @RequestMapping(value = "/admin/categories", method = RequestMethod.GET)
    public @ResponseBody Map<Long, String> allCategories() {
        Map<Long, String> result = movieService.getCategories();
        return result;
    }

    @RequestMapping(value = "/admin/countries", method = RequestMethod.GET)
    public @ResponseBody
    Map<Long, String> allCountries() {
        Map<Long, String> result = movieService.getCountries();
        return result;
    }

    @RequestMapping(value = "/admin/persons", method = RequestMethod.GET)
    public @ResponseBody Map<Long, String> allPersons() {
        Map<Long, String> result = movieService.getPersons();
        return result;
    }

    @RequestMapping(value = "/movie/add", method = RequestMethod.POST, consumes="application/json")
    public void insertMovie(@RequestBody MovieWeb movie) {
        movieService.createMovie(movie);
    }

    @RequestMapping(value = "/model/add", method = RequestMethod.POST, consumes="application/json")
    public void insertModel(@RequestBody SimpleModel model) {
        //TODO: save it
        //@ResponseStatus(value = HttpStatus.CREATED)
        String s = model.getName();
    }

    //"{"title":"ttt","description":"ddd","duration":450,"releaseDate":"10/10/2014","rating":"8.3","imageURL":"sadsads.dsd","imdbId":"5465657","ageCategory":{"id":26,"category":"category"},"genres":{"53":"53","56":"56"},"countries":{"1":"1","2":"2"},"directors":{"187":"187"},"writers":{"189":"189"},"actors":{"192":"192","194":"194"}}"
}
