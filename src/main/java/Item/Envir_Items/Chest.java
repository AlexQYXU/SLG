package Item.Envir_Items;

import Characters.Character;
import Characters.Job;
import Item.*;
import Item.Key.ChestKey;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Chest extends Envir_Item implements Lock {
    Item treasure;
    boolean locked;
    BufferedImage image_1;
    BufferedImage image_2;

    public Chest(int X, int Y, Item treasure) {
        this.name = "Chest";
        this.setX(X);
        this.setY(Y);
        this.width = 1;
        this.height = 1;
        this.treasure = treasure;
        this.locked = true;
        try {
            BufferedImage image = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Environment/Chest_Red.png"));
            image_1 = image.getSubimage(0, 0, 32, 32);
            image_2 = image.getSubimage(0, 96, 32, 32);
            this.image = image_1;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Chest emptyChest(int X, int Y) {
        Chest chest = new Chest(X, Y, null);
        chest.locked = false;
        chest.image = chest.image_2;
        return chest;
    }

    @Override
    public boolean isLocked() {
        return locked;
    }

    @Override
    public Object unlock(Object key) {
        if (locked == false) return treasure;

        if (key instanceof ChestKey) {
            locked = false;
            ((Consumables) key).consume();
            Object sub = treasure;
            treasure = null;
            return sub;
        } else if (key instanceof Staff && ((Staff) key).getMagicEffect() instanceof UnlockMagic) {
            locked = false;
            ((Staff) key).consume();
            Object sub = treasure;
            treasure = null;
            return sub;
        } else if (key instanceof Character && ((Character) key).getJob() == Job.Thief) {
            locked = false;
            Object sub = treasure;
            treasure = null;
            return sub;
        } else {
            return null;
        }
    }

    @Override
    public BufferedImage draw() {
        if (locked)
            return image_1;
        else return image_2;
    }

    public Object getTreasure() {
        return treasure;
    }
}
