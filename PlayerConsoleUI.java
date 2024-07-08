package main.consoleUI;

import game.player.Player;
import game.player.PlayerService;

import java.util.List;

public class PlayerConsoleUI {

    public static void listPlayers() {
        List<Player> players = PlayerService.getAll();
        for (Player player : players)
            System.out.println(player);
    }

}
