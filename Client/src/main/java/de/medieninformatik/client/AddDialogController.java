package de.medieninformatik.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.medieninformatik.common.Movie;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * @author Jeremy Grunert m29265
 * date 2022-02-10
 * Programmierung 3 - Hausarbeit
 * <p>
 * Shows a GUI to add a movie to the database.
 */
public class AddDialogController {

    @FXML
    TextField titleTextField;
    @FXML
    TextField directorTextField;
    @FXML
    TextField genreTextField;
    @FXML
    ChoiceBox<Integer> ratingsBox;
    @FXML
    Text applyButtonErrorText;

    private Stage stage;
    private ClientController clientController;

    /**
     * @param stage Sets the current JavaFX stage.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * @param clientController Sets the current ClientController instance.
     */
    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }

    /**
     * Sets the add button.
     * If the ui elements are missing necessary movie information, then it will prompt an error message.
     * Else it will create a new Movie object and call the addMovie method in the ClientController instance.
     *
     * @throws JsonProcessingException if there's a problem processing the movie JSON content.
     */
    public void setAdd() throws JsonProcessingException {
        if (titleTextField.getText().isEmpty()){
            applyButtonErrorText.setText("Film is missing a title.");
        } else if (directorTextField.getText().isEmpty()) {
            applyButtonErrorText.setText("Film is missing a director.");
        } else if (genreTextField.getText().isEmpty()) {
            applyButtonErrorText.setText("Film is missing a genre.");
        }else if (ratingsBox.getValue() == null) {
            applyButtonErrorText.setText("Film is missing a rating.");
        } else {
            Movie toAdd = new Movie(titleTextField.getText(), directorTextField.getText(), genreTextField.getText(), ratingsBox.getValue());
            clientController.addMovie(toAdd);
            stage.close();
        }
    }

    /**
     * Adds the ratingsBox after the controller has been instantiated.
     */
    @FXML
    private void initialize() {
        setRatingsBox();
    }

    /**
     * Adds the rating scale of 1 to 10 as an observableArrayList to the choice-box.
     */
    public void setRatingsBox() {
        ratingsBox.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
    }
}
