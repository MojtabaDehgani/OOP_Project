package GUI.controller;

import GUI.ChangesNotifier;
import GUI.util.WindowsUtils;
import game.card.Card;
import game.exceptions.MaximumCardLevelException;
import game.player.Player;
import game.player.PlayerRepository;
import game.player.PlayerService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import user.User;

import static GUI.CardPanel.tmpDir;

public class OwnedCardController {
    public static User loggedIn;
    public static ChangesNotifier notifier;
    @FXML
    private Label card_defense_attack;

    @FXML
    private Label card_duration;

    @FXML
    private ImageView card_image;

    @FXML
    private Label card_name;

    @FXML
    private Label card_player_damage;

    @FXML
    private Label card_upgrade_cost;

    @FXML
    private Label card_upgrade_level;
    private Card card;
    private Player player;

    public void setData(Card card){
        if (card.imagePath != null && !card.imagePath.isEmpty()) {
            card_image.setImage(new Image("file:"+tmpDir + card.imagePath));
        } else {
            card_image.setImage(new Image("file:"+tmpDir+"default.png"));
        }
        player=  PlayerService.getByUserId(loggedIn.id);

        card_name.setText(card.name);
        card_duration.setText(card.duration+"");
        card_defense_attack.setText(card.defenceAttack+"");
        card_player_damage.setText(card.playerDamage+"");
        card_upgrade_cost.setText(card.upgradeCost+"");
        card_upgrade_level.setText(        PlayerRepository.getCardLevel(player.id,card.id)+"");

        this.card=card;
    }

    public void upgradeclick(MouseEvent mouseEvent) {

        System.out.println("upgrading a card level");
        try {
            if(!(player.XP>=card.upgradeCost)){
                WindowsUtils.createAlertDialog("Error","Upgrading","Your XP is Low");
                return;
            }
            PlayerRepository.increaseCardLevel(player.id, card.id);
            notifier.notifyChanges(true);
            card_upgrade_level.setText(PlayerRepository.getCardLevel(player.id, card.id) + "");
        } catch (MaximumCardLevelException maximumCardLevelException){
            Button source = (Button) mouseEvent.getSource();
            source.setText("Max");
            source.setDisable(true);
        }
    }
}
