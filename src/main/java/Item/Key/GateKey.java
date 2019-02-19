package Item.Key;

import Item.Consumables;
import Item.Item;
import Item.Keys;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class GateKey extends Keys {

    public GateKey() {
        name = "Gate Key";
        uses = 1;
        Uses = 1;
        try {
            image = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Icons/Gate Key.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GateKey(boolean drop) {
        name = "Gate Key";
        uses = 1;
        Uses = 1;
        this.setDropable(drop);
        try {
            image = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Icons/Gate Key.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public GateKey getCopy() {
        return new GateKey(this.isDropable());
    }
}
