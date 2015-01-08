package org.nop.eshop.web.model;


public class CommentWeb {
    private Long id;
    private String title;
    private String text;
    private UserWeb user;
    private MovieWeb movie;

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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public UserWeb getUser() {
        return user;
    }

    public void setUser(UserWeb user) {
        this.user = user;
    }

    public MovieWeb getMovie() {
        return movie;
    }

    public void setMovie(MovieWeb movie) {
        this.movie = movie;
    }
}
