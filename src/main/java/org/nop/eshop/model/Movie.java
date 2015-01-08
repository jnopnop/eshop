package org.nop.eshop.model;

import org.hibernate.annotations.Cascade;
import org.hibernate.search.annotations.*;
import org.nop.eshop.search.TopRatingBoostStrategy;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "movies")
@Indexed
@DynamicBoost(impl = TopRatingBoostStrategy.class)
public class Movie implements Comparable<Movie> {

    @Id
    @GeneratedValue
    private Long id;

    @Field(store = Store.COMPRESS)
    private String title;

    @Field(store = Store.COMPRESS)
    private String description;

    @Field
    @DateBridge(resolution = Resolution.YEAR)
    private Date releaseDate;

    @Field(store = Store.COMPRESS, index = Index.NO)
    private Integer duration;

    @Field(store = Store.COMPRESS, index = Index.NO)
    private Float rating;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "image_container", joinColumns = {
            @JoinColumn(name = "entity_id")}, inverseJoinColumns = {
            @JoinColumn(name = "images_id")
    })
    private List<Image> images = new ArrayList<>();

    private String imdbId;

    @Field(store = Store.COMPRESS)
    private String ageCategory;

    @ManyToMany(fetch = FetchType.EAGER)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinTable(name="movies_genres",
            joinColumns = {@JoinColumn(name="movies_id", referencedColumnName="id")},
            inverseJoinColumns = {@JoinColumn(name="genres_id", referencedColumnName="id")})
    @IndexedEmbedded(depth = 1)
    private Set<Genre> genres = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "movie")
    private Set<Comment> comments = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="movies_countries",
            joinColumns = {@JoinColumn(name="movies_id", referencedColumnName="id")},
            inverseJoinColumns = {@JoinColumn(name="countries_id", referencedColumnName="id")})
    @IndexedEmbedded(depth = 1)
    private Set<Country> countries = new HashSet<>();

    @OneToMany(mappedBy = "pk.movie", fetch = FetchType.EAGER)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private Set<MoviePerson> persons = new HashSet<>();

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

    public Set<MoviePerson> getPersons() {
        return persons;
    }

    public void setPersons(Set<MoviePerson> persons) {
        this.persons = persons;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public String getAgeCategory() {
        return ageCategory;
    }

    public void setAgeCategory(String ageCategory) {
        this.ageCategory = ageCategory;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Country> getCountries() {
        return countries;
    }

    public void setCountries(Set<Country> countries) {
        this.countries = countries;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    @Override
    public int compareTo(Movie o) {
        if (this.id == null)
            return -1;
        if (o.getId() == null)
            return 1;
        return this.id.compareTo(o.getId());
    }
}

