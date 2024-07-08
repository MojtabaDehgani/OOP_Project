package game;

import game.card.Card;
import game.history.History;
import game.history.HistoryService;
import game.player.Player;
import game.player.PlayerService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameTwoPlayer {
    static Random random = new Random();

    private final GamePlayer playerOne, playerTwo;
    private List<Block> blocksPlayerOne, blocksPlayerTwo;

    public final int blocksLength = 21;
    public final int gameTurns = 4;
    public final int gameRounds = 2;

    private GamePlayer currentPlayer;
    public int currentTurn = 0;
    public int currentRound = 0;
    public boolean gameOver = false;

    public GameTwoPlayer(Player player1, Player player2) {
        this.playerOne = new GamePlayer(player1);
        this.playerTwo = new GamePlayer(player2);

        initialize();
    }

    private void initialize() {
        blocksPlayerOne = new ArrayList<>();
        blocksPlayerTwo = new ArrayList<>();

        for (int i = 0; i < blocksLength; i++) {
            blocksPlayerOne.add(new Block());
            blocksPlayerTwo.add(new Block());
        }

        blocksPlayerOne.get(random.nextInt(blocksLength)).isDestroyed = true;
        blocksPlayerTwo.get(random.nextInt(blocksLength)).isDestroyed = true;

        int n = random.nextInt(2);
        if (n == 0) currentPlayer = playerOne;
        else currentPlayer = playerTwo;
    }

    public void playCard(int cardNumber, int blockIndex) {
        if (gameOver) {
            System.out.println("game over!");
            return;
        }

        GamePlayer player, opponent;
        List<Block> playerBlocks, opponentBlocks;

        player = currentPlayer;

        if (currentPlayer == playerOne) {
            opponent = playerTwo;
            playerBlocks = blocksPlayerOne;
            opponentBlocks = blocksPlayerTwo;

        } else {
            opponent = playerOne;
            playerBlocks = blocksPlayerTwo;
            opponentBlocks = blocksPlayerOne;
        }

        try {
            Card card = player.currentCards.get(cardNumber - 1);
            playCard(card, blockIndex - 1, player, opponent, playerBlocks, opponentBlocks);

            currentTurn++;
            if (currentTurn == gameTurns * 2) runTimeLine();

            // change game turn
            currentPlayer = opponent;

            // remove played card and add a new random card
            player.currentCards.set(cardNumber - 1, player.player.cards.get(random.nextInt(player.player.cards.size())));

        } catch (Exception e) {
            System.out.println("Wrong inputs!");
        }
    }

    private void playCard(Card card, int blockIndex, GamePlayer player, GamePlayer opponent, List<Block> playerBlocks, List<Block> opponentBlocks) throws Exception {
        for (int i = 0; i < card.duration; i++) {
            Block block = playerBlocks.get(blockIndex + i);
            if (!block.isEmpty || block.isDestroyed) {
                throw new Exception("Block is destroyed or is not empty.");
            }
        }

        for (int i = 0; i < card.duration; i++) {
            Block playerBlock = playerBlocks.get(blockIndex + i);
            Block opponentBlock = opponentBlocks.get(blockIndex + i);

            playerBlock.isEmpty = false;
            playerBlock.acc = card.defenceAttack;
            playerBlock.dmg = card.playerDamage / card.duration;

            if (playerBlock.acc <= opponentBlock.acc) playerBlock.dmg = 0;
            if (opponentBlock.acc <= playerBlock.acc) opponentBlock.dmg = 0;
        }
    }

    private void runTimeLine() {
        Player winner = null;
        Player loser = null;

        for (int i = 0; i < blocksLength; i++) {
            playerOne.HP -= blocksPlayerTwo.get(i).dmg;
            if (playerOne.HP <= 0) {
                playerOne.HP = 0;
                winner = playerTwo.player;
                loser = playerOne.player;
                break;
            }

            playerTwo.HP -= blocksPlayerOne.get(i).dmg;
            if (playerTwo.HP <= 0) {
                playerTwo.HP = 0;
                winner = playerOne.player;
                loser = playerTwo.player;
                break;
            }
        }

        if (winner != null) {
            gameOver = true;

            winner.XP += 10;
            winner.level = 1 + winner.XP % 100;
            winner.coins += 10;
            PlayerService.update(winner);

            History history;
            history = new History(winner.id, loser.id, "Win", 10);
            HistoryService.create(history);
            history = new History(loser.id, winner.id, "Lost", -10);
            HistoryService.create(history);

            return;
        }

        if (currentRound == gameRounds) {
            gameOver = true;

            History history;
            history = new History(playerOne.player.id, playerTwo.player.id, "Draw", 0);
            HistoryService.create(history);
            history = new History(playerTwo.player.id, playerOne.player.id, "Draw", 0);
            HistoryService.create(history);

        } else {
            currentRound++;
            initialize();
        }
    }

    public GamePlayer getCurrentPlayer() {
        return currentPlayer;
    }

    public GamePlayer getPlayerOne() {
        return playerOne;
    }

    public GamePlayer getPlayerTwo() {
        return playerTwo;
    }

    public List<Block> getBlocksPlayerOne() {
        return blocksPlayerOne;
    }

    public List<Block> getBlocksPlayerTwo() {
        return blocksPlayerTwo;
    }

}
