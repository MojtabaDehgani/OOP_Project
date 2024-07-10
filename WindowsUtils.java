package GUI.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class WindowsUtils {
    private static final String STATIC_PATH = "/GUI/fxml/";

    public static final String OWNED_CARD_PATH =STATIC_PATH+"owned-card.fxml" ;

    public static final String IN_GAME_CARD_PATH =STATIC_PATH+"ingame-card.fxml" ;
    public static final String CARD_PATH = STATIC_PATH+"card.fxml";

    public static final String DUO_GAME_WINDOW_PATH = STATIC_PATH+"duo-game.fxml";
    public static final String CAPTCHA_WINDOW_PATH = STATIC_PATH+"captcha.fxml";
    public static final String LOGIN_WINDOW_PATH = STATIC_PATH+"login.fxml";
    public static final String SIGNUP_WINDOW_PATH = STATIC_PATH+"signup.fxml";
    public static final String SHOP_WINDOW_PATH = STATIC_PATH+"shop.fxml";
    public static final String USER_MENU_WINDOW_PATH = STATIC_PATH+"usermenu.fxml";
    public static final String HISTORY_WINDOW_PATH = STATIC_PATH+"history.fxml";
    public static boolean switchWindow(Node n, String to) {
        Stage stage = (Stage) n.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(WindowsUtils.class.getResource(to));
        Parent r = null;
        try {
            r = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setScene(new Scene(r));
        return true;
    }
    public static void createAlertDialog(String title,String header, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.initModality(Modality.WINDOW_MODAL);
        alert.showAndWait();
    }

}
