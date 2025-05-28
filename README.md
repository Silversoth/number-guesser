# Number Guesser 🎯

**Number Guesser** is a desktop application built with **JavaFX** following the **MVC (Model-View-Controller)** pattern. The player must guess a randomly generated number. The game features a graphical interface, feedback on guesses, and a persistent high score system.

---

## 📸 Preview
![Start screen](./Assets/Pic1.png)
![In game](./Assets/Pic2.png)
![Result](./Assets/Pic3.png)

---
## ✨ Features

- Interactive number guessing game
- User-friendly graphical interface with JavaFX
- Persistent high score saved locally
- Modular MVC structure for clean architecture

---

## 🧱 Project Structure
The structure follows the MVC pattern.
```
number-guesser/
├── src/
│ └── main/
│ └── java/
│ └── numberguesser/
│ ├── Main.java
│ ├── controller/
│ │ └── GameController.java
│ ├── model/
│ │ ├── GameModel.java
│ │ ├── Player.java
│ │ └── RNG.java
│ └── view/
│ └── GameView.java
├── module-info.java
├── scoreboard.dat

```




- `Main.java`: Application entry point.
- `model/`: Game logic (`RNG`, `Player`, `GameModel`).
- `controller/`: Controls interactions between view and model.
- `view/`: Builds the graphical interface using JavaFX.
- `scoreboard.dat`: File that stores the highest score persistently.
- `module-info.java`: Java module definition.

---

## 🛠 Technologies

- Java 17
- JavaFX
- MVC Architecture
- Javadoc documentation

---

## ▶ How to Run

### 🔧 Requirements

- Java 17 or higher
- [JavaFX SDK](https://gluonhq.com/products/javafx/) (version compatible with your JDK)
- Apache Maven 3.6+

---

### 🚀 Running the Project

#### 1. Set the JavaFX SDK path as an environment variable:

```bash
export PATH_TO_FX=/path/to/javafx-sdk-21/lib
```
#### 2. Run the application with Maven:
```bash
mvn clean javafx:run -f pom.xml
```

---

## 👨‍💻 Author

**Jonathan Hendrix**  
[GitHub](https://github.com/Silversoth)  
[LinkedIn](https://www.linkedin.com/in/tu-linkedin) <!-- Actualiza con tu URL real -->

---

## 📄 License

This project is licensed under the [MIT License](https://opensource.org/licenses/MIT).




