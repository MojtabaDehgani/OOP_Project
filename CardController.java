package GUI.controller;

import GUI.ChangesNotifier;
import GUI.util.WindowsUtils;
import game.card.Card;
import game.card.CardService;
import game.exceptions.MaximumCardLevelException;
import game.player.Player;
import game.player.PlayerRepository;
import game.player.PlayerService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import user.User;

import static GUI.CardPanel.tmpDir;

public class CardController {
   private Player player=null;
    public static User loggedIn;
    public static ShopController notifier;
    private Card card;

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
card_upgrade_level.setText(card.upgradeLevel+"");
        this.card = card;
    }

    public void buyCard(MouseEvent mouseEvent) {
        //System.out.println("Clicked");
        Button button = (Button) mouseEvent.getSource();

        if (button.getText().contains("Upgrade")) {
            upgrade(mouseEvent, card, player);
        } else {
            if (card.price <= player.coins) {

                player.coins -= card.price;
                PlayerRepository.update(player);
                CardService.buyCard(player.id, card.id);
                button.setText("Upgrade");
                notifier.notifyChanges(true);
                // button.setDisable(true);

            } else {
                WindowsUtils.createAlertDialog("Buy","","Not enough money");
            }
        }
    }
    public void upgrade(MouseEvent mouseEvent, Card card, Player player) {
      //  System.out.println("upgrading a card level");
        try {
            PlayerRepository.increaseCardLevel(player.id, card.id);

            notifier.notifyChanges(true);
            card_upgrade_level.setText(PlayerRepository.getCardLevel(player.id, card.id) + "");

        }    catch (
    MaximumCardLevelException maximumCardLevelException){
        Button source = (Button) mouseEvent.getSource();
        source.setText("Max");
        source.setDisable(true);
    }
    }
}
