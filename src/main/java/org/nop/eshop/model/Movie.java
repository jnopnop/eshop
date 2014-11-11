package org.nop.eshop.model;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "movies")
public class Movie implements Comparable<Movie> {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String description;
    private Integer duration;
    @Column(name = "release_date")
    private Date releaseDate;
    private Float rating;
    @Column(name = "img_url")
    private String imageURL;
    @Column(name="imdb_id")
    private String imdbId;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "age_categories_id")
    private AgeCategory ageCategory;

    @ManyToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name="movies_genres",
            joinColumns = {@JoinColumn(name="movies_id", referencedColumnName="id")},
            inverseJoinColumns = {@JoinColumn(name="genres_id", referencedColumnName="id")})
    private Set<Genre> genres;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "movie")
    private Set<Comment> comments = new HashSet<>();

    @ManyToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name="movies_countries",
            joinColumns = {@JoinColumn(name="movies_id", referencedColumnName="id")},
            inverseJoinColumns = {@JoinColumn(name="countries_id", referencedColumnName="id")})
    private Set<Country> countries;

    @OneToMany(mappedBy = "pk.movie", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<MoviePerson> persons = new HashSet<>(0);

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

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public AgeCategory getAgeCategory() {
        return ageCategory;
    }

    public void setAgeCategory(AgeCategory ageCategory) {
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

