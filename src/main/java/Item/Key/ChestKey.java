package Item.Key;

import Item.Consumables;
import Item.Item;
import Item.Keys;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class ChestKey extends Keys {
    public ChestKey() {
        name = "Chest Key";
        uses = 2;
        Uses = 2;
        try {
            image = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Icons/Chest Key.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ChestKey(boolean drop) {
        name = "Chest Key";
        uses = 2;
        Uses = 2;
        this.setDropable(drop);
        try {
            image = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Icons/Chest Key.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ChestKey(int uses) {
        name = "Chest Key";
        if (uses == 1 || uses == 2) {
            this.uses = uses;
        } else {
            this.uses = 2;
        }
        Uses = 2;
        try {
            image = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Icons/Chest Key.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ChestKey(int uses, boolean drop) {
        name = "Chest Key";
        if (uses == 1 || uses == 2) {
            this.uses = uses;
        } else {
            this.uses = 2;
        }
        Uses = 2;
        this.setDropable(drop);
        try {
            image = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Icons/Chest Key.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ChestKey getCopy() {
        return new ChestKey(this.uses, this.isDropable());
    }
}
