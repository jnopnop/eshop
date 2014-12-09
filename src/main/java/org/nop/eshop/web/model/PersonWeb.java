package org.nop.eshop.web.model;


import java.util.Date;
import java.util.Set;

public class PersonWeb {
    private Long id;
    private String fullname;
    private Date birthdate;
    private String photoURL;
    private String imdbId;
    private Set<MovieWeb> directorAt;
    private Set<MovieWeb> writerAt;
    private Set<MovieWeb> actorAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public Set<MovieWeb> getDirectorAt() {
        return directorAt;
    }

    public void setDirectorAt(Set<MovieWeb> directorAt) {
        this.directorAt = directorAt;
    }

    public Set<MovieWeb> getWriterAt() {
        return writerAt;
    }

    public void setWriterAt(Set<MovieWeb> writerAt) {
        this.writerAt = writerAt;
    }

    public Set<MovieWeb> getActorAt() {
        return actorAt;
    }

    public void setActorAt(Set<MovieWeb> actorAt) {
        this.actorAt = actorAt;
    }
}
