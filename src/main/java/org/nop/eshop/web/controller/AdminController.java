package org.nop.eshop.web.controller;

import org.jboss.logging.Logger;
import org.nop.eshop.service.MovieService;
import org.nop.eshop.web.model.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdminController {
    private static Logger LOG = Logger.getLogger(AdminController.class);
    public static final String GENRES = "genres";
    public static final String CATEGORIES = "categories";
    public static final String COUNTRIES = "countries";
    public static final String PERSONS = "persons";

    @Autowired
    private MovieService movieService;

//    @RequestMapping(value = "/admin/movies/info", method = RequestMethod.GET)
//    public @ResponseBody Map<String, Object> fullMovieInfo() {
//        Map<String, Object> result = new HashMap<>();
//        result.put(GENRES, movieService.getGenres());
//        result.put(CATEGORIES, movieService.getCategories());
//        result.put(COUNTRIES, movieService.getCountries());
//        result.put(PERSONS, movieService.getPersons());
//        return result;
//    }

//    @RequestMapping(value = "/admin/movies", method = RequestMethod.POST)
//    public @ResponseBody AjaxResult createMovie(@RequestBody MovieWeb movieWeb) {
//        Movie movie = movieService.createMovie(movieWeb);
//        if (movie == null) {
//            return new AjaxResult(false);
//        }
//
//        AjaxResult result = new AjaxResult(true);
//        Map<String, String> responseData = new HashMap<>();
//        responseData.put("redirect", "/movie/"+movie.getId());
//        result.setData(responseData);
//        return result;
//    }

//    @RequestMapping(value = "/admin/movies", method = RequestMethod.GET)
//    public String searchMovie(@RequestParam(value = "p", required = false) Integer page,
//                              @ModelAttribute SearchMovieForm sm, ModelMap model) throws ParseException {
//        PagerResult<MovieWeb> result = movieService.search(sm, page);
//        if (page != null && page > result.getLastPage()) {
//            result.setCurrPage(result.getLastPage());
//        }
//
//        Set<String> years = new TreeSet<>();
//        years.add("");
//        for (int i = 1990; i < 2015; i++) years.add(String.valueOf(i));
//        model.addAttribute("years", years);
//
//        Set<String> countries = new TreeSet<>(movieService.getCountries().values());
//        countries.add("");
//        model.addAttribute("countries", countries);
//        model.addAttribute("ageCategories", movieService.getCategories());
//        model.addAttribute("genres", movieService.getGenres().values());
//        model.addAttribute("movies", result);
//        model.addAttribute("sm", sm);
//        model.addAttribute("m", new MovieWeb());
//        return "admin_movies";
//    }

//    @RequestMapping(value = "/admin/movies/{id}", method = RequestMethod.GET)
//    public @ResponseBody AjaxResult getMovie(@PathVariable("id") Long id) {
//        return new AjaxResult(movieService.getById(id), true);
//    }

//    @RequestMapping(value = "/admin/movies", method = RequestMethod.PUT)
//    public @ResponseBody AjaxResult editMovie(@RequestBody MovieWeb m) {
//        movieService.updateMovie(m);
//        return new AjaxResult(true);
//    }
//
//    @RequestMapping(value = "/admin/movies/{id}", method = RequestMethod.DELETE)
//    public @ResponseBody AjaxResult deleteMovie(@PathVariable("id") Long id) {
//        movieService.deleteMovie(id);
//        return new AjaxResult(true);
//    }

//    @RequestMapping(value = "/admin/persons", method = RequestMethod.GET)
//    public String searchPersons(@RequestParam(value = "p", required = false) Integer page,
//                             @RequestParam(value = "q", required = false) String q, ModelMap model) {
//        PagerResult<PersonWeb> result = movieService.searchPersons(q, page);
//        model.addAttribute("query", q);
//        if (page != null && page > result.getLastPage()) {
//            result.setCurrPage(result.getLastPage());
//        } else if (page != null && page < 1) {
//            result.setCurrPage(1);
//        } else if (page != null) {
//            result.setCurrPage(page);
//        }
//        model.addAttribute("persons", result);
//        return "admin_persons";
//    }
//
//    @RequestMapping(value = "/admin/persons/{id}", method = RequestMethod.GET)
//    public @ResponseBody AjaxResult getPerson(@PathVariable("id") Long id) {
//        return new AjaxResult(movieService.getPerson(id), true);
//    }
//
//    @RequestMapping(value = "/admin/persons", method = RequestMethod.PUT)
//    public @ResponseBody AjaxResult editPerson(@RequestBody PersonWeb pw) {
//        movieService.updatePerson(pw);
//        return new AjaxResult(true);
//    }
//
//    @RequestMapping(value = "/admin/persons/{id}", method = RequestMethod.DELETE)
//    public @ResponseBody AjaxResult deletePerson(@PathVariable("id") Long id) {
//        movieService.deletePerson(id);
//        return new AjaxResult(true);
//    }

    @RequestMapping(value = "/admin/comments/{id}", method = RequestMethod.DELETE)
    public @ResponseBody AjaxResult deleteComment(@PathVariable("id") Long id) {
        movieService.deleteComment(id);
        return new AjaxResult(true);
    }

//    @RequestMapping(value = "/admin/news", method = RequestMethod.GET)
//    public String searchNews(@RequestParam(value = "p", required = false) Integer page,
//                                @RequestParam(value = "q", required = false) String q, ModelMap model) {
//        PagerResult<NewsWeb> result = movieService.searchNews(q, page);
//        model.addAttribute("query", q);
//        if (page != null && page > result.getLastPage()) {
//            result.setCurrPage(result.getLastPage());
//        } else if (page != null && page < 1) {
//            result.setCurrPage(1);
//        } else if (page != null) {
//            result.setCurrPage(page);
//        }
//        model.addAttribute("news", result);
//        return "admin_news";
//    }

//    @RequestMapping(value = "/admin/news/{id}", method = RequestMethod.GET)
//    public @ResponseBody AjaxResult getNews(@PathVariable("id") Long id) {
//        return new AjaxResult(movieService.getNewsById(id), true);
//    }

//    @RequestMapping(value = "/admin/news", method = RequestMethod.POST)
//    public @ResponseBody AjaxResult createNewss(@RequestParam(value = "title") String title, @RequestParam(value = "contents") String contents,  @RequestParam(value = "mainImage") MultipartFile mainImage) throws IOException {
//        NewsWeb nw = new NewsWeb();
//        nw.setTitle(title);
//        nw.setContents(contents);
//        nw.setMainImageFile(mainImage);
//        movieService.createNews(nw);
//        return new AjaxResult(true);
//    }

//    @RequestMapping(value = "/admin/news", method = RequestMethod.PUT)
//    public @ResponseBody AjaxResult editNews(@RequestBody NewsWeb news) {
//        //movieService.updateMovie(m);
//        movieService.updateNews(news);
//        return new AjaxResult(true);
//    }

//    @RequestMapping(value = "/admin/news/{id}", method = RequestMethod.DELETE)
//    public @ResponseBody AjaxResult deleteNews(@PathVariable("id") Long id) {
//        movieService.deleteMovie(id);
//        return new AjaxResult(true);
//    }

    //"{"title":"ttt","description":"ddd","duration":450,"releaseDate":"10/10/2014","rating":"8.3","imageURL":"sadsads.dsd","imdbId":"5465657","ageCategory":{"id":26,"category":"category"},"genres":{"53":"53","56":"56"},"countries":{"1":"1","2":"2"},"directors":{"187":"187"},"writers":{"189":"189"},"actors":{"192":"192","194":"194"}}"
}
