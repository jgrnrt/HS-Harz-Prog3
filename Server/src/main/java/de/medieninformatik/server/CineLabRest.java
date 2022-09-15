package de.medieninformatik.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.medieninformatik.common.Movie;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Jeremy Grunert m29265
 * date 2022-02-10
 * Programmierung 3 - Hausarbeit
 * <p>
 * The CineLabRest class connects to the database and provides methods to view, add and edit entries in the table movies.
 */
@Path("movies")
public class CineLabRest {

    private static Connection connection;

    static {
        try {
            // Database connection information
            ResourceBundle bundle = ResourceBundle.getBundle("Select");
            // Creation of the connection to the database.
            connection = DriverManager.getConnection(bundle.getString("URL"), bundle.getString("User"), bundle.getString("Passwd"));
        } catch (SQLException e) {
            System.out.printf("%n--- SQLException --- %n%n");
            System.err.printf("""
                    Message:: %s
                    SQLSTATE: %s
                    Vendor error code: %d
                                        
                    """.stripIndent(), e.getMessage(), e.getSQLState(), e.getErrorCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Saves the entries of the table "movies" in a result set.
     * The resul set's contents are converted to Movie POJOs and added to a list.
     *
     * @return either (1) Response with status "200" with an entity of a Movie[] JSON string or (2) Response with status 404.
     * @throws JsonProcessingException if there's a problem processing the movie JSON content.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMovies() throws JsonProcessingException, SQLException {
        //get movie table
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM movies"); // Get all movies
        statement.close();
        //save the table in a list
        List<Movie> movies = new ArrayList<>();
        while (resultSet.next()) {
            movies.add(
                    new Movie(
                            resultSet.getString("title"),
                            resultSet.getString("director"),
                            resultSet.getString("genre"),
                            resultSet.getInt("rating"),
                            resultSet.getInt("movieID")));
        }

        if (!movies.isEmpty()) {
            ObjectMapper objectMapper = new ObjectMapper();
            return Response.ok(objectMapper.writeValueAsString(movies)).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * Converts the "movieJSON" string into a movie POJO and adds it to the table "movies" in the database.
     *
     * @param movieJSON The new movie as a JSON string.
     * @return either (1) Response with status "304" or (2) Response with status "204".
     * @throws JsonProcessingException if there's a problem processing the movie JSON content.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newMovie(String movieJSON) throws JsonProcessingException, SQLException {
        //converting movieJSON string to Movie object
        ObjectMapper mapper = new ObjectMapper();
        Movie movie = mapper.readValue(movieJSON, Movie.class);

        //SQL statement to insert the new movie
        PreparedStatement preparedStatement1 = connection.prepareStatement("INSERT into movies (title, director, genre, rating) values (?,?,?,?)");
        preparedStatement1.setString(1, movie.getTitle());
        preparedStatement1.setString(2, movie.getDirector());
        preparedStatement1.setString(3, movie.getGenre());
        preparedStatement1.setInt(4, movie.getRating());

        if (preparedStatement1.executeUpdate() == 0) {
            return Response.notModified().build();
        } else {
            return Response.ok().status(Response.Status.NO_CONTENT).build();
        }
    }

    /**
     * Deletes a movie entry in the table "movies".
     *
     * @param movieID The movie that needs to be deleted.
     * @return either (1) Response with status "304" or (2) Response with status "204".
     */
    @DELETE
    @Path("/{movieID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteMovie(@PathParam("movieID") int movieID) throws SQLException {

        PreparedStatement preparedStatement1 = connection.prepareStatement("DELETE FROM movies WHERE movieID=?");
        preparedStatement1.setInt(1, movieID);

        if (preparedStatement1.executeUpdate() == 0) {
            return Response.notModified().build();
        } else {
            return Response.ok().status(Response.Status.NO_CONTENT).build();
        }
    }

    /**
     * Modifies the saved information of a selected movie according to the provided information in the "movieJSON" string.
     *
     * @param movieID   The movie that needs to be changed.
     * @param movieJSON The movie as a JSON string.
     * @return either (1) Response with status "304" or (2) Response with status "204".
     * @throws JsonProcessingException if there's a problem processing the movie JSON content.
     */
    @PUT
    @Path("/{movieID}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateMovie(@PathParam("movieID") int movieID, String movieJSON) throws SQLException, JsonProcessingException {
        //converting movieJSON string to Movie object
        ObjectMapper mapper = new ObjectMapper();
        Movie movie = mapper.readValue(movieJSON, Movie.class);

        PreparedStatement preparedStatement1 = connection.prepareStatement("UPDATE movies SET title = ?, director = ?, genre = ?, rating = ? where movieID = ?");
        preparedStatement1.setString(1, movie.getTitle());
        preparedStatement1.setString(2, movie.getDirector());
        preparedStatement1.setString(3, movie.getGenre());
        preparedStatement1.setInt(4, movie.getRating());
        preparedStatement1.setInt(5, movieID);

        if (preparedStatement1.executeUpdate() == 0) {
            return Response.notModified().build();
        } else {
            return Response.ok().status(Response.Status.NO_CONTENT).build();
        }
    }
}
