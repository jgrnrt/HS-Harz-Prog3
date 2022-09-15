package de.medieninformatik.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.medieninformatik.common.Movie;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Jeremy Grunert m29265
 * date 2022-02-10
 * Programmierung 3 - Hausarbeit
 * <p>
 * The ClientController class incorporates the AddDialogController class, the EditDialogController class and
 * the CineLabClient class.
 * It allows viewing, adding and editing movies provided by the database.
 */
public class ClientController {

    final String BASE_URI = "http://0.0.0.0:8080/cinelab";
    private final CineLabClient cineLabClient = new CineLabClient(BASE_URI);
    @FXML
    TableView<Movie> tableView = new TableView<>();
    @FXML
    TableColumn<Movie, String> titleColumn;
    @FXML
    TableColumn<Movie, String> directorColumn;
    @FXML
    TableColumn<Movie, String> genreColumn;
    @FXML
    TableColumn<Movie, Integer> ratingColumn;
    @FXML
    TableColumn<Movie, Boolean> editColumn;
    @FXML
    TableColumn<Movie, Boolean> deleteColumn;
    @FXML
    SplitMenuButton userButton;
    @FXML
    Button addButton;
    @FXML
    TextField filterField;

    /**
     * Refreshes the tableView after the controller has been instantiated.
     *
     * @throws JsonProcessingException if there's a problem processing the movie JSON content.
     */
    @FXML
    public void initialize() throws JsonProcessingException {
        refreshTableView();
    }

    /**
     * This method configures the table view and the corresponding table columns.
     * The observableList "movieList" is filled with the movies from the database.
     * A filtered list is created. The filterField text field is used for filtering the filtered list.
     * The filtered list is wrapped in a sorted list.
     * If the movieList is empty, then the items of the table view are set to the movieList and the table view is
     * cleared. Else the items of the table view are set to the sorted list.
     *
     * @throws JsonProcessingException if there's a problem processing the movie JSON content.
     */
    public void refreshTableView() throws JsonProcessingException {

        // 1. Initializing all columns
        titleColumn.setCellValueFactory(Movie -> Movie.getValue().getTitleProperty());
        directorColumn.setCellValueFactory(Movie -> Movie.getValue().getDirectorProperty());
        genreColumn.setCellValueFactory(Movie -> Movie.getValue().getGenreProperty());
        ratingColumn.setCellValueFactory(Movie -> Movie.getValue().getRatingProperty().asObject());
        // define a simple boolean cell value for the deleteColumn
        deleteColumn.setCellValueFactory(bool -> new SimpleBooleanProperty(bool.getValue() != null));
        // create a cell value factory with a delete button for each row in the table.
        deleteColumn.setCellFactory(movieBooleanTableColumn -> new DeleteButton(tableView));
        editColumn.setCellValueFactory(bool -> new SimpleBooleanProperty(bool.getValue() != null));
        editColumn.setCellFactory(movieBooleanTableColumn -> new EditButton(tableView));

        // 2. Refresh movieList
        ObservableList<Movie> movieList = FXCollections.observableArrayList(cineLabClient.get("/movies"));

        // 3. observableList in a filteredList, display all data
        FilteredList<Movie> filteredList = new FilteredList<>(movieList, p -> true);

        // 4. Set the filter predicate whenever the filter changes.
        filterField.textProperty().addListener((observable, oldValue, newValue) -> filteredList.setPredicate(movie -> {
            // If filter text is empty, display all movies.
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }

            // Compare title, director, genre and rating of every movie with filter text.
            String lowerCaseFilter = newValue.toLowerCase();

            if (movie.getTitle().toLowerCase().contains(lowerCaseFilter)) {
                return true; // Filter matches title.
            } else if (movie.getDirector().toLowerCase().contains(lowerCaseFilter)) {
                return true; // Filter matches director.
            } else if (movie.getGenre().toLowerCase().contains(lowerCaseFilter)) {
                return true; // Filter matches genre.
            } else return String.valueOf(movie.getRating()).contains(lowerCaseFilter); // Filter matches rating.
        }));

        // 5. Wrap the FilteredList in a SortedList.
        SortedList<Movie> sortedList = new SortedList<>(filteredList);

        // 6. Bind the SortedList comparator to the TableView comparator.
        sortedList.comparatorProperty().bind(tableView.comparatorProperty());

