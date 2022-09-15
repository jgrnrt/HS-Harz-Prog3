module de.medieninformatik.Server {
    requires java.logging;
    requires jakarta.ws.rs;
    requires jakarta.annotation;
    requires jakarta.activation;
    requires org.mariadb.jdbc;
    requires jersey.server;
    requires grizzly.http.server;
    requires jersey.container.grizzly2.http;
    requires de.medieninformatik.Common;
    requires com.fasterxml.jackson.databind;

    exports de.medieninformatik.server;
}