package ch.henrici.fxnotes.ui;

import ch.henrici.fxnotes.model.Note;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class MainView {

    private final BorderPane root;

    private final ListView<Note> noteListView;
    private final TextField titleField;
    private final TextArea contentArea;
    private final Button newButton;
    private final Button deleteButton;
    private final Button saveButton;

    public MainView() {
        root = new BorderPane();
        root.getStyleClass().add("root-pane");

        Label notesLabel = new Label("Notes");
        noteListView = new ListView<>();
        newButton = new Button("New");
        deleteButton = new Button("Delete");

        HBox buttonBox = new HBox(5, newButton, deleteButton);
        VBox leftBox = new VBox(5, notesLabel, noteListView, buttonBox);
        leftBox.setPadding(new Insets(10));
        root.setLeft(leftBox);

        Label titleLabel = new Label("Title");
        titleField = new TextField();

        Label contentLabel = new Label("Content");
        contentArea = new TextArea();

        saveButton = new Button("Save");

        VBox centerBox = new VBox(5,
                titleLabel, titleField,
                contentLabel, contentArea,
                saveButton
        );
        centerBox.setPadding(new Insets(10));
        root.setCenter(centerBox);
    }

    public Parent getRoot() { return root; }

    public ListView<Note> getNoteListView() { return noteListView; }
    public TextField getTitleField() { return titleField; }
    public TextArea getContentArea() { return contentArea; }
    public Button getNewButton() { return newButton; }
    public Button getDeleteButton() { return deleteButton; }
    public Button getSaveButton() { return saveButton; }
}