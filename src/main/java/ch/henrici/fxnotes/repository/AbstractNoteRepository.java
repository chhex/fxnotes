package ch.henrici.fxnotes.repository;

import ch.henrici.fxnotes.model.Note;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractNoteRepository implements NoteRepository {

    protected final List<Note> cache = new ArrayList<>();
    protected int idCounter = 1;

    private final SaveStrategy saveStrategy;

    protected AbstractNoteRepository(SaveStrategy saveStrategy) {
        this.saveStrategy = saveStrategy;
    }

    protected String nextId() {
        return String.valueOf(idCounter++);
    }

    @Override
    public List<Note> findAll() {
        return new ArrayList<>(cache);
    }

    @Override
    public Optional<Note> findById(String id) {
        return cache.stream()
                .filter(n -> n.getId().equals(id))
                .findFirst();
    }

    @Override
    public Note save(Note note) {
        // neue Note?
        if (note.getId() == null || note.getId().isBlank()) {
            note = new Note(nextId(), note.getTitle(), note.getContent());
        } else {
            deleteById(note.getId());
        }
        cache.add(note);
        saveStrategy.onMutation(this);
        return note;
    }

    @Override
    public void deleteById(String id) {
        cache.removeIf(n -> n.getId().equals(id));
        saveStrategy.onMutation(this);
    }

    @Override
    public void saveAll(List<Note> notes) {
        cache.clear();
        cache.addAll(notes);
        saveStrategy.onMutation(this);
    }

    /** wird von der Strategy aufgerufen, wenn wirklich geschrieben werden soll */
    protected abstract void writeToStorage();

    /** wird am Ende der App aufgerufen (z.B. in MainApp.stop()) */
    public void onExit() {
        saveStrategy.onExit(this);
    }

    /** Hilfsmethode, damit Strategy auf writeToStorage zugreifen kann */
    void performWrite() {
        writeToStorage();
    }
}