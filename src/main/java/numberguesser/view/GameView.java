package numberguesser.view;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import numberguesser.HandledException;
import numberguesser.controller.GameController;
import numberguesser.model.GameModel;
import numberguesser.model.Player;
import org.apache.commons.lang3.math.NumberUtils;
import java.io.IOException;

/**
 * The `GameView` class is responsible for managing the user interface of the Number Guesser game.
 * It constructs and transitions between different scenes, such as the initial scene, scoreboard scene,
 * game scene, and end scenes (win/lose). This class interacts with the `GameController` to process user inputs
 * and update the game state accordingly. It also handles displaying alerts and messages to the user.
 */
public class GameView {
    private final BorderPane root;
    private final GameController controller;
    private final Stage primaryStage;
    private Label triesLabel; // Instance variable so it can be updated

    /**
     * Constructs a GameView with the specified model, controller, and primary stage.
     *
     * @param controller the game controller
     * @param primaryStage the primary stage for the application
     */
    public GameView(GameController controller, Stage primaryStage) {
        this.controller = controller;
        this.primaryStage = primaryStage;
        root = new BorderPane();
    }

    /**
     * Builds the initial scene where the player enters their name.
     *
     * @return the initial scene
     */
    public Scene buildInitialScene() {
        Label nameLabel = new Label("Enter your name:");
        TextField nameInput = new TextField();
        Button submitButton = new Button("Submit");

        submitButton.setOnAction(event -> handleNameInput(nameInput, nameLabel));

        VBox vbox = new VBox(10, nameLabel, nameInput, submitButton);
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setPadding(new Insets(20));

        BorderPane root = new BorderPane();
        root.setCenter(vbox);

        return new Scene(root, 800, 600);
    }

    /**
     * Handles the name input and transitions to the scoreboard scene.
     *
     * @param nameInput the text field for name input
     * @param nameLabel the label for displaying messages
     */
    private void handleNameInput(TextField nameInput, Label nameLabel) {
        String playerName = nameInput.getText();
        if (!playerName.isEmpty()) {
            controller.setCurrentPlayer(playerName);
            try {
                Scene scoreboardScene = buildScoreboardScene(controller.getGamePlayer(), false);
                primaryStage.setScene(scoreboardScene);
            } catch (IOException e) {
                nameLabel.setText("Error loading scoreboard.");
            }
        } else {
            nameLabel.setText("Please enter a valid name.");
        }
    }

    /**
     * Builds the scoreboard scene where the player selects the difficulty level.
     *
     * @param player the current player
     * @param isTrue flag indicating if it's a retry
     * @return the scoreboard scene
     * @throws IOException if an I/O error occurs
     */
    public Scene buildScoreboardScene(Player player, boolean isTrue) throws IOException {
        String playerScore = "";
        if (!isTrue) playerScore = GameController.showPlayerScore(player, isTrue);

        Label scoreboardLabel = new Label(playerScore);
        Label difficultyLabel = new Label("Enter difficulty (1-3):");
        TextField difficultyInput = new TextField();
        Button submitButton = new Button("Start!");

        submitButton.setOnAction(event -> handleDifficultyInput(difficultyInput, difficultyLabel));

        VBox vbox = new VBox(10, scoreboardLabel, difficultyLabel, difficultyInput, submitButton);
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setPadding(new Insets(20));

        BorderPane root = new BorderPane();
        root.setCenter(vbox);

        return new Scene(root, 800, 600);
    }

    /**
     * Handles the difficulty input and transitions to the game scene.
     *
     * @param difficultyInput the text field for difficulty input
     * @param difficultyLabel the label for displaying messages
     */
    private void handleDifficultyInput(TextField difficultyInput, Label difficultyLabel) {
        String input = difficultyInput.getText();
        try {
            if (!input.isEmpty()) {
                if (!controller.validateDifficultyInput(input)) {
                    showAlert(AlertType.WARNING, "Invalid Input", "Please enter a valid difficulty (1-3)");
                }

                if (controller.validateDifficultyInput(input)) {
                    int difficulty = NumberUtils.createInteger(input);
                    Scene gameScene = buildGameScene(controller.getGamePlayer(), difficulty);
                    primaryStage.setScene(gameScene);
                }

            } else {
                showAlert(AlertType.WARNING, "Invalid Input", "Please enter a valid difficulty (1-3)");
            }
        } catch (HandledException e) {
            difficultyLabel.setText(e.getMessage());
        }
    }

