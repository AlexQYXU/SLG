package Item.Envir_Items;

import Item.Envir_Item;
import Item.Item;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Obstacle extends Envir_Item {
    BufferedImage image_H;
    BufferedImage image_V;

    public Obstacle() {
        try {
            image_H = ImageIO.read(new File(System.getProperty("user.dir") + "/src/maim/resources/Characters/Obstacle_A"));
            image_V = ImageIO.read(new File(System.getProperty("user.dir") + "/src/maim/resources/Characters/Obstacle_B"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
