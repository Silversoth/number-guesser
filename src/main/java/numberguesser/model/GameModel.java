package numberguesser.model;

import numberguesser.HandledException;
import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import static java.lang.System.err;

/**
 * The `GameModel` class represents the core logic and data management for the Number Guesser game.
 * It handles game state, player management, and persistence of player scores.
 */
public class GameModel {
    private static final String ERROR_MSG = "OPERATION FAILED: ";
    private static Set<Player> playerList = new HashSet<>();
    private int difficulty;
    private int tries;
    private int result;
    private int pointsWon;
    private Player currentPlayer;
    private RNG currentRNG;
    private final Map<Integer, RNG> difficultyMap;
    private final Map<Integer, Integer> pointsWonMap;

    /**
     * Constructs a `GameModel` object, initializing game settings and loading player data.
     *
     * @throws IOException if an I/O error occurs during loading
     * @throws HandledException if a handled exception occurs
     */
    public GameModel() throws IOException, HandledException {
        this.tries = 3;
        this.difficultyMap = new HashMap<>();
        this.pointsWonMap = new HashMap<>();
        initializeDifficultyMap();
        initializePointsWonMap();
        load();
    }

    /**
     * Initializes the difficulty map with predefined ranges for each difficulty level.
     */
    private void initializeDifficultyMap() {
        difficultyMap.put(1, new RNG(1, 10));
        difficultyMap.put(2, new RNG(1, 50));
        difficultyMap.put(3, new RNG(1, 100));
    }

    /**
     * Initializes the points won map with predefined points for each difficulty level.
     */
    private void initializePointsWonMap() {
        pointsWonMap.put(3, 100);
        pointsWonMap.put(2, 50);
        pointsWonMap.put(1, 25);
    }

    /**
     * Sets the game difficulty and initializes the random number generator for the selected difficulty.
     *
     * @param difficulty the game difficulty level
     * @throws HandledException if no difficulty is set
     */
    public void setDifficulty(int difficulty) throws HandledException {
        this.difficulty = difficulty;
        this.currentRNG = difficultyMap.get(difficulty);
        if (currentRNG == null) {
            throw new HandledException("VARIABLE_ERROR", "No difficulty set");
        }
        this.result = currentRNG.getResult();

    }

    /**
     * Retrieves the current game difficulty.
     *
     * @return the difficulty level
     */
    public int getDifficulty() {
        return this.difficulty;
    }

    /**
     * Sets the points won based on the number of tries left.
     *
     * @param triesLeft the number of tries left
     */
    public void setPointsWon(int triesLeft) {
        this.pointsWon = pointsWonMap.get(triesLeft);
    }

    /**
     * Retrieves the points won in the current game.
     *
     * @return the points won
     */
    public int getPointsWon() {
        return this.pointsWon;
    }

    /**
     * Sets the current player for the game.
     *
     * @param player the player to set
     */
    public void setPlayer(Player player) {
        this.currentPlayer = player;
    }

    /**
     * Retrieves the current player of the game.
     *
     * @return the current player
     */
    public Player getPlayer() {
        return this.currentPlayer;
    }

    /**
     * Checks if a player already exists and returns the existing player.
     *
     * @param player the player to check
     * @return the existing player if found, otherwise the input player
     */
    public Player playerExists(Player player) {
        if (playerList.contains(player)) {
            for (Player p : playerList) {
                if (p.equals(player)) {
                    player = p;
                }
            }
        }
        return player;
    }

    /**
     * Handles the player's guess and updates the game state.
     *
     * @param guess the player's guess
     * @return true if the guess is correct, false otherwise
     */
    public boolean handleGuess(int guess) {
        if (guess != result) {
            tries--;
            return false;
        } else {
            return true;
        }
    }

    /**
     * Resets the game to its initial state.
     */
    public void resetGame() {
        this.tries = 3;
        initializeDifficultyMap();
    }

    /**
     * Retrieves the number of tries left in the current game.
     *
     * @return the number of tries left
     */
    public int getTriesLeft() {
        return tries;
    }

    /**
     * Retrieves the result number for the current game.
     *
     * @return the result number
     */
    public int getResult() {
        return result;
    }

    /**
     * Retrieves the minimum number for the current game range.
     *
     * @return the minimum number
     */
    public int getMin() {
        return currentRNG.getMin();
    }

    /**
     * Retrieves the maximum number for the current game range.
     *
     * @return the maximum number
     */
    public int getMax() {
        return currentRNG.getMax();
    }

    /**
     * Saves the player list to a file for persistence.
     *
     * @param playerList the set of players to save
     * @throws IOException if an I/O error occurs during saving
     */
    public static void save(Set<Player> playerList) throws IOException {
        File scoreboard = new File("scoreboard.dat");
        if (!Files.exists(scoreboard.toPath())) {
            Files.createFile(scoreboard.toPath());
        }

        try (FileOutputStream fileOutputStream = new FileOutputStream(scoreboard);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {

            objectOutputStream.writeObject(playerList);

        } catch (IOException e) {
            err.println(ERROR_MSG + e.getMessage());
        }
    }

    /**
     * Loads the player list from a file at the start of the game.
     *
     * @throws IOException if an I/O error occurs during loading
     * @throws HandledException if a handled exception occurs
     */
    public static void load() throws IOException, HandledException {
        File scoreboard = new File("scoreboard.dat");
        if (!Files.exists(scoreboard.toPath())) {
            Files.createFile(scoreboard.toPath());
        }

        try (FileInputStream fileInputStream = new FileInputStream(scoreboard);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {

            playerList = (HashSet<Player>) objectInputStream.readObject();

        } catch (EOFException ignored) {
            // EOFException added here, so we don't get a message if the scoreboard file is empty
        } catch (IOException | ClassNotFoundException e) {
            err.println(ERROR_MSG + e.getMessage());
        }
    }

    /**
     * Retrieves the current list of players.
     *
     * @return the set of players
     */
    public static Set<Player> getPlayerList() {
        return playerList;
    }

    /**
     * Updates the player list with the current player's score and saves it.
     *
     * @param player the player to update
     * @param difficulty the difficulty level
     * @param points the points to add
     * @param playerList the set of players to update
     */
    public void updateList(Player player, int difficulty, int points, Set<Player> playerList) {
        // First update the player itself
        switch (difficulty) {
            case 1:
                player.setEasyCount(player.getEasyCount() + 1);
                player.setEasyPoints(player.getEasyPoints() + points);
                break;
            case 2:
                player.setMediumCount(player.getMediumCount() + 1);
                player.setMediumPoints(player.getMediumPoints() + points);
                break;
            case 3:
                player.setHardCount(player.getHardCount() + 1);
                player.setHardPoints(player.getHardPoints() + points);
                break;
            default:
        }

        playerList.remove(player);
        playerList.add(player);

        try {
            GameModel.save(playerList);
        } catch (IOException e) {
            System.err.println(ERROR_MSG + e.getMessage());
        }
    }
}