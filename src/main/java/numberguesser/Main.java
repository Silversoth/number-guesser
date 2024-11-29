package numberguesser;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

import static java.lang.System.*;

/**
 * The `Main` class serves as the entry point for the Number Guesser game.
 * It manages the game flow, including initializing the game environment,
 * handling player interactions, and maintaining player scores across sessions.
 * The class includes methods for loading and saving player data, managing
 * game difficulty, and handling user input and output.
 */
public class Main {
    private static final String ERROR_MSG = "OPERATION FAILED: ";

    /**
     * The main method to start the Number Guesser game.
     * Initializes the game environment, loads player data, and manages game flow.
     *
     * @param args Command line arguments
     * @throws IOException If an input or output exception occurs
     */
    public static void main(String[] args) throws IOException {
        errorHandler();
        Set<Player> playerList;
        playerList = load();

        out.println("WELCOME TO THE NUMBER GUESSER GAME \n");
        out.println("What is your name: ");


        try {
            Scanner sc = new Scanner(in);
            //KEYBOARD INPUT: NAME
            String name = sc.nextLine();
            Player player = new Player(name);
            boolean Retry = false;

            //SEARCH FOR PLAYER IN SCOREBOARD
            player = playerExists(playerList, player);

            //SHOW THEIR SCORE BEFORE MATCH
            showPlayerScore(playerList, player, Retry);

            int inputDiff;
            boolean RUNNING = true;

            while (RUNNING) {

                inputDiff = getValidDifficulty(sc);
                //ONLY PASS THIS IF THE INPUT WAS WITHIN ACCEPTABLE RANGE
                //DIFFICULTY LEVEL
                //USING A MAP TO STORE THE 3 DIFFICULTIES
                Map<Integer, RNG> difficultyMap = new HashMap<>();
                difficultyMap.put(1, new RNG(1, 10));
                difficultyMap.put(2, new RNG(1, 50));
                difficultyMap.put(3, new RNG(1, 100));

                RNG game = difficultyMap.get(inputDiff);
                if (game != null) {
                    gameStart(player, game, playerList, inputDiff);
                } else throw new HandledException("VARIABLE_ERROR", "No difficulty set");


                save(playerList);
                //SET RETRY TO TRUE BECAUSE ANY FURTHER MATCHES ARE RETRIES
                Retry = true;
                showPlayerScore(playerList, player, Retry);

                String input;

                do {
                    out.println("Would you like to play again? (y/n)");
                    input = sc.next();
                } while (!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("n"));


                if (input.equalsIgnoreCase("n")) RUNNING = false;
            }

        } catch (Exception e) {
            err.println(ERROR_MSG + e.getMessage());
        }


    }//END OF MAIN

    //GET VALID DIFFICULTY PASSING THE SCANNER

    /**
     * Prompts the user to select a difficulty level and ensures the input is valid.
     *
     * @param sc Scanner object for reading user input
     * @return The selected difficulty level as an integer
     */
    public static int getValidDifficulty(Scanner sc) {
        int difficulty;
        do {
            out.println("please choose a difficulty from 1 to 3");
            while (!sc.hasNextInt()) {
                out.println("please choose a difficulty from 1 to 3, type: 1, 2 or 3");
                sc.next();
            }
            difficulty = sc.nextInt();
        } while (difficulty < 1 || difficulty > 3);
        return difficulty;
    }

    /**
     * Starts the game for the given player with the specified difficulty and random number generator.
     *
     * @param player     The player object
     * @param random     The RNG object for generating numbers
     * @param playerList The set of all players
     * @param difficulty The difficulty level of the game
     */
    public static void gameStart(Player player, RNG random, Set<Player> playerList, int difficulty) {
        int tries = 3;
        int result = random.result;

        out.printf("guess the number from %d to %d. You have %d tries left %n", random.min, random.max, tries);

        //GET THE GUESS
        Scanner sc = new Scanner(in);
        while (tries > 0) {
            tries = handleGuess(sc, random, tries, result, player, difficulty, playerList);
        }
    }

    /**
     * Handles the player's guess, checks if it matches the result, and updates the number of tries.
     *
     * @param sc         Scanner object for reading user input
     * @param random     The RNG object for generating numbers
     * @param tries      The number of tries left
     * @param result     The correct number to guess
     * @param player     The player object
     * @param difficulty The difficulty level of the game
     * @param playerList The set of all players
     * @return The updated number of tries
     */
    private static int handleGuess(Scanner sc, RNG random, int tries, int result, Player player, int difficulty,
                                   Set<Player> playerList) {
        int guess;
        while (!sc.hasNextInt()) {
            out.printf("please enter a number between %d and %d, you have %d tries left %n", random.min, random.max, tries);
            sc.next();
        }
        guess = sc.nextInt();

        //IF FAILED
        if (guess != result) {
            tries--;
            getFail(guess, tries, result, player, difficulty, playerList);
        } else {
            //IF SUCCESS
            getSuccess(tries, player, difficulty, playerList);
            tries = 0; // Exit loop
        }
        return tries;
    }

    /**
     * Handles the scenario when the player's guess is incorrect.
     * Provides feedback and updates the player's score if no tries are left.
     *
     * @param guess      The player's guess
     * @param tries      The number of tries left
     * @param result     The correct number to guess
     * @param player     The player object
     * @param difficulty The difficulty level of the game
     * @param playerList The set of all players
     */
    public static void getFail(int guess, int tries, int result, Player player, int difficulty, Set<Player> playerList) {
        int points = 0;
        if (tries == 0) {
            out.printf("GAME OVER! The number was: %d %n%n", result);
            updateList(player, difficulty, points, playerList);
        }
        if (guess < result) {
            out.println("Wrong! Guess again! " + guess + " is below the number! " + tries + " tries left");
        } else out.println("Wrong! Guess again! " + guess + " is above the number! " + tries + " tries left");
    }

