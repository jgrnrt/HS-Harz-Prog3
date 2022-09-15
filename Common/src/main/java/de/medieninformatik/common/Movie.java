package de.medieninformatik.common;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author Jeremy Grunert m29265
 * date 2022-02-10
 * Programmierung 3 - Hausarbeit
 * <p>
 * The Movie class is used to create a plain old Java object.
 */
@JsonDeserialize(using = CustomDeserializer.class)
public class Movie {

    private final String title;
    private final String director;
    private final String genre;
    private final int rating;
    private final int movieID;
    private final StringProperty titleProperty = new SimpleStringProperty();
    private final StringProperty directorProperty = new SimpleStringProperty();
    private final StringProperty genreProperty = new SimpleStringProperty();
    private final IntegerProperty ratingProperty = new SimpleIntegerProperty();
    private final IntegerProperty movieIDProperty = new SimpleIntegerProperty();

    /**
     * Constructor creates a Movie object with all known variables.
     *
     * @param title    Name of the movie.
     * @param director A director controls a film's artistic and dramatic aspects and visualizes the screenplay.
     * @param genre    A film genre is a stylistic or thematic category.
     * @param rating   A 1-10 rating scale.
     * @param movieID  The movie key provided by the database.
     */
    public Movie(String title, String director, String genre, int rating, int movieID) {
        this.title = title;
        this.director = director;
        this.genre = genre;
        this.rating = rating;
        this.movieID = movieID;
        titleProperty.set(title);
        directorProperty.set(director);
        genreProperty.set(genre);
        ratingProperty.set(rating);
        movieIDProperty.set(movieID);
    }

    /**
     * Constructor creates a Movie object without a movieID parameter.
     *
     * @param title    Name of the movie.
     * @param director A director controls a film's artistic and dramatic aspects and visualizes the screenplay.
     * @param genre    A film genre is a stylistic or thematic category.
     * @param rating   A 1-10 rating scale.
     */
    public Movie(String title, String director, String genre, int rating) {
        this.movieID = 0;
        this.title = title;
        this.director = director;
        this.genre = genre;
        this.rating = rating;
        titleProperty.set(title);
        directorProperty.set(director);
        genreProperty.set(genre);
        ratingProperty.set(rating);
        movieIDProperty.set(0);
    }

    /**
     * @return The movie key provided by the database.
     */
    public int getMovieID() {
        return movieID;
    }

    /**
     * @return Name of the movie.
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return A simple string property wrapping the title value.
     */
    public StringProperty getTitleProperty() {
        return titleProperty;
    }

    /**
     * @return A director controls a film's artistic and dramatic aspects and visualizes the screenplay.
     */
    public String getDirector() {
        return director;
    }

    /**
     * @return A simple string property wrapping the director value.
     */
    public StringProperty getDirectorProperty() {
        return directorProperty;
    }

    /**
     * @return A film genre is a stylistic or thematic category.
     */
    public String getGenre() {
        return genre;
    }

    /**
     * @return A simple string property wrapping the genre value.
     */
    public StringProperty getGenreProperty() {
        return genreProperty;
    }

    /**
     * @return A 1-10 rating scale.
     */
    public int getRating() {
        return rating;
    }

    /**
     * @return A simple integer property wrapping the rating value.
     */
    public IntegerProperty getRatingProperty() {
        return ratingProperty;
    }
}
