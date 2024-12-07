package numberguesser.controller;

import numberguesser.HandledException;
import numberguesser.model.GameModel;
import numberguesser.model.Player;
import org.apache.commons.lang3.math.NumberUtils;
import java.io.IOException;
import java.util.Set;

/**
 * The `GameController` class manages the interaction between the user interface and the game logic.
 * It acts as a mediator between the `GameView` and `GameModel`, handling user inputs, updating the game state,
 * and providing feedback to the user. This class is responsible for setting up the game, processing player actions,
 * and maintaining the flow of the game.
 */
public class GameController {
    private static final String ERROR_MSG = "OPERATION FAILED: ";
    private GameModel game;

    /**
     * Constructs a GameController with the specified GameModel.
     * Redirects error streams to a file.
     *
     * @param game the GameModel instance to control
     */
    public GameController(GameModel game) {
        this.game = game;
        HandledException.redirectErrorStream(); // Redirect errors to file
    }

    /**
     * Sets the current player for the game.
     *
     * @param playerName the name of the player
     */
    public void setCurrentPlayer(String playerName) {
        Player player = new Player(playerName);
        player = getPlayerExists(player);
        game.setPlayer(player);
    }

    /**
     * Sets the difficulty level for the current game.
     *
     * @param difficulty the difficulty level to set
     * @throws HandledException if the difficulty is invalid
     */
    public void setGameDifficulty(int difficulty) throws HandledException {
        game.setDifficulty(difficulty);
    }

    /**
     * Retrieves the current player of the game.
     *
     * @return the current Player
     */
    public Player getGamePlayer() {
        return game.getPlayer();
    }

    /**
     * Checks if a player already exists and returns the existing player.
     *
     * @param player the player to check
     * @return the existing Player if found, otherwise the input player
     */
    public Player getPlayerExists(Player player) {
        return game.playerExists(player);
    }

    /**
     * Retrieves the minimum number for the current game range.
     *
     * @return the minimum number
     */
    public int getGameMin() {
        return game.getMin();
    }

    /**
     * Retrieves the maximum number for the current game range.
     *
     * @return the maximum number
     */
    public int getGameMax() {
        return game.getMax();
    }

    /**
     * Retrieves the number of tries left in the current game.
     *
     * @return the number of tries left
     */
    public int getGameTries() {
        return game.getTriesLeft();
    }

    /**
     * Retrieves the result number for the current game.
     *
     * @return the result number
     */
    public int getGameResult() {
        return game.getResult();
    }

    /**
     * Retrieves the current scoreboard as a set of players.
     *
     * @return the set of players in the scoreboard
     */
    public Set<Player> getCurrentScoreboard() {
        return GameModel.getPlayerList();
    }

    /**
     * Retrieves the difficulty level as a string.
     *
     * @return the difficulty level string
     */
    public String getGameDifficultyString() {
        String result = switch (getGameDifficultyInt()) {
            case 3 -> "DIFFICULTY: HARD";
            case 2 -> "DIFFICULTY: MEDIUM";
            case 1 -> "DIFFICULTY: EASY";
            default -> "Unknown difficulty value";
        };

        return result;
    }

    /**
     * Retrieves the difficulty level as an integer.
     *
     * @return the difficulty level integer
     */
    public int getGameDifficultyInt() {
        return game.getDifficulty();
    }

    /**
     * Sets the points won in the current game based on remaining tries.
     */
    public void setGamePointsWon() {
        game.setPointsWon(getGameTries());
    }

    /**
     * Retrieves the points won in the current game.
     *
     * @return the points won
     */
    public int getGamePointsWon() {
        return game.getPointsWon();
    }

    /**
     * Returns the player's score or an appropriate message as a string.
     *
     * @param player the player whose score is to be shown
     * @param isRetry flag indicating if it's a retry
     * @return the player's score as a string
     * @throws IOException if an I/O error occurs
     */
    public static String showPlayerScore(Player player, boolean isRetry) throws IOException {
        StringBuilder scoreBuilder = new StringBuilder();
        Set<Player> playerList = GameModel.getPlayerList();
        if (playerList != null && player != null && !playerList.isEmpty()) {
            if (playerList.contains(player)) {
                if (isRetry) {
                    scoreBuilder.append(String.format("POST MATCH SCORE FOR %s:%n", player.getName()));
                } else {
                    scoreBuilder.append(String.format("Welcome back %s!%nYour current scores are:%n", player.getName()));
                }
                scoreBuilder.append(String.format("EASY DIFFICULTY: %d points in %d tries%n", player.getEasyPoints(), player.getEasyCount()));
                scoreBuilder.append(String.format("MEDIUM DIFFICULTY: %d points in %d tries%n", player.getMediumPoints(), player.getMediumCount()));
                scoreBuilder.append(String.format("HARD DIFFICULTY: %d points in %d tries%n%n", player.getHardPoints(), player.getHardCount()));
            } else {
                scoreBuilder.append(String.format("Hello %s!%n", player.getName()));
            }
        } else {
            if (player == null) {
                return "null player";
            }
            scoreBuilder.append(String.format("Hello %s! You're our first player!%n", player.getName()));
        }
        return scoreBuilder.toString();
    }

    /**
     * Validates the difficulty input.
     *
     * @param input the difficulty input as a string
     * @return true if the input is valid, false otherwise
     * @throws HandledException if an error occurs during validation
     */
    public boolean validateDifficultyInput(String input) throws HandledException {
        if (!NumberUtils.isCreatable(input)) {
            return false;
        }

        int difficulty = NumberUtils.createInteger(input);
        return difficulty >= 1 && difficulty <= 3;
    }

    /**
     * Handles the guess input and returns the result as a string.
     *
     * @param guess the player's guess
     * @return "WIN", "LOST", "HIGHER", or "LOWER" based on the guess
     */
    public String handleGuess(int guess) {
        if (!game.handleGuess(guess)) {
            if (game.getTriesLeft() == 0) {
                return "LOST";
            }
            return game.getResult() > guess ? "HIGHER" : "LOWER";
        }
        return "WIN";
    }

    /**
     * Validates the guess input.
     *
     * @param input the guess input as a string
     * @return true if the input is valid, false otherwise
     * @throws HandledException if an error occurs during validation
     */
    public boolean validateGuessInput(String input) throws HandledException {
        if (!NumberUtils.isCreatable(input)) {
            return false;
        }

        int guess = NumberUtils.createInteger(input);
        return guess >= game.getMin() && guess <= game.getMax();
    }

    /**
     * Updates the player list with the current player's score.
     *
     * @param player the player to update
     * @param difficulty the difficulty level
     * @param points the points to add
     */
    public void updateListCall(Player player, int difficulty, int points) {
        Set<Player> playerList = GameModel.getPlayerList();
        game.updateList(player, difficulty, points, playerList);
    }

    /**
     * Resets the game to its initial state.
     */
    public void resetGame() {
        game.resetGame();
    }
}