    /**
     * Handles the scenario when the player's guess is correct.
     * Awards points based on the number of tries left and updates the player's score.
     *
     * @param tries      The number of tries left
     * @param player     The player object
     * @param difficulty The difficulty level of the game
     * @param playerList The set of all players
     */
    public static void getSuccess(int tries, Player player, int difficulty, Set<Player> playerList) {
        //ENHANCED SWITCH
        int points = switch (tries) {
            case 3 -> 100;
            case 2 -> 50;
            case 1 -> 25;
            default -> 0;
        };
        out.printf("you get %d pts!!!! %n", points);
        updateList(player, difficulty, points, playerList);
    }

    /**
     * Saves the current state of the player list to a file.
     *
     * @param playerList The set of all players
     * @throws IOException If an input or output exception occurs
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
     * Updates the player's score and difficulty level in the player list.
     *
     * @param player     The player object
     * @param difficulty The difficulty level of the game
     * @param points     The points to be added to the player's score
     * @param playerList The set of all players
     */
    public static void updateList(Player player, int difficulty, int points, Set<Player> playerList) {
        //FIRST UPDATE THE PLAYER ITSELF
        switch (difficulty) {
            case 1:
                player.setCount1(player.getCount1() + 1);
                player.setPoints1(player.getPoints1() + points);
                break;
            case 2:
                player.setCount2(player.getCount2() + 1);
                player.setPoints2(player.getPoints2() + points);
                break;
            case 3:
                player.setCount3(player.getCount3() + 1);
                player.setPoints3(player.getPoints3() + points);
                break;
            default:
        }

        playerList.remove(player);
        playerList.add(player);

    }

    /**
     * Loads the player data from a file and returns it as a set.
     *
     * @return A set of players loaded from the file
     * @throws IOException If an input or output exception occurs
     */
    public static Set<Player> load() throws IOException {
        Set<Player> playerList = new HashSet<>();
        File scoreboard = new File("scoreboard.dat");
        if (!Files.exists(scoreboard.toPath())) {
            Files.createFile(scoreboard.toPath());
        }

        try (FileInputStream fileInputStream = new FileInputStream(scoreboard);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {


            playerList = (HashSet<Player>) objectInputStream.readObject();


        } catch (
                EOFException ignored) {//EOFException added here, so we don't get a message if the scoreboard file is empty

        } catch (IOException | ClassNotFoundException e) {
            err.println(ERROR_MSG + e.getMessage());
        }

        return playerList;
    }

    /**
     * Displays the player's score before and after the match.
     * If the match is a retry, it shows the post-match score; otherwise, it shows the current score.
     * Handles cases where the player is new or the first player ever.
     *
     * @param playerList The set of all players
     * @param player     The player object
     * @param isRetry    Indicates if the current match is a retry
     */
    public static void showPlayerScore(Set<Player> playerList, Player player, boolean isRetry) {
        if (playerList != null && player != null && !playerList.isEmpty()) {
            if (playerList.contains(player)) {
                if (isRetry) {
                    out.printf("POST MATCH SCORE FOR %s: %n", player.getName());
                    out.printf("EASY DIFFICULTY: %d points in %d tries %n", player.getPoints1(), player.getCount1());
                    out.printf("MEDIUM DIFFICULTY: %d points in %d tries %n", player.getPoints2(), player.getCount2());
                    out.printf("HARD DIFFICULTY: %d points in %d tries %n%n", player.getPoints3(), player.getCount3());
                } else {
                    out.println("Welcome back " + player.getName() + "!\n");
                    out.println("your current scores are: ");
                    out.printf("EASY DIFFICULTY: %d points in %d tries %n", player.getPoints1(), player.getCount1());
                    out.printf("MEDIUM DIFFICULTY: %d points in %d tries %n", player.getPoints2(), player.getCount2());
                    out.printf("HARD DIFFICULTY: %d points in %d tries %n%n", player.getPoints3(), player.getCount3());
                }
            } else {
                //IF PLAYER DOES NOT EXIST
                out.printf("hello %s! %n", player.getName());
            }
        } else {
            if (player == null) {
                out.println("null player");
                return;
            }
            //IF FIRST PLAYER EVER
            out.printf("Hello %s! You're our first player! %n", player.getName());
        }
    }

    /**
     * Checks if the player already exists in the player list and returns the player object.
     *
     * @param playerList The set of all players
     * @param player     The player object to check
     * @return The existing player object if found, otherwise the new player object
     */
    public static Player playerExists(Set<Player> playerList, Player player) {
        if (!playerList.isEmpty()) {
            for (Player i : playerList) {
                if (i.getName().equals(player.getName())) {
                    player = i;
                }
            }
        }
        return player;
    }


    /**
     * Redirects error messages to a text file for logging purposes.
     *
     * @throws IOException If an input or output exception occurs
     */
    public static void errorHandler() throws IOException {
        File errorFile = new File("error.txt");
        if (!Files.exists(errorFile.toPath())) {
            Files.createFile(errorFile.toPath());
        }
        FileOutputStream fos = new FileOutputStream(errorFile);
        PrintStream ps = new PrintStream(fos);
        System.setErr(ps);
    }




}









