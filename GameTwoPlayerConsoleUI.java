package main.consoleUI;

import console.Console;
import console.menu.MenuManager;
import console.parser.ParsedString;
import console.parser.Parser;
import game.Block;
import game.GamePlayer;
import game.GameTwoPlayer;
import game.card.Card;
import game.player.Player;
import game.player.PlayerService;
import general.General;
import main.Menus;
import user.User;
import user.UserService;

import java.util.List;
import java.util.Objects;

public class GameTwoPlayerConsoleUI {

    private static GameTwoPlayer game;

    public static void start() {
        System.out.println("Login second user:");
        User secondUser = UserConsoleUI.checkPassword();
        if (secondUser == null) return;

        Player firstPlayer = PlayerService.getByUserId(Objects.requireNonNull(UserService.getCurrentUser()).id);
        Player secondPlayer = PlayerService.getByUserId(secondUser.id);

        game = new GameTwoPlayer(firstPlayer, secondPlayer);
        print();

        MenuManager.menu = Menus.GamePlay;
    }

    public static void print() {
        System.out.printf("\n%-70s %-30s %s\n\n", game.getPlayerOne().toString(), "", game.getPlayerTwo().toString());

        List<Card> cardsOne = game.getPlayerOne().currentCards;
        List<Card> cardsTwo = game.getPlayerTwo().currentCards;

        List<Block> blocksOne = game.getBlocksPlayerOne();
        List<Block> blocksTwo = game.getBlocksPlayerTwo();

        int count = game.blocksLength;

        for (int i = 0; i < count; i++) {
            String cardOne = i >= cardsOne.size() ? "" : cardsOne.get(i).toStringGame();
            String cardTwo = i >= cardsTwo.size() ? "" : cardsTwo.get(i).toStringGame();

            System.out.printf("%-70s %-30s %s\n", cardOne, blocksOne.get(i).toString() + " === " + blocksTwo.get(i).toString(), cardTwo);
        }

        System.out.printf("\nGame round: %d\n", game.currentRound + 1);
        System.out.printf("Game turn: %s\n", game.getCurrentPlayer().player.user.nickname);
    }

    public static void playCard() {
        System.out.println("place card -n <card-number> -i <block>");
        General.copyToClipboard("place card -n 1 -i 1");
        String inputString = Console.scanner.nextLine();

        ParsedString parsedString = Parser.Parse(inputString);

        if (!parsedString.getFunction().equals("place card")) {
            System.out.println("Wrong function!");
            return;
        }

        int cardNumber = Integer.parseInt(parsedString.getArgument("n"));
        int blockIndex = Integer.parseInt(parsedString.getArgument("i"));

        game.playCard(cardNumber, blockIndex);
        print();
    }

    public static void ShowCard() {
        System.out.println("select card -n <card-number> -p <player-number>");
        General.copyToClipboard("select card -n 1 -p 1");
        String inputString = Console.scanner.nextLine();

        ParsedString parsedString = Parser.Parse(inputString);

        if (!parsedString.getFunction().equals("select card")) {
            System.out.println("Wrong function!");
            return;
        }

        int cardNumber = Integer.parseInt(parsedString.getArgument("n"));
        int playerNumber = Integer.parseInt(parsedString.getArgument("p"));

        GamePlayer player = null;
        if (playerNumber == 1) player = game.getPlayerOne();
        else if (playerNumber == 2) player = game.getPlayerTwo();

        Card card = Objects.requireNonNull(player).currentCards.get(cardNumber - 1);
        System.out.println(card);
    }

}
