package Item;

import Characters.Character;

import java.awt.image.BufferedImage;

public abstract class Item {
    protected String name;
    protected BufferedImage image;
    protected int weight;
    boolean usable = false;
    boolean equipable = false;
    private String description;
    protected Character owner;
    private boolean dropable;
    protected int price;

    public Item(String name) {
        this.name = name;
    }

    public Item() {

    }

    public abstract Item getCopy();

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Character getOwner() {
        return owner;
    }

    public void setOwner(Character owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public BufferedImage getImage() {
        return image;
    }

    public int getWeight() {
        return weight;
    }

    public boolean isUsable() {
        return usable;
    }

    public boolean isEquipable() {
        return equipable;
    }

    public boolean equipable() {
        return equipable;
    }

    public void setDropable(boolean dropable) {
        this.dropable = dropable;
    }

    public boolean isDropable() {
        return dropable;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Item && ((Item) obj).getClass() == this.getClass() && ((Item) obj).getName() == this.getName()) {
            return true;
        }
        return false;
    }
}
