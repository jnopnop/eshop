package org.nop.eshop.web.controller;

import org.nop.eshop.model.Person;
import org.nop.eshop.service.MovieService;
import org.nop.eshop.web.model.AjaxResult;
import org.nop.eshop.web.model.PagerResult;
import org.nop.eshop.web.model.PersonWeb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class PersonController {

    @Autowired
    private MovieService movieService;

    @RequestMapping(value = "/persons", method = RequestMethod.GET)
    public String searchPersons(@RequestParam(value = "p", required = false) Integer page,
                                @RequestParam(value = "q", required = false) String q, ModelMap model) {
        PagerResult<PersonWeb> result = movieService.searchPersons(q, page);
        model.addAttribute("query", q);
        if (page != null && page > result.getLastPage()) {
            result.setCurrPage(result.getLastPage());
        } else if (page != null && page < 1) {
            result.setCurrPage(1);
        } else if (page != null) {
            result.setCurrPage(page);
        }
        model.addAttribute("persons", result);
        return "persons";
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

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/persons/{id}", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult getPerson(@PathVariable("id") Long id) {
        return new AjaxResult(movieService.getPerson(id), true);
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/persons", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult createPerson(@RequestBody PersonWeb personWeb) {
        Person person = movieService.createPerson(personWeb);
        if (person == null) {
            return new AjaxResult(false);
        }

        AjaxResult result = new AjaxResult(true);
        Map<String, String> responseData = new HashMap<>();
        responseData.put("redirect", "/person/" + person.getId());
        result.setData(responseData);
        return result;
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/persons", method = RequestMethod.PUT)
    @ResponseBody
    public AjaxResult editPerson(@RequestBody PersonWeb pw) {
        movieService.updatePerson(pw);
        return new AjaxResult(true);
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/persons/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public AjaxResult deletePerson(@PathVariable("id") Long id) {
        movieService.deletePerson(id);
        return new AjaxResult(true);
    }
}
