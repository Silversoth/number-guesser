# Number Guesser 🎯

**Number Guesser** is a desktop application built with **JavaFX** following the **MVC (Model-View-Controller)** pattern. The player must guess a randomly generated number. The game features a graphical interface, feedback on guesses, and a persistent high score system.

---

## 📸 Preview
![image](https://github.com/user-attachments/assets/60877894-fd24-4cef-9457-3010b04ce13f)
![image](https://github.com/user-attachments/assets/2c283ba5-cbb1-4b67-8af4-0124808a07bf)
![image](https://github.com/user-attachments/assets/02e0e5e5-49ea-4b36-8ee9-8f0d6a67548f)



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
👨‍💻 Author
Jonathan Hendrix
GitHub
LinkedIn

---
📄 License
This project is licensed under the MIT License.



