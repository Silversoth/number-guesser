# Number Guesser

A JavaFX-based number guessing game with persistent player scores and multiple difficulty levels.

## Prerequisites

- Java JDK 21
- Maven
- JavaFX 21

## Building

```bash
mvn clean install
```

## Running
```
mvn javafx:run
```

## Project Structure
```
src/main/java/
└── numberguesser/
    ├── controller/
    │   └── GameController.java
    ├── model/
    │   ├── GameModel.java
    │   ├── Player.java
    │   └── RNG.java
    ├── view/
    │   └── GameView.java
    ├── Main.java
    └── HandledException.java
```
## Game Features

### Difficulty Levels
* Easy (Level 1): Numbers 1-10
* Medium (Level 2): Numbers 1-50
* Hard (Level 3): Numbers 1-100

More points awarded in each difficulty the fewer tries needed.

### Gameplay
* Three attempts per game
* Real-time feedback on guesses
* Score tracking and persistence
* Player statistics by difficulty level

## Technical Implementation
### Architecture
* MVC design pattern
* JavaFX UI framework
* Custom exception handling with error logging
* Serialization for data persistence
### Core components
* Main: Application entry point and JavaFX initialization
* GameController: Game logic and input handling
* GameModel: State management and data persistence
* GameView: UI components and scene management
* Player: Serializable player entity
* HandledException: Custom exception handling

## Dependencies
```
<dependencies>
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-controls</artifactId>
        <version>${javafx.version}</version>
    </dependency>
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-fxml</artifactId>
        <version>${javafx.version}</version>
    </dependency>
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter-engine</artifactId>
        <version>5.8.2</version>
    </dependency>
    <dependency>
        <groupId>org.apache.commons</groupId>
        <artifactId>commons-lang3</artifactId>
        <version>3.17.0</version>
    </dependency>
</dependencies>
```
## Testing
```
mvn test
```

## Distribution
The project is configured for GitHub deployment:
```
<distributionManagement>
    <repository>
        <id>github</id>
        <name>Number-Guesser-Release-Repository</name>
        <url>https://github.com/Silversoth/number-guesser</url>
    </repository>
</distributionManagement>
```
## Required Modules
* javafx.graphics
* javafx.controls
* javafx.fxml
* java.desktop
* org.apache.commons.lang3
* org.junit.jupiter.api

## Error Handling
Errors are logged to error.txt through the HandledException system

## License

This project is maintained by Jonathan Hendrix