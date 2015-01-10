package org.nop.eshop.utils;


import org.jboss.logging.Logger;
import org.nop.eshop.model.*;
import org.nop.eshop.service.ImageService;
import org.nop.eshop.service.MovieService;
import org.nop.eshop.service.UserService;
import org.nop.eshop.web.model.*;

import java.util.*;

public class GMConverter {
    Logger log = Logger.getLogger(GMConverter.class);

    private static String URLFor(Image image, String entity) {
        return "/pic/" + entity + "/" + image.getId();
    }

    public static MovieWeb toSimpleMovie(Movie movie) {
        MovieWeb result = new MovieWeb();
        result.setId(movie.getId());
        result.setRating(movie.getRating());
        result.setReleaseDate(movie.getReleaseDate());
        result.setTitle(movie.getTitle());
        for (Image i: movie.getImages()) {
            if (i.getType().equalsIgnoreCase(ImageService.IMAGE_TYPE_PRIMARY)) {
                result.setMainImage(URLFor(i, ImageService.ENTITY_MOVIE));
                break;
            }
        }
        result.setDescription(movie.getDescription());
        result.setDuration(movie.getDuration());
        result.setImdbId(movie.getImdbId());
        result.setAgeCategory(movie.getAgeCategory());

        return result;
    }

    public static MovieWeb toWebModel(Movie movie) {
        if (movie == null)
            return null;

        MovieWeb result = new MovieWeb();
        result.setId(movie.getId());
        result.setRating(movie.getRating());
        result.setReleaseDate(movie.getReleaseDate());
        result.setTitle(movie.getTitle());
        for (Image i: movie.getImages()) {
            if (i.getType().equalsIgnoreCase(ImageService.IMAGE_TYPE_PRIMARY)) {
                result.setMainImage(URLFor(i, ImageService.ENTITY_MOVIE));
            } else {
                result.getCarouselImages().add(URLFor(i, ImageService.ENTITY_MOVIE));
            }
        }
        result.setDescription(movie.getDescription());
        result.setDuration(movie.getDuration());
        result.setImdbId(movie.getImdbId());
        result.setAgeCategory(movie.getAgeCategory());

        //complex fields
        result.setGenres(toWebGenres(movie.getGenres()));
        result.setCountries(toSimpleWebCountries(movie.getCountries()));
        result.setDirectors(toWebDirectors(movie.getPersons()));
        result.setActors(toWebActors(movie.getPersons()));
        result.setWriters(toWebWriters(movie.getPersons()));
        result.setComments(toWebComments(movie.getComments()));
        return result;
    }

    private static Map<Long, CommentWeb> toWebComments(Set<Comment> comments) {
        Map<Long, CommentWeb> result = new LinkedHashMap<>();
        Set<Comment> ss = new TreeSet<>(comments);
        for (Comment c: ss) {
            result.put(c.getId(), toWebComment(c));
        }
        return result;
    }

    private static CommentWeb toWebComment(Comment c) {
        CommentWeb cw = new CommentWeb();
        cw.setText(c.getText());
        cw.setTitle(c.getTitle());
        cw.setId(c.getId());
        cw.setUser(toUserWeb(c.getUser()));
        return cw;
    }

    public static List<UserWeb> toWebUsers(Collection<User> u) {
        List<UserWeb> users = new ArrayList<>();
        for (User uu: u) {
            users.add(toUserWeb(uu));
        }

        return users;
    }

    public static UserWeb toUserWeb(User user) {
        UserWeb uw = new UserWeb();
        uw.setId(user.getId());
        uw.setEmail(user.getEmail());
        uw.setFullname(user.getFullname());
        uw.setPassword(user.getPassword());
        for (Image i: user.getImages()) {
            if (i.getType().equalsIgnoreCase(ImageService.IMAGE_TYPE_PRIMARY)) {
                uw.setImage(URLFor(i, ImageService.ENTITY_USER));
                break;
            }
        }
        for (Role r: user.getRoles()) {
            if (UserService.ROLE_ADMIN.equalsIgnoreCase(r.getRole())) {
                uw.setAdmin(true);
                break;
            }
        }
        return uw;
    }

    private static Map<Long, String> toWebWriters(Collection<MoviePerson> persons) {
        Map<Long, String> result = new HashMap<>();
        for (MoviePerson mp: persons) {
            if (mp.getCareer().equals(MovieService.PERSON_WRITER)) {
                result.put(mp.getPerson().getId(), mp.getPerson().getFullname());
            }
        }
        return result;
    }

    public static Map<Long, String> toWebDirectors(Collection<MoviePerson> persons) {
        Map<Long, String> result = new HashMap<>();
        for (MoviePerson mp: persons) {
            if (mp.getCareer().equals(MovieService.PERSON_DIRECTOR)) {
                result.put(mp.getPerson().getId(), mp.getPerson().getFullname());
            }
        }
        return result;
    }

    public static Map<Long, String> toWebActors(Collection<MoviePerson> persons) {
        Map<Long, String> result = new HashMap<>();
        for (MoviePerson mp: persons) {
            if (mp.getCareer().equals(MovieService.PERSON_ACTOR)) {
                result.put(mp.getPerson().getId(), mp.getPerson().getFullname());
            }
        }
        return result;
    }

