package org.nop.eshop.web.controller;

import org.nop.eshop.service.ImageService;
import org.nop.eshop.service.MovieService;
import org.nop.eshop.service.UserService;
import org.nop.eshop.web.model.AjaxResult;
import org.nop.eshop.web.model.UserWeb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class ImageController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/pic/{etype}/{name}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getImage(@PathVariable("etype") String etype, @PathVariable("name") String name) throws IOException {
        return imageService.getImage(etype, name);
    }

    @Secured(UserService.ROLE_USER)
    @RequestMapping(value = "/pic/users/me", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getMyImage() throws IOException {
        UserWeb currentUser = userService.getUserInfo(getAuthentication());
        return imageService.getImage(currentUser.getImage());
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "/pic/{ptype}/{etype}/{id}", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult handleFormUpload(@PathVariable("ptype") String ptype,
                                       @PathVariable("etype") String etype,
                                       @PathVariable("id") Long id,
                                       @RequestParam("files[]") List<MultipartFile> files) throws IOException {
        switch (etype) {
            case ImageService.ENTITY_MOVIE:
                movieService.addMovieImages(files, id, ptype);
                break;
            case ImageService.ENTITY_PERSON:
                movieService.addPersonImages(files, id, ptype);
                break;
            case ImageService.ENTITY_NEWS:
                movieService.addNewsImage(files, id, ptype);
                break;
            case ImageService.ENTITY_USER:
                userService.updateUserImage(files, getAuthentication(), ptype);
                break;
            default:
                return new AjaxResult(false);
        }

        return new AjaxResult(true);
    }



    @RequestMapping(value = "/pic/{etype}/{name}", method = RequestMethod.DELETE)
    @ResponseBody
    public AjaxResult deleteImage(@PathVariable("etype") String etype,
                                  @PathVariable("name") String name) throws IOException {
        imageService.deleteImage(etype, name);
        return new AjaxResult(true);
    }

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
