package GUI.controller;

import GUI.AdminPanel;
import GUI.CardPanel;
import GUI.util.WindowsUtils;
import game.player.PlayerService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import user.User;
import user.UserService;

public class LoginController {
    @FXML
    private Label status_label;
    @FXML
    private TextField password_field;

    @FXML
    private TextField username_field;

    @FXML
    void forgetPass(MouseEvent event) {

     User u=  UserService.getByUsername( username_field.getText());
       if(u==null){
           WindowsUtils.createAlertDialog("Error","","Username not found");
           return;
       }
        TextInputDialog textInputDialog = new TextInputDialog();
       textInputDialog.setContentText(u.recoveryQuestion);
       String q= textInputDialog.showAndWait().get();
       if(q!=null && q.trim().equalsIgnoreCase(u.answer.trim())){
           WindowsUtils.createAlertDialog("Password","Here is your password",u.password);

       }
    }

    @FXML
    void login(MouseEvent event) {
        String u = username_field.getText();
        String p = password_field.getText();

        if (u.isBlank() || u.isEmpty() || p.isBlank() || p.isEmpty()) {
            status_label.setText("Username or Password is Empty");
            return;
        }
        if (u.equalsIgnoreCase("admin") && p.equalsIgnoreCase("admin")) {
Button btn= (Button) event.getSource();
Stage st= (Stage) btn.getScene().getWindow();
new AdminPanel().start(st);
        } else {
            UserService.login(u, p);
            User loggedIn = UserService.getCurrentUser();

            if (loggedIn != null) {
                UserMenuController.loggedIn = loggedIn;
                CardController.loggedIn=loggedIn;
                OwnedCardController.loggedIn=loggedIn;
                WindowsUtils.switchWindow(password_field, WindowsUtils.USER_MENU_WINDOW_PATH);

            } else {
                status_label.setText("Username or Password is incorrect");
            }
        }
    }

    @FXML
    void signup(MouseEvent event) {
            WindowsUtils.switchWindow(password_field,"/GUI/fxml/signup.fxml");
    }

}
