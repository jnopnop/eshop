package org.nop.eshop.web.model;


import org.nop.eshop.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.Date;

public class NewsWeb {
    private Long id;
    private String title;
    private String mainImage;
    private String contents;
    private Date createdOn;
    private Collection<? extends Image> images;
    private MultipartFile mainImageFile;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Collection<? extends Image> getImages() {
        return images;
    }

    public void setImages(Collection<? extends Image> images) {
        this.images = images;
    }

    public MultipartFile getMainImageFile() {
        return mainImageFile;
    }

    public void setMainImageFile(MultipartFile mainImageFile) {
        this.mainImageFile = mainImageFile;
    }
}
