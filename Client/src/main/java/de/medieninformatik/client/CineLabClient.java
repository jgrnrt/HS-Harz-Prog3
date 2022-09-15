package de.medieninformatik.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.medieninformatik.common.Movie;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * @author Jeremy Grunert m29265
 * date 2022-02-10
 * Programmierung 3 - Hausarbeit
 * <p>
 * The CineLabClient class provides the essential client-side rest methods.
 * It sends HTTP requests and receives corresponding responses from the server.
 */
public class CineLabClient {

    private final Client client;
    private final String baseURI;

    /**
     * Creates a new client instance.
     *
     * @param baseURI The root address of the server
     */
    public CineLabClient(String baseURI) {
        this.client = ClientBuilder.newClient();
        this.baseURI = baseURI;
    }

    /**
     * Simplifies targeting the server address and displays the operation on the console.
     *
     * @param crud the desired HTTP method
     * @param uri  the address to be added after the root address
     * @return complete server address for the HTTP request
     */
    private WebTarget getTarget(String crud, String uri) {
        System.out.printf("%n--- %s %s%s%n", crud, baseURI, uri);
        return client.target(baseURI + uri);
    }

    /**
     * Displays the HTTP response to the console.
     *
     * @param response the reply to the HTTP request of the server
     * @return HTTP request status
     */
    private int status(Response response) {
        int code = response.getStatus();
        String reason = response.getStatusInfo().getReasonPhrase();
        System.out.printf("Status: %d %s%n", code, reason);
        return code;
    }

    /**
     * Requests a copy of all movies from the database.
     *
     * @param uri /movies
     * @return either (1) an array of Movie objects from the database or (2) an array of Movie objects with one
     * entry of null
     * @throws JsonProcessingException if there's a problem processing the movie JSON content.
     */
    public Movie[] get(String uri) throws JsonProcessingException {
        WebTarget target = getTarget("GET", uri);
        Response response = target.request(MediaType.APPLICATION_JSON).get();

        if (status(response) != 200) {
            response.close();
            return new Movie[1]; //return empty Movie array
        } else {
            //convert Json String to movie[] array
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(response.readEntity(String.class), Movie[].class);
        }
    }

    /**
     * Converts the Movie object to a JSON string and requests it to be added to the database.
     *
     * @param uri   /movies
     * @param movie To be added movie.
     * @throws JsonProcessingException if there's a problem processing the movie JSON content.
     */
    public void post(String uri, Movie movie) throws JsonProcessingException {
        WebTarget target = getTarget("POST", uri);
        //convert Movie object to Json String
        ObjectMapper objectMapper = new ObjectMapper();
        //create entity for response
        Entity<String> entity = Entity.entity(objectMapper.writeValueAsString(movie), MediaType.APPLICATION_JSON);
        Response response = target.request().post(entity);
        status(response);
        response.close();
    }

    /**
     * Requests to delete a movie in the database.
     *
     * @param uri     /movies/movieID
     * @param movieID To be deleted movie.
     */
    public void delete(String uri, int movieID) {
        WebTarget target = getTarget("DELETE", uri + "/" + movieID);
        Response response = target.request().delete();
        status(response);
        response.close();
    }

    /**
     * The getMovieID method is used to target the to be changed movie.
     * The Movie object is converted to a JSON string and sent to the server.
     *
     * @param uri   /movies/movieID
     * @param movie The already edited movie.
     * @throws JsonProcessingException if there's a problem processing the movie JSON content.
     */
    public void put(String uri, Movie movie) throws JsonProcessingException {
        WebTarget target = getTarget("POST", uri + "/" + movie.getMovieID());
        //convert Movie object to Json String
        ObjectMapper objectMapper = new ObjectMapper();
        //create entity for response
        Entity<String> entity = Entity.entity(objectMapper.writeValueAsString(movie), MediaType.APPLICATION_JSON);
        Response response = target.request().put(entity);
        status(response);
        response.close();
    }
}
