package org.nop.eshop.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "movies_persons")
@AssociationOverrides({
        @AssociationOverride(name = "pk.person", joinColumns = @JoinColumn(name = "persons_id")),
        @AssociationOverride(name = "pk.movie", joinColumns = @JoinColumn(name = "movies_id")),
        @AssociationOverride(name = "pk.career", joinColumns = @JoinColumn(name = "careers_id"))
})
public class MoviePerson implements Serializable {
    private static final long serialVersionUID = -3342324212394888441L;

    @EmbeddedId
    private MoviePersonId pk = new MoviePersonId();

    public MoviePersonId getPk() {
        return pk;
    }

    public void setPk(MoviePersonId pk) {
        this.pk = pk;
    }

    @Transient
    public Person getPerson() {
        return getPk().getPerson();
    }

    public void setPerson(Person person) {
        getPk().setPerson(person);
    }

    @Transient
    public Career getCareer() {
        return getPk().getCareer();
    }

    public void setCareer(Career career) {
        getPk().setCareer(career);
    }

    @Transient
    public Movie getMovie() {
        return getPk().getMovie();
    }

    public void setMovie(Movie movie) {
        getPk().setMovie(movie);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MoviePerson)) return false;

        MoviePerson that = (MoviePerson) o;

        if (!getPk().equals(that.getPk())) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return getPk().hashCode();
    }
}
