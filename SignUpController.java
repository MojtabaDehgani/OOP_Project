package GUI.controller;

import GUI.AdminPanel;
import GUI.util.WindowsUtils;
import game.card.Card;
import game.card.CardService;
import game.player.Player;
import game.player.PlayerRepository;
import game.player.PlayerService;
import general.General;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import user.User;
import user.UserRepository;
import user.UserService;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import static GUI.util.Constants.MIN_CARD_PER_PLAYER;
import static GUI.util.Constants.MIN_COIN_PER_PLAYER;

public class SignUpController implements Initializable {
    @FXML
    private Label status_label;
    @FXML
    private TextField email_field;

    @FXML
    private TextField nickname_field;

    @FXML
    private TextField password_field;

    @FXML
    private TextField retype_password_field;

    @FXML
    private TextField sec_question_ans;

    @FXML
    private ComboBox<String> sec_question_box;

    @FXML
    private TextField username_field;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sec_question_box.getItems().addAll("What is your father name ?",
                "What is your favourite color ?",
                "What was the name of your first pet?");
        sec_question_box.getSelectionModel().selectFirst();
    }

    @FXML
    void randomPassword(MouseEvent event) {
        String randomPass = General.generateRandomString(8);
        password_field.setText(randomPass);
        retype_password_field.setText(randomPass);
    }

    @FXML
    void signup(MouseEvent event) throws IOException {
        if(!password_field.getText().equals(retype_password_field.getText())){
            status_label.setText("Passwords do not match");
            return;
        }
        if(isFieldEmpty(username_field)
                ||isFieldEmpty(email_field)
                ||isFieldEmpty(password_field)
                ||isFieldEmpty(retype_password_field)
                ||isFieldEmpty(sec_question_ans)
                ||isFieldEmpty(nickname_field) ) {
            status_label.setText("Some Fields are Empty!");
        return;}

        User user = new User();
        user.username = username_field.getText();
        user.password = password_field.getText();
        user.recoveryQuestion = sec_question_box.getSelectionModel().getSelectedItem();
        user.answer = sec_question_ans.getText();
        user.nickname = nickname_field.getText();
        user.email = email_field.getText();
        if(validateUserInputs(user)){
            if(checkCaptcha()) {
                if(UserService.getByUsername(user.username)!=null){
                    WindowsUtils.createAlertDialog("Signup","","You already have an account");
                    return;
                }
                UserRepository.insert(user);
            createPlayer(user);
          login(user.username,user.password);
                status_label.setTextFill(Color.GREEN);
                status_label.setText("User added successfully!");
            }else{
                status_label.setText("Captcha Failed. Please Try Again");
            }
        }
    }

    private void login(String u, String p) {
    WindowsUtils.createAlertDialog("Welcome","","Congratulations! You received 3 cards as a gift!");

        if (u.isBlank() || u.isEmpty() || p.isBlank() || p.isEmpty()) {
            status_label.setText("Username or Password is Empty");
            return;
        }

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

    private void createPlayer(User user) {

        int id= UserService.getByUsername(user.username).id;
        Player player = new Player();
        player.level=1;
        player.maxHP=100;
        player.XP=1;
        player.coins= MIN_COIN_PER_PLAYER;
        player.userId=id;
        List<Card> all =CardService.getAll();
        Collections.shuffle(all);

        PlayerRepository.insert(player);
      player= PlayerService.getByUserId(UserService.getByUsername(user.username).id);
      for(Card c:all.subList(0,Math.min(all.size()-1, MIN_CARD_PER_PLAYER))) {
          PlayerRepository.addCard(player, c);
      }

    }

    public boolean checkCaptcha() throws IOException {
        FXMLLoader loader = new FXMLLoader(SignUpController.class.getResource(WindowsUtils.CAPTCHA_WINDOW_PATH));
        Parent p = loader.load();
        CaptchaController controller = loader.getController();
        Scene scene = new Scene(p);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        return controller.isVerified();
    }

    @FXML
    void back(MouseEvent event) {
        WindowsUtils.switchWindow(password_field,WindowsUtils.LOGIN_WINDOW_PATH);
    }
    private boolean isFieldEmpty(TextField field) {
        return field.getText().isBlank() || field.getText().isEmpty();
    }
    boolean validateUserInputs(User user){
        if (user.password == null || user.password.length() < 4) {
            status_label.setText("The length of the password must be at least 4 characters.");
            return false;
        }

        if (user.email == null || user.email.equals("")) {
            status_label.setText("The email cannot be empty.");
            return false;
        }

        if (user.nickname == null || user.nickname.length() < 4) {
            status_label.setText("The length of the nickname must be at least 4 characters.");
            return false;
        }

        if (!user.password.matches("\\w+")) {
            status_label.setText("Password can only contain letters and numbers and underline.");
            return false;
        }

        if (!user.email.matches("^[\\w!#$%&'*+/=?^`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^`{|}~-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$")) {
            status_label.setText("The email address is incorrect.");
            return false;
        }


        return true;
    }
}
