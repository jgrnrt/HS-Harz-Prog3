package de.medieninformatik.client;


import com.fasterxml.jackson.core.JsonProcessingException;
import de.medieninformatik.common.Movie;
import javafx.application.Platform;
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
 * Shows a GUI to edit a movie in the database.
 */
public class EditDialogController {

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

    private Movie movie;
    private Stage stage;
    private ClientController clientController;

    /**
     * @param movie Sets the to be altered Movie object.
     */
    public void setMovie(Movie movie) {
        this.movie = movie;
    }

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
     * Sets the apply button.
     * If the ui elements are missing necessary movie information, then it will prompt an error message.
     * Else it will create a new Movie object and call the editMovie method in the ClientController instance.
     *
     * @throws JsonProcessingException if there's a problem processing the movie JSON content.
     */
    public void setApply() throws JsonProcessingException {
        if (titleTextField.getText().isEmpty()){
            applyButtonErrorText.setText("Film is missing a title.");
        } else if (directorTextField.getText().isEmpty()) {
            applyButtonErrorText.setText("Film is missing a director.");
        } else if (genreTextField.getText().isEmpty()) {
            applyButtonErrorText.setText("Film is missing a genre.");
        }else if (ratingsBox.getValue() == null) {
            applyButtonErrorText.setText("Film is missing a rating.");
        } else {
            Movie toEdit = new Movie(titleTextField.getText(), directorTextField.getText(), genreTextField.getText(), ratingsBox.getValue(), movie.getMovieID());
            clientController.editMovie(toEdit);
            stage.close();
        }
    }

    /**
     * Adds the ratingsBox after the controller has been instantiated.
     * "runLater()" used to update the UI elements.
     */
    @FXML
    private void initialize() {
        setRatingsBox();

        Platform.runLater(() -> { //Setting text-field text after acquiring the Movie object.
            titleTextField.setText(movie.getTitle());
            directorTextField.setText(movie.getDirector());
            genreTextField.setText(movie.getGenre());
            ratingsBox.setValue(movie.getRating());
        });
    }

    /**
     * Adds the rating scale of 1 to 10 as an observableArrayList to the choice-box.
     */
    public void setRatingsBox() {
        ratingsBox.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
    }
}
