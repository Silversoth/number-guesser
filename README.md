# Number Guesser Game

Welcome to the Number Guesser Game! This is a simple console-based game where players attempt to guess a randomly generated number within a specified range. The game keeps track of player scores across sessions, allowing players to improve their scores over time.

## Features

- **Multiple Difficulty Levels**: Choose from three difficulty levels, each with a different range of numbers.
- **Persistent Scores**: Player scores are saved and loaded from a file, allowing scores to persist across game sessions.
- **Custom Exception Handling**: Uses a custom exception class to handle errors with additional context.
- **Serialization**: Player data is serialized to maintain state between sessions.

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- A Java IDE or command-line tools to compile and run the program

### Installation

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/yourusername/number-guesser-game.git
   cd number-guesser-game
   ```

2. **Compile the Program**:
   Navigate to the `src` directory and compile the Java files:
   ```bash
   javac numberguesser/*.java
   ```

3. **Run the Program**:
   Execute the `Main` class to start the game:
   ```bash
   java numberguesser.Main
   ```

## How to Play

1. **Start the Game**: Run the program, and you will be greeted with a welcome message.
2. **Enter Your Name**: Provide your name to start or continue your game session.
3. **Select Difficulty**: Choose a difficulty level (1 for Easy, 2 for Medium, 3 for Hard).
4. **Guess the Number**: You have three attempts to guess the correct number. Points are awarded based on the number of attempts taken:
   - 1st attempt: 100 points
   - 2nd attempt: 50 points
   - 3rd attempt: 25 points
5. **View Scores**: After each game, your scores are displayed, and you can choose to play again or exit.

## Code Structure

- **`Main.java`**: The entry point of the game. Manages game flow, player interactions, and score persistence.
- **`RNG.java`**: Handles random number generation within a specified range.
- **`Player.java`**: Represents a player and manages player data, including scores and attempts.
- **`HandledException.java`**: Custom exception class for handling errors with additional context.

## Error Handling

- Errors are logged to an `error.txt` file for debugging purposes.
- Custom exceptions provide detailed error messages and codes.

## Contributing

Contributions are welcome! Please fork the repository and submit a pull request for any enhancements or bug fixes.

## License

This project is licensed under the MIT License. See the `LICENSE` file for more details.

## Contact

For questions or feedback, please contact [soth26@gmail.com].


