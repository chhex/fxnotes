package ch.henrici.fxnotes.services;

import ch.henrici.fxnotes.model.Note;
import ch.henrici.fxnotes.repository.NoteRepository;

import java.util.List;
import java.util.Optional;

public class NoteService {

    private final NoteRepository repository;

    public NoteService(NoteRepository repository) {
        this.repository = repository;
    }

    public List<Note> getAllNotes() {
        return repository.findAll();
    }

    public Optional<Note> getNote(String id) {
        return repository.findById(id);
    }

    public Note createNote(Note newNote) {
        return repository.save(newNote);
    }

    public Note updateNote(Note note, String title, String content) {
        note.setTitle(title);
        note.setContent(content);
        return repository.save(note);
    }

    public void deleteNote(Note note) {
        repository.deleteById(note.getId());
    }
}