    /**
     * Builds the game scene where the player makes guesses.
     *
     * @param player the current player
     * @param difficulty the selected difficulty level
     * @return the game scene
     * @throws HandledException if a handled exception occurs
     */
    public Scene buildGameScene(Player player, int difficulty) throws HandledException {
        controller.setGameDifficulty(difficulty);
        int min = controller.getGameMin();
        int max = controller.getGameMax();
        int tries = controller.getGameTries();
        String difficultyString = controller.getGameDifficultyString();

        Label diffLevelLabel = new Label(difficultyString);
        triesLabel = new Label(String.format("TRIES LEFT: %d", tries));
        Label rangeLabel = new Label(String.format("THE NUMBER YOU SEEK IS BETWEEN %d AND %d, ENTER YOUR GUESS: ", min, max));
        TextField guessInput = new TextField();
        Button submitButton = new Button("Submit");
        submitButton.setId("guessSubmitButton");

        submitButton.setOnAction(event -> handleGuessInput(guessInput));

        VBox vbox = new VBox(10, diffLevelLabel, triesLabel, rangeLabel, guessInput, submitButton);
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setPadding(new Insets(20));

        BorderPane root = new BorderPane();
        root.setTop(vbox);

        return new Scene(root, 800, 600);
    }

    /**
     * Handles the guess input and processes the game logic.
     *
     * @param guessInput the text field for guess input
     */
    private void handleGuessInput(TextField guessInput) {
        String input = guessInput.getText();
        int min = controller.getGameMin();
        int max = controller.getGameMax();
        Player player = controller.getGamePlayer();
        int difficulty = controller.getGameDifficultyInt();

        try {
            if (!input.isEmpty()) {
                if (!controller.validateGuessInput(input)) {
                    showAlert(Alert.AlertType.WARNING, "Invalid Input", String.format("Please enter a number between %d and %d", min, max));
                } else {
                    int guess = NumberUtils.createInteger(input);
                    String result = controller.handleGuess(guess);

                    int triesLeft = controller.getGameTries();
                    triesLabel.setText(String.format("TRIES LEFT: %d", triesLeft));

                    switch (result) {
                        case "WIN" -> {
                            showAlert(Alert.AlertType.INFORMATION, "Congratulations!", String.format("CONGRATULATIONS!!! %d WAS THE CORRECT NUMBER", controller.getGameResult()));
                            controller.setGamePointsWon();
                            int points = controller.getGamePointsWon();
                            controller.updateListCall(player, difficulty, points);
                            Scene endScene = buildWinScene(controller.getGamePlayer());
                            primaryStage.setScene(endScene);
                        }
                        case "LOST" -> {
                            showAlert(Alert.AlertType.INFORMATION, "Game Over", "You've run out of tries!");
                            controller.updateListCall(player, difficulty, 0);
                            Scene endScene = buildLoseScene();
                            primaryStage.setScene(endScene);
                        }
                        case "HIGHER" -> showAlert(Alert.AlertType.INFORMATION, "Wrong", String.format("The result is higher than %d", guess));
                        case "LOWER" -> showAlert(Alert.AlertType.INFORMATION, "Wrong", String.format("The result is lower than %d", guess));
                    }
                }

            } else {
                showAlert(Alert.AlertType.WARNING, "Invalid Input", String.format("Please enter a number between %d and %d", min, max));
            }

        } catch (HandledException | IOException e) {
            showAlert(AlertType.ERROR, "Error", e.getMessage());
        }
    }

