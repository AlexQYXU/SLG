package Item;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import Characters.Character;

import javax.imageio.ImageIO;

public class HealingItem extends Consumables implements Usable {
    int val;


    public HealingItem(String name, int val, int uses, int price, BufferedImage image) {
        this.name = name;
        this.val = val;
        this.Uses = uses;
        this.uses = uses;
        this.image = image;
        this.usable = true;
        this.price = price;
    }

    public HealingItem(String name, int val, int uses, int price, BufferedImage image, boolean drop) {
        this.name = name;
        this.val = val;
        this.Uses = uses;
        this.uses = uses;
        this.image = image;
        this.usable = true;
        this.price = price;
        this.weight = 0;
        this.setDropable(drop);
    }


    @Override
    public void use(Character c) {
        if (c.getHP() > c.gethp()) {
            c.sethp(Math.min(c.getHP(), c.gethp() + val));
            uses--;
        }

        if (uses == 0) {
            c.getBag().remove(this);
        }
    }

    public int getVal() {
        return val;
    }

    public int getUses() {
        return uses;
    }

    public void setUses(int val) {
        uses = val;
    }

    @Override
    public HealingItem getCopy() {
        HealingItem item = new HealingItem(this.name, this.val, this.uses, this.price, this.image);
        item.setDropable(this.isDropable());
        return item;
    }

    public static HealingItem getVulnerary() throws IOException {
        return new HealingItem("Vulnerary", 10, 3, 300, ImageIO.read
                (new File(System.getProperty("user.dir") + "/src/main/resources/Icons/Vulnerary.png")));
    }

    public static HealingItem getVulnerary(boolean drop) throws IOException {
        return new HealingItem("Vulnerary", 10, 3, 300, ImageIO.read
                (new File(System.getProperty("user.dir") + "/src/main/resources/Icons/Vulnerary.png")), drop);
    }
}
