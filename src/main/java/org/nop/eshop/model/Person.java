package org.nop.eshop.model;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;
import java.util.*;


@Entity
@Table(name="persons")
@Indexed
public class Person {

    @Id
    @GeneratedValue
    private Long id;

    @Field
    private String fullname;

    @Field
    private Date birthdate;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "image_container", joinColumns = {
            @JoinColumn(name = "entity_id")}, inverseJoinColumns = {
            @JoinColumn(name = "images_id")
    })
    private List<Image> images = new ArrayList<>();

    @Column(name="imdb_id")
    private String imdbId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.person")
    private Set<MoviePerson> moviePersons = new HashSet<>(0);

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Set<MoviePerson> getMoviePersons() {
        return moviePersons;
    }

    public void setMoviePersons(Set<MoviePerson> moviePersons) {
        this.moviePersons = moviePersons;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }
}

