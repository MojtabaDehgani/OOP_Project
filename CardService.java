package game.card;

import database.Database;
import game.player.Player;
import game.player.PlayerService;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class CardService {
    public static boolean buyCard(int playerId,int cardId){
 /*       Card card = get(cardId);
       Card c= getUserCards(playerId).stream().filter(e->e.equals(card)).findAny().orElse(null);
     if(c!=null){
         return false;
     }*/
   return CardRepository.buyCard(playerId,cardId);
    }

    public static boolean isValid(Card card) {
        if (card.playerDamage % card.duration != 0) {
            System.out.println("Card playerDamage % duration must be 0!");
            return false;
        }
        return true;
    }

    public static Card copy(Card card) {
        return card;
    }

    public static void create(Card card) {
        if (isValid(card)) CardRepository.insert(card);
    }

    public static Card get(int id) {
        return CardRepository.get(id);
    }

    public static List<Card> getAll() {
        return CardRepository.getAll();
    }

    public static List<Card> getUserCards(int userId) {
        Player player = PlayerService.getByUserId(userId);
        return player.cards;
    }

    public static void update(Card card) {
        if (isValid(card)) CardRepository.update(card);
    }

    public static void delete(Card card) {
        CardRepository.delete(card);
    }

}
