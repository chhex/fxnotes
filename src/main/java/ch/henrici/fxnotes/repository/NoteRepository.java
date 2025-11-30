package ch.henrici.fxnotes.repository;

import ch.henrici.fxnotes.model.Note;

import java.util.List;
import java.util.Optional;

public interface NoteRepository {

    List<Note> findAll();

    Optional<Note> findById(String id);

    Note save(Note note);

    void deleteById(String id);

    void saveAll(List<Note> notes);
}