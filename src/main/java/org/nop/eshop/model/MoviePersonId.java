package org.nop.eshop.model;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class MoviePersonId implements Serializable {
    private static final long serialVersionUID = -6384721937759489842L;

    @ManyToOne
    private Movie movie;

    @ManyToOne
    @Cascade(CascadeType.SAVE_UPDATE)
    private Person person;

    private String career;

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MoviePersonId)) return false;

        MoviePersonId that = (MoviePersonId) o;

        return movie.equals(that.movie) && person.equals(that.person) && career.equalsIgnoreCase(that.career);
    }

    @Override
    public int hashCode() {
        int result = movie.hashCode();
        result = 31 * result + person.hashCode();
        result = 31 * result + career.hashCode();
        return result;
    }
}
