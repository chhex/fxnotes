package ch.henrici.fxnotes.repository;

public interface SaveStrategy {
    void onMutation(AbstractNoteRepository repo);
    void onExit(AbstractNoteRepository repo);
}