package org.nop.eshop.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="genres")
public class Genre {

    @Id
    @GeneratedValue
    private Integer id;

    private String title;

    @ManyToMany(cascade=CascadeType.ALL, mappedBy = "genres", fetch = FetchType.LAZY)
    private Set<Movie> movies;

    public Genre() {
    }

    public Genre(String title) {
        this.title = title;
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
