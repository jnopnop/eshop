package org.nop.eshop.web.model;


import java.util.Date;
import java.util.Map;

public class MovieWeb {
    private Long id;
    private String title;
    private String description;
    private Integer duration;
    private Date releaseDate;
    private Float rating;
    private String imageURL;
    private String imdbId;
    private AgeCategoryWeb ageCategory;
    private Map<Long, String> genres;
    private Map<Long, String> countries;
    private Map<Long, String> directors;
    private Map<Long, String> writers;
    private Map<Long, String> actors;

    public Map<Long, String> getWriters() {
        return writers;
    }

    public void setWriters(Map<Long, String> writers) {
        this.writers = writers;
    }

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

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public AgeCategoryWeb getAgeCategory() {
        return ageCategory;
    }

    public void setAgeCategory(AgeCategoryWeb ageCategory) {
        this.ageCategory = ageCategory;
    }

    public Map<Long, String> getGenres() {
        return genres;
    }

    public void setGenres(Map<Long, String> genres) {
        this.genres = genres;
    }

    public Map<Long, String> getCountries() {
        return countries;
    }

    public void setCountries(Map<Long, String> countries) {
        this.countries = countries;
    }

    public Map<Long, String> getDirectors() {
        return directors;
    }

    public void setDirectors(Map<Long, String> directors) {
        this.directors = directors;
    }

    public Map<Long, String> getActors() {
        return actors;
    }

    public void setActors(Map<Long, String> actors) {
        this.actors = actors;
    }
}