        // 7. Clear the tableview, if the movieList is empty or set the tableview to the sortedlist
        if (movieList.get(0) == null) {
            tableView.setItems(movieList);
            tableView.getItems().clear();
        } else {
            tableView.setItems(sortedList);
        }
    }

    /**
     * Calls the post method in the CineLabClient instance.
     *
     * @param movie The to be added movie.
     * @throws JsonProcessingException if there's a problem processing the movie JSON content.
     */
    public void addMovie(Movie movie) throws JsonProcessingException {
        cineLabClient.post("/movies", movie);
        refreshTableView();
        filterField.setText("");
    }

    /**
     * Calls the put method in the CineLabClient instance.
     *
     * @param movie The to be edited movie.
     * @throws JsonProcessingException if there's a problem processing the movie JSON content.
     */
    public void editMovie(Movie movie) throws JsonProcessingException {
        cineLabClient.put("/movies", movie);
        refreshTableView();
        filterField.setText("");
    }

    /**
     * Sets some UI elements to be invisible for viewing purposes.
     *
     * @throws JsonProcessingException if there's a problem processing the movie JSON content.
     */
    public void changeUserToUser() throws JsonProcessingException {
        editColumn.setVisible(false);
        deleteColumn.setVisible(false);
        userButton.setText("User");
        addButton.setVisible(false);
        refreshTableView();
    }

    /**
     * Sets some UI elements to be visible for adding and editing movies.
     *
     * @throws JsonProcessingException if there's a problem processing the movie JSON content.
     */
    public void changeUserToAdmin() throws JsonProcessingException {
        editColumn.setVisible(true);
        deleteColumn.setVisible(true);
        userButton.setText("Admin");
        addButton.setVisible(true);
        refreshTableView();
    }

    /**
     * A new stage and controller class is started in this method.
     * The stage and the ClientController instance can be accessed through setter methods.
     */
    public void setAddButton() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/de.medieninformatik.client/AddDialog.fxml"));
        Parent root = fxmlLoader.load();
        AddDialogController controller = fxmlLoader.getController();
        Scene scene = new Scene(root);
        Stage stage2 = new Stage();
        stage2.setTitle("Add a new Movie");
        stage2.setScene(scene);
        stage2.initModality(Modality.APPLICATION_MODAL);
        controller.setStage(stage2);
        controller.setClientController(ClientController.this);
        stage2.show();
    }

    /**
     * The EditButton class adds a button to a table cell.
     */
    private class EditButton extends TableCell<Movie, Boolean> {
        final Button editButton = new Button("Edit");

        /**
         * Sets an action event for the button.
         * The action event creates the Movie object from the row to get the movie information for alteration.
         * A new stage and controller class is started in this method.
         * The to be altered movie, the stage and the ClientController instance can be accessed through setter methods.
         *
         * @param tableView TableView to get the current Movie object in the according row.
         */
        EditButton(final TableView<Movie> tableView) {
            editButton.setOnAction(actionEvent -> {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/de.medieninformatik.client/EditDialog.fxml"));
                    Parent root = fxmlLoader.load();
                    EditDialogController controller = fxmlLoader.getController();
                    Movie toEdit = tableView.getItems().get(getTableRow().getIndex());
                    controller.setMovie(toEdit);
                    Scene scene = new Scene(root);
                    Stage stage2 = new Stage();
                    stage2.setTitle("Edit Movie Information");
                    stage2.setScene(scene);
                    stage2.initModality(Modality.APPLICATION_MODAL);
                    controller.setStage(stage2);
                    controller.setClientController(ClientController.this);
                    stage2.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        /**
         * Overrides the table cell and adds a button.
         *
         * @param item  The new item for the cell.
         * @param empty whether this cell represents data from the list. If it is empty,
         *              then it does not represent any domain data, but is a cell being used to render an "empty" row.
         */
        @Override
        protected void updateItem(Boolean item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setGraphic(null);
            } else {
                setGraphic(editButton);
            }
        }
    }

    /**
     * The DeleteButton class adds a button to a table cell.
     */
    private class DeleteButton extends TableCell<Movie, Boolean> {
        final Button deleteButton = new Button("-");

        /**
         * Sets an action event for the button.
         * The action event creates the Movie object from the row to get the needed movieID for deletion.
         *
         * @param tableView TableView to get the current Movie object in the according row.
         */
        DeleteButton(final TableView<Movie> tableView) {
            deleteButton.setOnAction(actionEvent -> {
                Movie toRemove = tableView.getItems().get(getTableRow().getIndex());
                cineLabClient.delete("/movies", toRemove.getMovieID());
                try {
                    refreshTableView();
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                filterField.setText("");
            });
        }

        /**
         * Overrides the table cell and adds a button.
         *
         * @param item  The new item for the cell.
         * @param empty whether this cell represents data from the list. If it is empty,
         *              then it does not represent any domain data, but is a cell being used to render an "empty" row.
         */
        @Override
        protected void updateItem(Boolean item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setGraphic(null);
            } else {
                setGraphic(deleteButton);
            }
        }
    }
}
