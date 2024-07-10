package GUI.controller;

import GUI.BlockGUI;
import GUI.util.Constants;
import GUI.util.WindowsUtils;
import game.card.Card;
import game.card.CardService;
import game.history.History;
import game.history.HistoryService;
import game.player.Player;
import game.player.PlayerService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import static GUI.util.Constants.MIN_CARD_PER_PLAYER;

public class DuoGameController implements Initializable {

    public static int betCoins=0;
    @FXML private VBox parent;
    @FXML private GridPane first_player_ground;

    @FXML private VBox first_player_info;
    @FXML private VBox second_player_info;

    @FXML private HBox first_player_cards;
    @FXML private HBox second_player_cards;

    @FXML private GridPane second_player_ground;


    @FXML private Label turn_label;

    @FXML private TextField second_player_card_Number;
    @FXML private TextField second_player_column_Number;

    @FXML private TextField first_player_card_number;
    @FXML private TextField first_player_column_number;

    @FXML  private Button firstPlayerPutButton;
    @FXML private Button secondPlayerPutButton;

    @FXML private Label first_player_name;
    @FXML private Label second_player_name;

    @FXML private Label first_player_hp;
    @FXML private Label second_player_hp;

    @FXML private Label round_label;

    public static Player firstPlayer;
    public static Player secondPlayer;
    private int firstPlayerHP;
    private int secondPlayerHP;


    private List<Card> firstPlayerCards;
    private List<Card> secondPlayerCards;
    private int turn = 0;//0: First Player | 1: Second Player


    ObservableList<BlockGUI> firstPlayerBlocks = FXCollections.observableArrayList();
    ObservableList<BlockGUI> secondPlayerBlocks = FXCollections.observableArrayList();



    private int gameTurns   =4; //GameTurns % 2 should be 0
    private int currentTurn =0;

    private int currentRound = 1;
    private int gameRounds = 4;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Give Player Cards to them
        firstPlayerCards = CardService.getUserCards(firstPlayer.userId);
        secondPlayerCards = CardService.getUserCards(secondPlayer.userId);
        Collections.shuffle(firstPlayerCards);
        Collections.shuffle(secondPlayerCards);


        firstPlayerHP = firstPlayer.maxHP;
        secondPlayerHP = secondPlayer.maxHP;
        first_player_name.setText(firstPlayer.user.nickname);
        second_player_name.setText(secondPlayer.user.nickname);

        try {
            initGameBoard();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        round_label.setText(currentRound+"/"+gameRounds);
        this.turn = (int) (Math.random()*2);
        if(turn==0){
            turn_label.setText(firstPlayer.user.nickname);
            WindowsUtils.createAlertDialog("Turn","Who's Turn?","It's "+ firstPlayer.user.nickname+" Turn!");
            secondPlayerSetDisabled(true);
        }else{
            turn_label.setText(secondPlayer.user.nickname);
            WindowsUtils.createAlertDialog("Turn","Who's Turn?","It's "+ secondPlayer.user.nickname+" Turn!");
            firstPlayerSetDisabled(true);
        }
    }

    private void initGameBoard() throws IOException {

        first_player_ground.getChildren().clear();
        second_player_ground.getChildren().clear();
        first_player_cards.getChildren().clear();
        second_player_cards.getChildren().clear();

        firstPlayerBlocks.clear();
        secondPlayerBlocks.clear();

        for (int i = 0; i < 21; i++) {
            BlockGUI blockGUI1 = new BlockGUI();
            BlockGUI blockGUI2 = new BlockGUI();

            firstPlayerBlocks.add(blockGUI1);
            first_player_ground.add(blockGUI1,i,0);

            secondPlayerBlocks.add(blockGUI2);
            second_player_ground.add(blockGUI2,i,0);
        }


        first_player_hp.setText(firstPlayerHP+"");
        second_player_hp.setText(secondPlayerHP+"");


        //make two fields Destroyed in both side!
        int destroyed1Index = (int)(Math.random()*21);
        int destroyed2Index = (int)(Math.random()*21);
        firstPlayerBlocks.get(destroyed1Index).destroy();
        secondPlayerBlocks.get(destroyed2Index).destroy();

        Collections.shuffle(firstPlayerCards);
        Collections.shuffle(secondPlayerCards);
        initCards();

    }



    private void initCards() throws IOException {
        first_player_cards.getChildren().clear();
        second_player_cards.getChildren().clear();
        for (int i = 0; i < Constants.MIN_CARD_PER_PLAYER; i++) {
            addPlayerCard(firstPlayerCards.get(i),first_player_cards);
            addPlayerCard(secondPlayerCards.get(i),second_player_cards);
        }


    }
    private void addPlayerCard(Card card, HBox cardHolder) throws IOException {
        FXMLLoader loader = new FXMLLoader(ShopController.class.getResource(WindowsUtils.IN_GAME_CARD_PATH));
        VBox vBox = loader.load();
        InGameCardController controller = loader.getController();
        controller.setData(card);
        cardHolder.getChildren().add(vBox);
    }

    public void secondPlayerPutButton(MouseEvent mouseEvent) throws IOException {
        int cardNumber = Integer.parseInt(second_player_card_Number.getText()) - 1;
        int columnIndex = Integer.parseInt(second_player_column_Number.getText()) - 1;
        action(cardNumber, columnIndex,false);
    }
    @FXML
    void firstPlayerPutButton(MouseEvent event) throws IOException {
        int cardNumber = Integer.parseInt(first_player_card_number.getText()) - 1;
        int columnIndex = Integer.parseInt(first_player_column_number.getText()) - 1;
        action(cardNumber, columnIndex,true);
    }

