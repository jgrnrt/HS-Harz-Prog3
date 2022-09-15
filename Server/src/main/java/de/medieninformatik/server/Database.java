package de.medieninformatik.server;

import java.sql.*;
import java.util.ResourceBundle;

/**
 * @author Jeremy Grunert m29265
 * date 2022-02-10
 * Programmierung 3 - Hausarbeit
 * <p>
 * The database class creates the table "movies" and adds dummy entries to it.
 */
public class Database {


    /**
     * Creates the necessary table, if it's not already present.
     * Does not delete an already existing table.
     */
    public static void createDatabase() throws SQLException{
        // Database connection information
        ResourceBundle bundle = ResourceBundle.getBundle("Select");
        // Creation of the connection to the database.
        Connection connection = DriverManager.getConnection(bundle.getString("URL"), bundle.getString("User"), bundle.getString("Passwd"));
        //Create table movies.
        initTableMovies(connection);
    }

    /**
     * Create the table "movies" with example movies.
     *
     * @param connection Connection to the database.
     */
    public static void initTableMovies(Connection connection) throws SQLException {
        if (!tableExists(connection, "movies")) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("""
                    create table movies (
                    movieID int NOT NULL AUTO_INCREMENT,
                    title varchar(255),
                    director varchar(255),
                    genre varchar(255),
                    rating integer,
                    primary key(movieID)
                    )""");
            statement.executeUpdate("""
                    insert into movies (title,director,genre,rating) values ("City of God", "Fernando Meirelles", "Crime", 9);
                    """);
            statement.executeUpdate("""
                    insert into movies (title,director,genre,rating) values ("American Psycho", "Mary Harron", "Horror", 8)
                    """);
            statement.executeUpdate("""
                    insert into movies (title,director,genre,rating) values ("Matrix Resurrections", "Lana Wachowski", "Action", 4)
                    """);
            statement.executeUpdate("""
                    insert into movies (title,director,genre,rating) values ("Chernobyl", "Craig Mazin", "Drama", 10)
                    """);
            statement.executeUpdate("""
                    insert into movies (title,director,genre,rating) values ("Pulp Fiction", "Quentin Tarantino", "Crime", 9)
                    """);
            statement.executeUpdate("""
                    insert into movies (title,director,genre,rating) values ("The Wolf of Wall Street", "Martin Scorsese", "Biography", 9)
                    """);
            statement.executeUpdate("""
                    insert into movies (title,director,genre,rating) values ("Interstellar", "Christopher Nolan", "Adventure", 9)
                    """);
            statement.executeUpdate("""
                    insert into movies (title,director,genre,rating) values ("Se7en", "David Fincher", "Crime", 9)
                    """);
            statement.executeUpdate("""
                    insert into movies (title,director,genre,rating) values ("Parasite", "Bong Joon Ho", "Comedy", 9)
                    """);
            statement.executeUpdate("""
                    insert into movies (title,director,genre,rating) values ("Steins;Gate", "Hiroshi Hamasaki, Takuya Satō", "Anime", 9)
                    """);
            statement.executeUpdate("""
                    insert into movies (title,director,genre,rating) values ("The Office", "Greg Daniels", "Comedy", 9)
                    """);
            statement.executeUpdate("""
                    insert into movies (title,director,genre,rating) values ("Memento", "Christopher Nolan", "Mystery", 8)
                    """);
            statement.close();
        }
    }


    /**
     * Check if table exists.
     *
     * @param connection Connection to the database.
     * @param tableName  Table to be checked.
     * @return true if table exists.
     */
    public static boolean tableExists(Connection connection, String tableName) throws SQLException {
        boolean tExists = false;
        try (ResultSet rs = connection.getMetaData().getTables(null, null, tableName, null)) {
            while (rs.next()) {
                String tName = rs.getString("TABLE_NAME");
                if (tName != null && tName.equals(tableName)) {
                    tExists = true;
                    break;
                }
            }
        }
        return tExists;
    }
}
