package GUI.controller;

import GUI.util.WindowsUtils;
import game.history.History;
import game.history.HistoryService;
import game.player.Player;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class HistoryController implements Initializable {
    public static Player player;
    @FXML  private ListView<History> histroy_listView;
    @FXML private BorderPane parent;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<History> historyList = HistoryService.getAll();
        historyList.stream()
                .filter(e-> Objects.equals(e.playerId, player.id))
                .toList()
                .forEach(e->{histroy_listView.getItems().add(e);});
    }

    @FXML
    public void back(MouseEvent mouseEvent){
        WindowsUtils.switchWindow(parent,WindowsUtils.USER_MENU_WINDOW_PATH);
    }

}
