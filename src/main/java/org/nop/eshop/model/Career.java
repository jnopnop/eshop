package org.nop.eshop.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="careers")
public class Career {

    @Id
    @GeneratedValue
    private Integer id;

    private String title;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.career")
    private Set<MoviePerson> moviePersons = new HashSet<>(0);

    public Career() {
    }

    public Career(String title) {
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

    public Set<MoviePerson> getMoviePersons() {
        return moviePersons;
    }

    public void setMoviePersons(Set<MoviePerson> moviePersons) {
        this.moviePersons = moviePersons;
    }
}

