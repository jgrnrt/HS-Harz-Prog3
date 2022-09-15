package de.medieninformatik.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Jeremy Grunert m29265
 * date 2022-02-10
 * Programmierung 3 - Hausarbeit
 * <p>
 * The Main class starts the CineLab client.
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch();
    }

    /**
     * Opens a JavaFX stage and displays the CineLab client GUI.
     *
     * @param stage Display the scene and the stage title on the JavaFX stage.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientController.class.getResource("/de.medieninformatik.client/Client.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("CineLab - Client");
        stage.setScene(scene);
        stage.show();
    }
}