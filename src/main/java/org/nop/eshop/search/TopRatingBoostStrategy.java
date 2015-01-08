package org.nop.eshop.search;

import org.hibernate.search.engine.BoostStrategy;
import org.nop.eshop.model.Movie;


public class TopRatingBoostStrategy implements BoostStrategy {
    @Override
    public float defineBoost(Object o) {
        if (o == null || !(o instanceof Movie)) {
            return 1;
        }
        Movie movie = (Movie) o;
        if (movie.getRating() != null && movie.getRating() >= 7.0f) {
            return 2;
        }
        return 1;
    }
}
