package ch.henrici.fxnotes;

import java.nio.file.Path;

import ch.henrici.fxnotes.repository.AbstractNoteRepository;
import ch.henrici.fxnotes.repository.CsvFileNoteRepository;
import ch.henrici.fxnotes.repository.ExitSaveStrategy;
import ch.henrici.fxnotes.repository.ImmediateSaveStrategy;
import ch.henrici.fxnotes.repository.InMemoryNoteRepository;
import ch.henrici.fxnotes.repository.NoteRepository;
import ch.henrici.fxnotes.repository.SaveStrategy;
import ch.henrici.fxnotes.services.NoteService;
import ch.henrici.fxnotes.ui.MainController;
import ch.henrici.fxnotes.ui.MainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    private AbstractNoteRepository repoForExit;

   @Override
public void start(Stage stage) {
    boolean USE_MOCK = true;
    boolean SAVE_ON_EXIT = true; // <– Strategie-Wahl

    SaveStrategy strategy = SAVE_ON_EXIT
            ? new ExitSaveStrategy()
            : new ImmediateSaveStrategy();

    AbstractNoteRepository repoImpl = USE_MOCK
            ? new InMemoryNoteRepository(strategy)
            : new CsvFileNoteRepository(Path.of("data", "notes.txt"), strategy);

    NoteRepository noteRepo = repoImpl;  // für Service/Controller
    NoteService service = new NoteService(noteRepo);

    MainView view = new MainView();
    MainController controller = new MainController(service, view);
    controller.init();

    Scene scene = new Scene(view.getRoot(), 800, 600);
    stage.setScene(scene);
    stage.setTitle("FX Notes");
    stage.show();

    this.repoForExit = repoImpl; // als Field speichern
}

@Override
public void stop() {
    if (repoForExit != null) {
        repoForExit.onExit(); // -> Strategy.onExit -> ggf. writeToStorage()
    }
}

    public static void main(String[] args) {
        launch(args);
    }
}