package ch.henrici.fxnotes.repository;

public class ImmediateSaveStrategy implements SaveStrategy {

    @Override
    public void onMutation(AbstractNoteRepository repo) {
        repo.performWrite(); // bei jeder Änderung speichern
    }

    @Override
    public void onExit(AbstractNoteRepository repo) {
        // Nichts zu tun
    }
}