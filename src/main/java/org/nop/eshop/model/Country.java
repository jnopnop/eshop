package org.nop.eshop.model;

import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.Field;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="countries")
public class Country {

    @Id
    @GeneratedValue
    private Integer id;

    @Field
    private String title;

    @ManyToMany(cascade=CascadeType.ALL, mappedBy = "countries", fetch = FetchType.LAZY)
    @ContainedIn
    private Set<Movie> movies = new HashSet<>();

    public Country(String title) {
        this.title = title;
    }

    public Country() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }
}
