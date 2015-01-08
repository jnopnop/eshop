package org.nop.eshop.service;


import org.nop.eshop.model.Image;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

public interface ImageService {
    public static final String IMG_ROOT = "/var/www/eshop/img/";
    public static final String ENTITY_MOVIE = "movies";
    public static final String ENTITY_PERSON = "persons";
    public static final String ENTITY_USER = "users";
    public static final String ENTITY_NEWS = "news";

    public static final String NOT_FOUND = "notfound";

    public static final String IMAGE_TYPE_PRIMARY = "primary";
    public static final String IMAGE_TYPE_CAROUSEL = "carousel";

    byte[] getImage(String entity, String filename) throws FileNotFoundException;
    void update(String type, Long id, byte[] file) throws IOException;
    void delete(String type, Long id) throws IOException;
    Image upload(byte[] file, String forEntity, String ofType) throws IOException;
    void upload(byte[] file, String forEntity, Image image) throws IOException;
    void upload(String externalURL, int timeout, String forEntity, Image image) throws IOException;
    void upload(Map<String, Image> images, int timeout, String forEntity);

    void deleteImage(String etype, String name) throws IOException;
    //String URLFor(Image image, String entity);
}
