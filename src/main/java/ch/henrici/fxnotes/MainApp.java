package ch.henrici.fxnotes;

import ch.henrici.fxnotes.data.NoteRepository;
import ch.henrici.fxnotes.ui.MainController;
import ch.henrici.fxnotes.ui.MainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.nio.file.Path;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {

        boolean USE_MOCK = true; 

        NoteRepository repo =
                new NoteRepository(USE_MOCK, Path.of("data", "notes.txt"));

        MainView view = new MainView();
        MainController controller = new MainController(repo, view);
        controller.init();

        Scene scene = new Scene(view.getRoot(), 800, 600);
        stage.setScene(scene);
        stage.setTitle("FX Notes");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}