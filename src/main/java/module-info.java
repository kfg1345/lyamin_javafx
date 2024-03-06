module com.example.lab3javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml.bind;
    requires java.prefs;

    opens com.example.lab3javafx.controllers to javafx.fxml;
    opens com.example.lab3javafx.model to java.xml.bind;
    opens com.example.lab3javafx to javafx.fxml;

    exports com.example.lab3javafx;
    exports com.example.lab3javafx.controllers;
    exports com.example.lab3javafx.util;

}