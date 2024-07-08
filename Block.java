package game;

public class Block {
    boolean isEmpty = true;
    boolean isDestroyed = false;
    public int acc;
    public int dmg;

    @Override
    public String toString() {
        if (isDestroyed) return "*******";
        if (isEmpty) return "-------";
        return String.format(" %-2d  %-2d", acc, dmg);
    }
}