    public static Map<Long, String> toWebPersons(Collection<Person> persons) {
        Map<Long, String> result = new HashMap<>();
        for (Person mp: persons) {
            result.put(mp.getId(), mp.getFullname());
        }
        return result;
    }

    public static List<PersonWeb> toWebPersonModels(Collection<Person> persons) {
        List<PersonWeb> result = new ArrayList<>();
        for (Person p: persons) {
            result.add(toWebPerson(p));
        }
        return result;
    }

    public static PersonWeb toWebPerson(Person person) {
        PersonWeb result = new PersonWeb();
        result.setId(person.getId());
        result.setBirthdate(person.getBirthdate());
        result.setFullname(person.getFullname());
        result.setImdbId(person.getImdbId());
        for (Image i: person.getImages()) {
            if (i.getType().equalsIgnoreCase(ImageService.IMAGE_TYPE_PRIMARY)) {
                result.setMainImage(URLFor(i, ImageService.ENTITY_PERSON));
            } else {
                result.getCarouselImages().add(URLFor(i, ImageService.ENTITY_PERSON));
            }
        }
        result.setDirectorAt(getDirectedMovies(person.getMoviePersons()));
        result.setWriterAt(getWroteMovies(person.getMoviePersons()));
        result.setActorAt(getActedMovies(person.getMoviePersons()));
        return result;
    }

    private static Set<MovieWeb> getActedMovies(Set<MoviePerson> moviePersons) {
        Set<MovieWeb> result = new HashSet<>();
        for (MoviePerson mp: moviePersons) {
            if (mp.getCareer().equals(MovieService.PERSON_ACTOR)) {
                result.add(toSimpleMovie(mp.getMovie()));
            }
        }
        return result;
    }

    private static Set<MovieWeb> getWroteMovies(Set<MoviePerson> moviePersons) {
        Set<MovieWeb> result = new HashSet<>();
        for (MoviePerson mp: moviePersons) {
            if (mp.getCareer().equals(MovieService.PERSON_WRITER)) {
                result.add(toSimpleMovie(mp.getMovie()));
            }
        }
        return result;
    }

    private static Set<MovieWeb> getDirectedMovies(Set<MoviePerson> moviePersons) {
        Set<MovieWeb> result = new HashSet<>();
        for (MoviePerson mp: moviePersons) {
            if (mp.getCareer().equals(MovieService.PERSON_DIRECTOR)) {
                result.add(toSimpleMovie(mp.getMovie()));
            }
        }
        return result;
    }

    public static Map<Long, String> toSimpleWebCountries(Collection<Country> countries) {
        Map<Long, String> result = new HashMap<>();
        for (Country c: countries) {
            result.put(Long.valueOf(c.getId()), c.getTitle());
        }
        return result;
    }

    public static Map<Long, CountryWeb> toWebCountries(Collection<Country> countries) {
        Map<Long, CountryWeb> result = new HashMap<>();
        for (Country c: countries) {
            result.put(Long.valueOf(c.getId()), toWebCountry(c));
        }
        return result;
    }

    public static CountryWeb toWebCountry(Country country) {
        CountryWeb result = new CountryWeb();
        result.setId(country.getId());
        result.setTitle(country.getTitle());
        return result;
    }

    public static Map<Long, String> toWebGenres(Collection<Genre> genres) {
        Map<Long, String> result = new HashMap<>();
        for (Genre g: genres) {
            result.put(Long.valueOf(g.getId()), g.getTitle());
        }
        return result;
    }

    public static GenreWeb toWebGenre(Genre genre) {
        GenreWeb result = new GenreWeb();
        result.setId(genre.getId());
        result.setTitle(genre.getTitle());
        return result;
    }

    public static List<MovieWeb> toWebModels(Collection<Movie> movies) {
        if (movies == null)
            return null;

        List<MovieWeb> webMovies = new ArrayList<>();
        for (Movie m: movies) {
            webMovies.add(toWebModel(m));
        }

        return webMovies;
    }

    public static NewsWeb toWebNews(News news) {
        NewsWeb nw = new NewsWeb();
        nw.setId(news.getId());
        nw.setTitle(news.getTitle());
        for (Image i: news.getImages()) {
            if (i.getType().equalsIgnoreCase(ImageService.IMAGE_TYPE_PRIMARY)) {
                nw.setMainImage(URLFor(i, ImageService.ENTITY_NEWS));
                break;
            }
        }
        nw.setContents(news.getContents());
        nw.setCreatedOn(news.getCreatedOn());
        return nw;
    }

    public static List<NewsWeb> toWebNews(Collection<News> search) {
        List<NewsWeb> result = new ArrayList<>();
        for (News n: search) {
            result.add(toWebNews(n));
        }
        return result;
    }

    public static User toSimpleUser(UserWeb uw) {
        User u = new User();
        u.setId(uw.getId());
        u.setPassword(uw.getPassword());
        u.setEmail(uw.getEmail());
        return u;
    }

    public static Movie toMovie(MovieWeb mw) {
        Movie m = new Movie();
        m.setId(mw.getId());
        m.setTitle(mw.getTitle());
        return m;
    }

    public static Comment toComment(CommentWeb cw) {
        Comment c = new Comment();
        c.setUser(toSimpleUser(cw.getUser()));
        c.setTitle(cw.getTitle());
        c.setText(cw.getText());
        c.setMovie(toMovie(cw.getMovie()));
        return c;
    }
}