    private void action(int cardNumber, int columnIndex,boolean firstPlayer) throws IOException {
        Card selectedCard;
        if(firstPlayer){
            selectedCard = firstPlayerCards.get(cardNumber);
            boolean result = validateColumnsByDuration(selectedCard, columnIndex,firstPlayerBlocks,secondPlayerBlocks);

            if(result){
                firstPlayerCards.remove(cardNumber);
                firstPlayerCards.add(selectedCard);
                initCards();
                addPlayerCard(firstPlayerCards.get((int) (Math.random()*firstPlayerCards.size())),first_player_cards);
                changeTurn();
            }else{
                System.out.println("Invalid Move\tTry Again");
                return;
            }
        }else{
            selectedCard = secondPlayerCards.get(cardNumber);
            boolean result = validateColumnsByDuration(selectedCard,columnIndex,secondPlayerBlocks,firstPlayerBlocks);
            if(result){

                secondPlayerCards.remove(cardNumber);
                secondPlayerCards.add(selectedCard);
                initCards();
                addPlayerCard(secondPlayerCards.get((int) (Math.random()*secondPlayerCards.size())),second_player_cards);
                changeTurn();

            }else{
                System.out.println("Invalid Move\tTry Again");
                return;
            }
        }
        checkForTimeline();
    }

    private void checkForTimeline() throws IOException {
        currentTurn++;
        if(currentTurn==gameTurns) {
            currentTurn=0;
            Player winner = null;
            Player loser = null;
            for (int i = 0; i < 21; i++) {
                firstPlayerHP -= secondPlayerBlocks.get(i).block.dmg;
                if (firstPlayerHP <= 0) {
                    firstPlayerHP = 0;
                    winner = secondPlayer;

                    loser = firstPlayer;


                    break;
                }
                secondPlayerHP -= firstPlayerBlocks.get(i).block.dmg;
                if (secondPlayerHP <= 0) {
                    secondPlayerHP = 0;
                    winner = firstPlayer;
                    loser = secondPlayer;
                    break;
                }
            }
            first_player_hp.setText(firstPlayerHP+"");
            second_player_hp.setText(secondPlayerHP+"");
            if (winner != null) {
                winner.coins+=betCoins*2;

                WindowsUtils.createAlertDialog("RESULT","Game Result",winner.user.nickname+" WON!");
                winner.XP += 10;
                winner.level = 1 + winner.XP % 100;
                if(betCoins==0) {
                    winner.coins += 10;
                }
                PlayerService.update(winner);
                if(betCoins>0) {
                    loser.coins -= betCoins;
                    WindowsUtils.createAlertDialog("RESULT","Bet",winner.user.nickname+" received "+betCoins*2+" coins");

                    PlayerService.update(loser);
                }
                History history;
                history = new History(winner.id, loser.id, "Win", 10);
                HistoryService.create(history);
                history = new History(loser.id, winner.id, "Lost", -10);
                HistoryService.create(history);

                WindowsUtils.switchWindow(parent,WindowsUtils.USER_MENU_WINDOW_PATH);
                return;
            }

            if (currentRound == gameRounds) {
                WindowsUtils.createAlertDialog("RESULT","Game Result","DRAW");
                History history;
                history = new History(firstPlayer.id, secondPlayer.id, "Draw", 0);
                HistoryService.create(history);
                history = new History(firstPlayer.id, secondPlayer.id, "Draw", 0);
                HistoryService.create(history);
                WindowsUtils.switchWindow(parent,WindowsUtils.USER_MENU_WINDOW_PATH);

            } else {
                currentRound++;
                round_label.setText(currentRound + "/" + gameRounds);
                WindowsUtils.createAlertDialog("Round","Next Round","Now It's Round "+ currentRound+"/"+gameRounds+". Let's GO!");
                initGameBoard();
            }
        }


    }

    private boolean validateColumnsByDuration(Card card, int startIndex,
                                              ObservableList<BlockGUI> blocks,
                                              ObservableList<BlockGUI> opponentBlocks) {

        int duration = card.duration;
        //Check iF the next {Duration} columns are empty
        int i = 0;
        while(i<duration){
            if(i>=blocks.size()) return false;
            if(!blocks.get(i+startIndex).isEmpty) return false;
            i++;
        }

        //Safe To Put Card in the next {Duration} columns
        i = 0;
        while(i<duration){
            blocks.get(startIndex+i).block.acc = card.defenceAttack;
            blocks.get(startIndex+i).block.dmg = card.playerDamage / card.duration;
            blocks.get(startIndex+i).fillBlock(card);
            //Compare Player and Opponent Cards By Power
            if (blocks.get(startIndex+i).block.acc <= opponentBlocks.get(startIndex+i).block.acc) blocks.get(startIndex+i).block.dmg = 0;
            if (opponentBlocks.get(startIndex+i).block.acc <= (blocks.get(startIndex+i).block.acc)) opponentBlocks.get(startIndex+i).block.dmg = 0;

            i++;
        }
        return true;
    }

    private void changeTurn(){
        if(turn==0){
            turn = 1;
            firstPlayerSetDisabled(true);
            secondPlayerSetDisabled(false);
            turn_label.setText(secondPlayer.user.nickname);
        }else{
            turn = 0;
            firstPlayerSetDisabled(false);
            secondPlayerSetDisabled(true);
            turn_label.setText(firstPlayer.user.nickname);
        }
    }
    private void firstPlayerSetDisabled(boolean isDisable){
        firstPlayerPutButton.setDisable(isDisable);
        first_player_card_number.setDisable(isDisable);
        first_player_column_number.setDisable(isDisable);
    }

    private void secondPlayerSetDisabled(boolean isDisable){
        secondPlayerPutButton.setDisable(isDisable);
        second_player_card_Number.setDisable(isDisable);
        second_player_column_Number.setDisable(isDisable);
    }

}
