package numberguesser;

/**
 * A JavaFX-based number guessing game implementation using MVC architecture.
 * This package contains the core components and logic for running an
 * interactive number guessing game with persistent player scores.
 *
 * <p>Key components:</p>
 * <ul>
 *   <li>{@link numberguesser.Main} - Application entry point and JavaFX initialization</li>
 *   <li>{@link numberguesser.controller.GameController} - Handles game logic and user interactions</li>
 *   <li>{@link numberguesser.model.GameModel} - Manages game state and data persistence</li>
 *   <li>{@link numberguesser.view.GameView} - Implements the UI components and scenes</li>
 *   <li>{@link numberguesser.model.Player} - Player entity with serialization support</li>
 *   <li>{@link numberguesser.model.RNG} - Random number generation utility</li>
 *   <li>{@link numberguesser.HandledException} - Custom exception handling</li>
 * </ul>
 *
 * <p>Features:</p>
 * <ul>
 *   <li>Multiple difficulty levels with varying number ranges</li>
 *   <li>Score tracking and persistence across sessions</li>
 *   <li>Interactive GUI with real-time feedback</li>
 *   <li>Custom exception handling for graceful error management</li>
 * </ul>
 *
 * <p>Usage example:</p>
 * <pre>
 * GameModel model = new GameModel();
 * GameController controller = new GameController(model);
 * GameView view = new GameView(model, controller, primaryStage);
 * </pre>
 *
 * @version 1.0
 * @see numberguesser.Main
 * @see numberguesser.controller.GameController
 * @see numberguesser.model.GameModel
 * @see numberguesser.view.GameView
 */