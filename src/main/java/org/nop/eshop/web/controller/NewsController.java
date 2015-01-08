package org.nop.eshop.web.controller;

import org.nop.eshop.service.MovieService;
import org.nop.eshop.web.model.AjaxResult;
import org.nop.eshop.web.model.NewsWeb;
import org.nop.eshop.web.model.PagerResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class NewsController {

    @Autowired
    private MovieService movieService;

    @RequestMapping(value = { "/", "/index", "/news"}, method = RequestMethod.GET)
    public String searchNews(@RequestParam(value = "p", required = false) Integer page,
                             @RequestParam(value = "q", required = false) String q, ModelMap model) {
        PagerResult<NewsWeb> result = movieService.searchNews(q, page);
        model.addAttribute("query", q);
        if (page != null && page > result.getLastPage()) {
            result.setCurrPage(result.getLastPage());
        } else if (page != null && page < 1) {
            result.setCurrPage(1);
        } else if (page != null) {
            result.setCurrPage(page);
        }
        model.addAttribute("news", result);
        return "news";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/admin/news/{id}", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult getNews(@PathVariable("id") Long id) {
        return new AjaxResult(movieService.getNewsById(id), true);
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/admin/news", method = RequestMethod.POST)
    public @ResponseBody AjaxResult createNews(@RequestParam(value = "title") String title,
                                               @RequestParam(value = "contents") String contents,
                                               @RequestParam(value = "mainImage") MultipartFile mainImage) throws IOException {
        NewsWeb nw = new NewsWeb();
        nw.setTitle(title);
        nw.setContents(contents);
        nw.setMainImageFile(mainImage);
        movieService.createNews(nw);
        return new AjaxResult(true);
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/admin/news", method = RequestMethod.PUT)
    @ResponseBody
    public AjaxResult editNews(@RequestBody NewsWeb news) {
        movieService.updateNews(news);
        return new AjaxResult(true);
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/admin/news/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public AjaxResult deleteNews(@PathVariable("id") Long id) {
        movieService.deleteNews(id);
        return new AjaxResult(true);
    }
}
