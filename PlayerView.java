package GUI;

import game.player.Player;
import game.player.PlayerService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import user.User;
import user.UserService;

import java.util.LinkedList;
import java.util.List;

public class PlayerView {

    private ListView<String> listView;

    public PlayerView() {
        listView = new ListView<>();
        loadData();
    }

    public VBox getView() {
        VBox vbox = new VBox();
Button back= new Button("Back");
back.setOnMouseClicked(event -> {
            new AdminPanel().start((Stage) ((Button)(event.getSource())).getScene().getWindow());
        });
        vbox.getChildren().addAll(listView,back);

        return vbox;
    }

    private void loadData() {
        List<String> players = new LinkedList<>();
        for(User u:UserService.getAll()){

                Player p = PlayerService.getPlayerWithotCardsByUserId(u.id);
                if (p != null) {
                    String t = p.toString() + ",user name" + u.username + ",Nick name:" + u.nickname;
               players.add(t);
                }

        }

        ObservableList<String> playerList = FXCollections.observableArrayList(players);
        listView.setItems(playerList);
    }
}
