package org.nop.eshop.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="comments")
public class Comment implements Comparable<Comment> {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String text;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movies_id")
    private Movie movie;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdOn", insertable=false)
    private Date createdOn;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    @Override
    public int compareTo(Comment o) {
        return o.getCreatedOn().compareTo(this.createdOn);
    }
}
