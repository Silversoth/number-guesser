module number.guesser {
    requires javafx.graphics;
    requires org.apache.commons.lang3;
    requires org.junit.jupiter.api;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    exports numberguesser.view;
    exports numberguesser.controller;
    exports numberguesser to javafx.graphics;
    exports numberguesser.model to javafx.graphics;
}