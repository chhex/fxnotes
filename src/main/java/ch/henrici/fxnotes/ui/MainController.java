package ch.henrici.fxnotes.ui;

import ch.henrici.fxnotes.repository.NoteRepository;
import ch.henrici.fxnotes.services.NoteService;
import ch.henrici.fxnotes.model.Note;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MainController {

    private final NoteService service;
    private final MainView view;

    private final ObservableList<Note> notes = FXCollections.observableArrayList();

    public MainController(NoteService noteService, MainView view) {
        this.service = noteService;
        this.view = view;
    }

    public void init() {
        notes.setAll(service.getAllNotes());
        view.getNoteListView().setItems(notes);

        view.getNoteListView().getSelectionModel().selectedItemProperty()
                .addListener((obs, oldNote, newNote) -> {
                    if (newNote != null) {
                        view.getTitleField().setText(newNote.getTitle());
                        view.getContentArea().setText(newNote.getContent());
                    } else {
                        view.getTitleField().clear();
                        view.getContentArea().clear();
                    }
                });

        view.getNewButton().setOnAction(e -> onNew());
        view.getDeleteButton().setOnAction(e -> onDelete());
        view.getSaveButton().setOnAction(e -> onSave());
    }

    private void onNew() {
        Note newNote = new Note("", "New Note", ""); // NOTE ID leer → Repository vergibt neue ID
        Note saved = service.createNote(newNote);
        notes.add(saved);
        view.getNoteListView().getSelectionModel().select(saved);
    }

    private void onDelete() {
        Note selected = view.getNoteListView().getSelectionModel().getSelectedItem();

        if (selected != null) {
            service.deleteNote(selected);
            notes.remove(selected);
        }
    }

    private void onSave() {
        Note selected = view.getNoteListView().getSelectionModel().getSelectedItem();

        if (selected == null) return;
        
        service.updateNote(selected,view.getTitleField().getText(),view.getContentArea().getText());
        view.getNoteListView().refresh();
    }
}