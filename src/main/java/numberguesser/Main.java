package numberguesser;

import javafx.application.Application;
import javafx.stage.Stage;
import numberguesser.controller.GameController;
import numberguesser.model.GameModel;
import java.io.*;
import javafx.scene.Scene;
import numberguesser.view.GameView;


/**
 * The `Main` class serves as the entry point for the Number Guesser application.
 * It extends the `Application` class from JavaFX to set up and launch the graphical user interface.
 * This class initializes the game model, controller, and view, and sets the initial scene for the application.
 */
public class Main extends Application {
    private static final String ERROR_MSG = "OPERATION FAILED: ";

    /**
     * The main method that launches the JavaFX application.
     *
     * @param args command-line arguments passed to the application
     * @throws IOException if an I/O error occurs during application launch
     */
    public static void main(String[] args) throws IOException {
        launch(args);
    }

    /**
     * Starts the JavaFX application and sets the initial scene.
     *
     * @param primaryStage the primary stage for the application
     * @throws IOException if an I/O error occurs during initialization
     * @throws HandledException if a handled exception occurs during initialization
     */
    public void start(Stage primaryStage) throws IOException, HandledException {
        // Initialize the model
        GameModel gameModel = new GameModel();

        // Initialize the controller with the model
        GameController gameController = new GameController(gameModel);

        // Initialize the view
        GameView gameView = new GameView(gameModel, gameController, primaryStage);

        // Prepare the initial scene and stage
        Scene initialScene = gameView.buildInitialScene();
        primaryStage.setScene(initialScene); // SCENE 1 -> FROM GAMEVIEW THE OTHER SCENES WILL CONTINUE
        primaryStage.setTitle("Number Guesser");
        primaryStage.show();
    }
}

