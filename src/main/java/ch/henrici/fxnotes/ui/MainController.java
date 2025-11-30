package ch.henrici.fxnotes.ui;

import ch.henrici.fxnotes.data.NoteRepository;
import ch.henrici.fxnotes.model.Note;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MainController {

    private final NoteRepository repo;
    private final MainView view;

    private final ObservableList<Note> notes =
            FXCollections.observableArrayList();

    public MainController(NoteRepository repo, MainView view) {
        this.repo = repo;
        this.view = view;
    }

    public void init() {
        notes.setAll(repo.findAll());
        view.getNoteListView().setItems(notes);

        view.getNoteListView().getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, o, n) -> {
                    if (n != null) {
                        view.getTitleField().setText(n.getTitle());
                        view.getContentArea().setText(n.getContent());
                    }
                });

        view.getNewButton().setOnAction(e -> onNew());
        view.getDeleteButton().setOnAction(e -> onDelete());
        view.getSaveButton().setOnAction(e -> onSave());
    }

    private void onNew() {
        Note n = repo.save(new Note("", "New Note", ""));
        notes.add(n);
        view.getNoteListView().getSelectionModel().select(n);
    }

    private void onDelete() {
        Note n = view.getNoteListView().getSelectionModel().getSelectedItem();
        if (n != null) {
            repo.deleteById(n.getId());
            notes.remove(n);
        }
    }

    private void onSave() {
        Note n = view.getNoteListView().getSelectionModel().getSelectedItem();
        if (n != null) {
            n.setTitle(view.getTitleField().getText());
            n.setContent(view.getContentArea().getText());
            repo.save(n);
            view.getNoteListView().refresh();
        }
    }
}