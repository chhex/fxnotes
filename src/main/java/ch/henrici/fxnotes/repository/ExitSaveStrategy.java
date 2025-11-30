package ch.henrici.fxnotes.repository;

public class ExitSaveStrategy implements SaveStrategy {

    private boolean dirty = false;

    @Override
    public void onMutation(AbstractNoteRepository repo) {
        dirty = true; // merken: es gibt ungespeicherte Änderungen
    }

    @Override
    public void onExit(AbstractNoteRepository repo) {
        if (dirty) {
            repo.performWrite();
            dirty = false;
        }
    }
}