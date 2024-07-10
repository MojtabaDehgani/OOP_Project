module CityWars {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires java.desktop;
    exports GUI;
    exports GUI.controller to javafx.fxml;
    opens GUI.controller to javafx.fxml;

    exports database to java.sql;
    exports general to java.desktop;

}