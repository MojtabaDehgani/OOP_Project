package GUI.controller;

import GUI.ChangesNotifier;
import GUI.util.WindowsUtils;
import game.card.Card;
import game.card.CardService;
import game.player.PlayerService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import user.User;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ShopController implements Initializable, ChangesNotifier {

    public static User loggedIn;
    @FXML
    private FlowPane cards_holder;

    @FXML
    private RadioButton showAllCards;
    @FXML
    private RadioButton showLockedCards;
    @FXML
    private RadioButton showUnlockedCards;
    @FXML
    private Label coins;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateCoins();
        try {
            getAllCards();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        showAllCards.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                try {
                    cards_holder.getChildren().clear();
                    getAllCards();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        showUnlockedCards.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                try {
                    cards_holder.getChildren().clear();
                    getPlayerCards();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        showLockedCards.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                try {
                    cards_holder.getChildren().clear();
                    getLockedCards();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    public void getLockedCards() throws IOException {
        List<Card> userCards = CardService.getUserCards(loggedIn.id);
        List<Card> allCards = CardService.getAll();
        allCards.removeAll(userCards);
        cards_holder.getChildren().clear();
        for(Card card:allCards){
            addLockedCardToUI(card);
        }

    }

    public void getPlayerCards() throws IOException {
        List<Card> userCards = CardService.getUserCards(loggedIn.id);

        for(Card card:userCards){
            addUnlockedCardToUI(card);
        }
    }


    public void getAllCards() throws IOException {
        List<Card> userCards = CardService.getUserCards(loggedIn.id);
        List<Card> cards = CardService.getAll();
        for(Card card:cards){
            if(userCards.contains(card)){
                addUnlockedCardToUI(card);
            }else{
                addLockedCardToUI(card);
            }
        }
    }
    private void addLockedCardToUI(Card card) throws IOException {
        FXMLLoader loader = new FXMLLoader(ShopController.class.getResource(WindowsUtils.CARD_PATH));
        VBox vBox = loader.load();
        CardController controller = loader.getController();
        CardController.notifier = this;
        OwnedCardController.notifier = this;
        controller.setData(card);
        cards_holder.getChildren().add(vBox);
    }
    private void addUnlockedCardToUI(Card card) throws IOException {
        FXMLLoader loader = new FXMLLoader(ShopController.class.getResource(WindowsUtils.OWNED_CARD_PATH));
        VBox vBox = loader.load();
        OwnedCardController controller = loader.getController();
        controller.setData(card);
        cards_holder.getChildren().add(vBox);
    }

    @FXML
    void back(MouseEvent event) {
        WindowsUtils.switchWindow(cards_holder,WindowsUtils.USER_MENU_WINDOW_PATH);

    }

    @Override
    public void notifyChanges(boolean isChanged) {
        if(isChanged){
            updateCoins();
        }

    }



    private void updateCoins() {
        coins.setText(PlayerService.getByUserId(loggedIn.id).coins+"");


    }
}
