package org.nop.eshop.model;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


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

    @Column(name="photo_url")
    private String photoURL;

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

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }
}

