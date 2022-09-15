package de.medieninformatik.common;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

/**
 * @author Jeremy Grunert m29265
 * date 2022-02-10
 * Programmierung 3 - Hausarbeit
 * <p>
 * The CustomDeserializer class adds the required deserialize method for the JsonParser.
 */
public class CustomDeserializer extends StdDeserializer<Movie> {


    public CustomDeserializer() {
        this(Movie.class);
    }

    public CustomDeserializer(Class<Movie> movieClass) {
        super(movieClass);
    }

    /**
     * The custom deserializer for the JsonParser.
     * It converts the nodes in the DeserializationContext to the corresponding variables and
     * creates a new Movie object.
     *
     * @param p       Base class that defines public API for reading JSON content. Instances are created using factory methods of a JsonFactory instance.
     * @param context Context for the process of deserialization a single root-level value. Used to allow passing in configuration settings and reusable temporary objects (scrap arrays, containers).
     * @return A new Movie object based on the provided JSON string.
     */
    @Override
    public Movie deserialize(JsonParser p, DeserializationContext context) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        int movieID = (Integer) node.get("movieID").numberValue();
        int rating = (Integer) node.get("rating").numberValue();
        String director = node.get("director").asText();
        String genre = node.get("genre").asText();
        String title = node.get("title").asText();
        return new Movie(title, director, genre, rating, movieID);
    }
}
