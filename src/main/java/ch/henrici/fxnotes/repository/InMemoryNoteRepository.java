package ch.henrici.fxnotes.repository;

import ch.henrici.fxnotes.model.Note;

public class InMemoryNoteRepository extends AbstractNoteRepository {

    public InMemoryNoteRepository(SaveStrategy saveStrategy) {
        super(saveStrategy);
        Note n1 = new Note(null,"Hali Hallo", "Das ist eine mock note.");
        Note n2 = new Note(null,"Hola", "Hola de un mock repository!");
        save(n1);
        save(n2);
    }

    @Override
    protected void writeToStorage() {
       // Do nothing
    }

}