    /**
     * Builds the victory scene displayed when the player wins.
     *
     * @param player the current player
     * @return the victory scene
     * @throws IOException if an I/O error occurs
     */
    public Scene buildWinScene(Player player) throws IOException {
        String difficultyString = controller.getGameDifficultyString();
        int triesLeft = controller.getGameTries();
        int points = controller.getGamePointsWon();
        String playerScore = GameController.showPlayerScore(player, true);
        Button playButton = new Button("Play Again");
        Button exitButton = new Button("Close");
        String triesMsg = String.format("You won with %d try left!", triesLeft);
        if (triesLeft > 1) triesMsg = String.format("You won with %d tries left!", triesLeft);

        Label diffLevelLabel = new Label(difficultyString);
        Label winTriesLabel = new Label(triesMsg);
        Label pointsWonLabel = new Label(String.format("You've won %d points!!", points));
        Label scoreboardTitleLabel = new Label("YOUR POST MATCH SCORES: ");
        Label scoreboardLabel = new Label(String.format(playerScore));
        Label playAgainLabel = new Label(String.format("Would you like to play again %s?", player.getName()));

        playButton.setOnAction(event -> {
            try {
                controller.resetGame();
                handleRestartButton();
            } catch (IOException e) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Scene Loading Error");
                alert.setContentText("There was an error loading the scoreboard scene.");
                alert.showAndWait();
            }
        });

        exitButton.setOnAction(event -> handleExitButton());

        VBox vbox = new VBox(10, diffLevelLabel, winTriesLabel, pointsWonLabel, scoreboardTitleLabel, scoreboardLabel, playAgainLabel, playButton, exitButton);
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setPadding(new Insets(20));

        BorderPane root = new BorderPane();
        root.setTop(vbox);

        return new Scene(root, 800, 600);
    }

    /**
     * Builds the losing scene displayed when the player loses.
     *
     * @return the losing scene
     * @throws IOException if an I/O error occurs
     * @throws HandledException if a handled exception occurs
     */
    public Scene buildLoseScene() throws IOException, HandledException {
        Player player = controller.getGamePlayer();
        String difficultyString = controller.getGameDifficultyString();
        String playerScore = GameController.showPlayerScore(player, true);
        Button playButton = new Button("Play Again");
        Button exitButton = new Button("Close");

        Label diffLevelLabel = new Label(difficultyString);
        Label loseLabel = new Label(String.format("Oh no %s! YOU LOSE", player.getName()));
        Label scoreboardTitleLabel = new Label("YOUR POST MATCH SCORES: ");
        Label scoreboardLabel = new Label(String.format(playerScore));
        Label playAgainLabel = new Label(String.format("Would you like to play again %s?", player.getName()));

        playButton.setOnAction(event -> {
            try {
                controller.resetGame();
                handleRestartButton();
            } catch (IOException e) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Scene Loading Error");
                alert.setContentText("There was an error loading the scoreboard scene.");
                alert.showAndWait();
            }
        });

        exitButton.setOnAction(event -> handleExitButton());

        VBox vbox = new VBox(10, diffLevelLabel, scoreboardTitleLabel, scoreboardLabel, playAgainLabel, playButton, exitButton);
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setPadding(new Insets(20));

        BorderPane root = new BorderPane();
        root.setTop(vbox);

        return new Scene(root, 800, 600);
    }

    /**
     * Handles the restart button action, transitioning back to the scoreboard scene.
     *
     * @throws IOException if an I/O error occurs
     */
    private void handleRestartButton() throws IOException {
        controller.resetGame();
        Scene scoreboardScene = buildScoreboardScene(controller.getGamePlayer(), true);
        primaryStage.setScene(scoreboardScene);
    }

    /**
     * Handles the exit button action, closing the application.
     */
    private void handleExitButton() {
        Platform.exit();
    }

    /**
     * Displays an alert with the specified type, title, and message.
     *
     * @param alertType the type of alert
     * @param title the title of the alert
     * @param message the message to display in the alert
     */
    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Gets the root node of the UI.
     *
     * @return the root node
     */
    public Parent getRoot() {
        return root;
    }

}