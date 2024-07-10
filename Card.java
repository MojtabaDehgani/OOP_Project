package game.card;

import java.util.Objects;

public class Card {
    public Integer id;
    public String name;
    public boolean isSpecial;
    public int defenceAttack;
    public int duration;
    public int playerDamage;
    public int upgradeLevel;
    public int upgradeCost;
    public int price;

    //optional image
    public String imagePath;

    public Card() {
    }

    public Card(String name, boolean isSpecial, int defenceAttack, int duration, int playerDamage, int upgradeLevel, int upgradeCost, int price) {
        this.name = name;
        this.isSpecial = isSpecial;
        this.defenceAttack = defenceAttack;
        this.duration = duration;
        this.playerDamage = playerDamage;
        this.upgradeLevel = upgradeLevel;
        this.upgradeCost = upgradeCost;
        this.price = price;
    }

    public String toStringGame() {
        String acc = String.format("acc: %d", defenceAttack);
        String dmg = String.format("dmg: %d", playerDamage);
        String dur = String.format("dur: %d", duration);
        return String.format("%d. %-30s %s %s %s", id, name, acc, dmg, dur);
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isSpecial=" + isSpecial +
                ", defenceAttack=" + defenceAttack +
                ", duration=" + duration +
                ", playerDamage=" + playerDamage +
                ", upgradeLevel=" + upgradeLevel +
                ", upgradeCost=" + upgradeCost +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return isSpecial == card.isSpecial && defenceAttack == card.defenceAttack && duration == card.duration && playerDamage == card.playerDamage && upgradeLevel == card.upgradeLevel && upgradeCost == card.upgradeCost && price == card.price && Objects.equals(id, card.id) && Objects.equals(name, card.name) && Objects.equals(imagePath, card.imagePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, isSpecial, defenceAttack, duration, playerDamage, upgradeLevel, upgradeCost, price, imagePath);
    }
}
