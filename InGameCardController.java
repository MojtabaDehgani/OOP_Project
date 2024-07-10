package GUI.controller;

import game.card.Card;
import game.player.PlayerService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import static GUI.CardPanel.tmpDir;

public class InGameCardController{
    @FXML
    private Label card_duration;

    @FXML
    private ImageView card_image;

    @FXML
    private Label card_name;

    @FXML
    private Label card_player_damage;

    @FXML
    private Label card_defense_attack;

    private Card card;
    public void setData(Card card){
        if (card.imagePath != null && !card.imagePath.isEmpty()) {
            card_image.setImage(new Image("file:"+tmpDir + card.imagePath));
        } else {
            card_image.setImage(new Image("file:"+tmpDir+"default.png"));
        }
        card_name.setText(card.name);
        card_duration.setText(card.duration+"");
        card_defense_attack.setText(card.defenceAttack+"");
        card_player_damage.setText(card.playerDamage+"");
        this.card = card;
    }

}
