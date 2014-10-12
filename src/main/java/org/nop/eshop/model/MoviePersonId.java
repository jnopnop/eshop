package org.nop.eshop.model;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class MoviePersonId implements Serializable {
    private static final long serialVersionUID = -6384721937759489842L;

    @ManyToOne
    private Movie movie;

    @ManyToOne
    private Person person;

    @ManyToOne
    private Career career;

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

    public Career getCareer() {
        return career;
    }

    public void setCareer(Career career) {
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

        if (!career.equals(that.career)) return false;
        if (!movie.equals(that.movie)) return false;
        if (!person.equals(that.person)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = movie.hashCode();
        result = 31 * result + person.hashCode();
        result = 31 * result + career.hashCode();
        return result;
    }
}
