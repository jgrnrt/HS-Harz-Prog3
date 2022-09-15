module de.medieninformatik.Client {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.medieninformatik.Common;
    requires jakarta.ws.rs;
    requires jakarta.annotation;
    requires jakarta.activation;
    requires com.fasterxml.jackson.databind;

    opens de.medieninformatik.client to javafx.fxml;
    exports de.medieninformatik.client;
}