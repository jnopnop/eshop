package org.nop.eshop.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="countries")
public class Country {

    @Id
    @GeneratedValue
    private Integer id;

    private String title;

    @ManyToMany(cascade=CascadeType.ALL, mappedBy = "countries", fetch = FetchType.LAZY)
    private Set<Movie> movies;

    public Country() {
    }

    public Country(String title) {
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
}
