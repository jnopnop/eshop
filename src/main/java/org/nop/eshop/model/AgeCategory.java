package org.nop.eshop.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="age_categories")
public class AgeCategory {

    @Id
    @GeneratedValue
    private Integer id;

    private String category;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ageCategory")
    private Set<Movie> movies;

    public AgeCategory() {
    }

    public AgeCategory(String category) {
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
