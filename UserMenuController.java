package GUI.controller;

import GUI.EditProfile;
import GUI.util.WindowsUtils;
import game.player.Player;
import game.player.PlayerRepository;
import game.player.PlayerService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import user.User;
import user.UserService;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class UserMenuController implements Initializable {
    private boolean bet=false;
    @FXML
    private Label coins;

    @FXML
    private Label hp;

    @FXML
    private Label level;

    @FXML
    private Label nickname;

    @FXML
    private GridPane parent;

    @FXML
    private Label xp;


    @FXML
    private RadioButton soloMode;
    @FXML
    private RadioButton duoMode;
    @FXML
    private RadioButton betMode;

    @FXML
    private VBox secondPlayerBox;

    @FXML
    private TextField second_player_password;

    @FXML
    private TextField second_player_username;
    public static User loggedIn;
    private Player player;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        player = PlayerRepository.getByUserId(loggedIn.id,false);
        nickname.setText(loggedIn.nickname);
        hp.setText(player.maxHP+"");
        xp.setText(player.XP+"");
        level.setText(player.level+"");
        coins.setText(player.coins+"");


        soloMode.setSelected(true);
        secondPlayerBox.setVisible(false);
        soloMode.selectedProperty().addListener((observable, oldValue, newValue) -> {
            bet=false;
            secondPlayerBox.setVisible(false);
        });
        duoMode.selectedProperty().addListener((observable, oldValue, newValue) -> {
            bet=false;
            secondPlayerBox.setVisible(true);
        });
        betMode.selectedProperty().addListener((observable, oldValue, newValue) -> {
          bet=true;
            secondPlayerBox.setVisible(true);
        });
    }

    @FXML
    void editProfile(MouseEvent event) {
        Button b = (Button) event.getSource();
        EditProfile.loggedIn=loggedIn;
        new EditProfile().start((Stage) b.getScene().getWindow());
    }

    @FXML
    void history(MouseEvent event) {
        HistoryController.player = player;
        WindowsUtils.switchWindow(parent,WindowsUtils.HISTORY_WINDOW_PATH);
    }

    @FXML
    void logout(MouseEvent event) {
        WindowsUtils.switchWindow(parent,WindowsUtils.LOGIN_WINDOW_PATH);
    }

    @FXML
    void shop(MouseEvent event) {
        ShopController.loggedIn = loggedIn;
        WindowsUtils.switchWindow(parent,WindowsUtils.SHOP_WINDOW_PATH);
    }

    @FXML
    void startTheGame(MouseEvent event) {
        String secondPlayerUsername = second_player_username.getText();
        String secondPlayerPassword = second_player_password.getText();
        if(secondPlayerPassword.trim().isEmpty() || secondPlayerUsername.trim().isEmpty()){
            WindowsUtils.createAlertDialog("Error","","Password and username is empty.");
            return;
        }

        User user = UserService.getByUsername(secondPlayerUsername);
        if(user==null){
            WindowsUtils.createAlertDialog("Error","","User not found!");
       return;
        }
        User fuser = UserService.getByUsername(loggedIn.username);
        Player second = PlayerService.getByUserId(user.id);
Player first=PlayerService.getByUserId(fuser.id);
        if(!user.password.equalsIgnoreCase(secondPlayerPassword.trim())){
            WindowsUtils.createAlertDialog("Error","","Wrong password!");
            return;
        }
        if(bet){
            TextInputDialog textInputDialog = new TextInputDialog("Enter coins:");
            Optional<String> c = textInputDialog.showAndWait();

            int coin=Integer.valueOf(c.get());
            if(first.coins>=coin && second.coins>=coin){
                first.coins-=coin;
                second.coins-=coin;
                PlayerService.update(first);
                PlayerService.update(second);

                DuoGameController.firstPlayer = player;
                DuoGameController.secondPlayer = second;
                DuoGameController.betCoins=coin;
                WindowsUtils.switchWindow(parent,WindowsUtils.DUO_GAME_WINDOW_PATH);

            } else {
                WindowsUtils.createAlertDialog("Error","","You don't have enough money!");
            }
        }
       else if(duoMode.isSelected()){


            DuoGameController.firstPlayer = player;
            DuoGameController.secondPlayer = second;
            WindowsUtils.switchWindow(parent,WindowsUtils.DUO_GAME_WINDOW_PATH);

        }
    }

}
