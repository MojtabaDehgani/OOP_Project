package game.history;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class History {
    public Integer id;
    public Instant dateTime;
    public int playerId;
    public int opponentId;
    public String result;
    public int score;

    public History() {
    }

    public History(int playerId, int opponentId, String result, int score) {
        this.playerId = playerId;
        this.opponentId = opponentId;
        this.result = result;
        this.score = score;
    }

    public OffsetDateTime getDateTime() {
        ZoneOffset z = ZoneOffset.systemDefault().getRules().getOffset(dateTime);
        return dateTime.atOffset(z);
    }

    @Override
    public String toString() {
        return "id: " + id +
                ", date: " + getDateTime() +
                ", playerId: " + playerId +
                ", opponentId: " + opponentId +
                ", result: " + result +
                ", score: " + score
                ;
    }
}
