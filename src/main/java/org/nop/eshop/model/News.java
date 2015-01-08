package org.nop.eshop.model;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "news")
@Indexed
public class News {

    @Id
    @GeneratedValue
    private Long id;

    @Field
    private String title;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "image_container", joinColumns = {
            @JoinColumn(name = "entity_id")}, inverseJoinColumns = {
            @JoinColumn(name = "images_id")
    })
    private List<Image> images = new ArrayList<>();

    @Field
    private String contents;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdOn", insertable=false)
    private Date createdOn;

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

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
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
}
