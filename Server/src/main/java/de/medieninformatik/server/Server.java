package de.medieninformatik.server;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.ServerConfiguration;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Jeremy Grunert m29265
 * date 2022-02-10
 * Programmierung 3 - Hausarbeit
 * <p>
 * This class starts a server built with the Grizzly framework.
 * The server listenes for HTTP requests, which are handled by the CineLabApplication class.
 */
public class Server {

    /**
     * The main method configures and starts the Grizzly server.
     * Also, an HTTP handler is added.
     *
     * @param args Arguments are not used.
     * @throws IOException        Signals that an I/O exception of some sort has occurred. This class is the general class of exceptions produced by failed or interrupted I/O operations.
     * @throws URISyntaxException Checked exception thrown to indicate that a string could not be parsed as a URI reference.
     */
    public static void main(String[] args) throws IOException, URISyntaxException {
        try {
            Database.createDatabase();
        } catch (SQLException e) {
            System.out.printf("%n--- SQLException --- %n%n");
            System.err.printf("""
                    Message:: %s
                    SQLSTATE: %s
                    Vendor error code: %d
                    """.stripIndent(), e.getMessage(), e.getSQLState(), e.getErrorCode());
            System.exit(1);
        }
        Logger.getLogger("org.glassfish").setLevel(Level.SEVERE);
        // setting up baseURI and config for the grizzly server
        URI baseURI = new URI("http://0.0.0.0:8080/cinelab");
        ResourceConfig config = ResourceConfig.forApplicationClass(CineLabApplication.class);

        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(baseURI, config);
        StaticHttpHandler handler = new StaticHttpHandler("web");
        handler.setFileCacheEnabled(false);
        ServerConfiguration serverConfig = server.getServerConfiguration();
        serverConfig.addHttpHandler(handler, "/");

        if (!server.isStarted()) server.start();
        System.out.println("Press ENTER to stop the server");
        System.in.read();
        server.shutdownNow();
    }
}
