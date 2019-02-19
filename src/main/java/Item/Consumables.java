package Item;

public abstract class Consumables extends Item {
    protected int uses;
    protected int Uses;

    public void consume() {
        uses--;
    }

    public int getUses() {
        return uses;
    }

    public int getUSES() {
        return Uses;
    }
}
