package main.consoleUI;

import console.Console;
import console.parser.ParsedString;
import console.parser.Parser;
import game.card.Card;
import game.card.CardService;
import general.General;
import user.UserService;

import java.util.List;
import java.util.Objects;

public class CardConsoleUI {

    public static void listCards() {
        List<Card> cards = CardService.getAll();
        for (Card card : cards)
            System.out.println(card);
    }

    public static void listUserCards() {
        List<Card> cards = CardService.getUserCards(Objects.requireNonNull(UserService.getCurrentUser()).id);
        for (Card card : cards)
            System.out.println(card);
    }

    public static void create() {
        System.out.println("create card -name <card-name> -acc <accuracy> -dur <duration> -dmg <damage>");
        General.copyToClipboard("create card -name card1 -acc 38 -dur 4 -dmg 16");
        String inputString = Console.scanner.nextLine();

        ParsedString parsedString = Parser.Parse(inputString);

        if (!parsedString.getFunction().equals("create card")) {
            System.out.println("Wrong function!");
            return;
        }

        String name = parsedString.getArgument("name");
        int accuracy = Integer.parseInt(parsedString.getArgument("acc"));
        int duration = Integer.parseInt(parsedString.getArgument("dur"));
        int damage = Integer.parseInt(parsedString.getArgument("dmg"));

        Card card = new Card(name, false, accuracy, duration, damage, 1, 1, 10);
        CardService.create(card);
    }

    public static void update() {
        System.out.println("edit card -id <id> -name <card-name> -acc <accuracy> -dur <duration> -dmg <damage>");
        General.copyToClipboard("edit card -id 1 -name card1 -acc 38 -dur 4 -dmg 16");
        String inputString = Console.scanner.nextLine();

        ParsedString parsedString = Parser.Parse(inputString);

        if (!parsedString.getFunction().equals("edit card")) {
            System.out.println("Wrong function!");
            return;
        }

        int id = Integer.parseInt(parsedString.getArgument("id"));
        String name = parsedString.getArgument("name");
        int accuracy = Integer.parseInt(parsedString.getArgument("acc"));
        int duration = Integer.parseInt(parsedString.getArgument("dur"));
        int damage = Integer.parseInt(parsedString.getArgument("dmg"));

        Card card = CardService.get(id);
        card.name = name;
        card.defenceAttack = accuracy;
        card.duration = duration;
        card.playerDamage = damage;

        CardService.update(card);
    }

    public static void delete() {
        System.out.println("delete card -id <id>");
        General.copyToClipboard("delete card -id 1");
        String inputString = Console.scanner.nextLine();

        ParsedString parsedString = Parser.Parse(inputString);

        if (!parsedString.getFunction().equals("delete card")) {
            System.out.println("Wrong function!");
            return;
        }

        int id = Integer.parseInt(parsedString.getArgument("id"));

        Card card = CardService.get(id);
        CardService.delete(card);
    }

}
