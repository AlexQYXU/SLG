package Item.Envir_Items;

import Item.Envir_Item;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Pillar extends Envir_Item {
    public Pillar(int X, int Y) {
        this.name = "Pillar";
        this.X = X;
        this.Y = Y;
        this.width = 1;
        this.height = 2;
        this.DEF_Bonus = 1;
        this.AVO_Bonus = 20;
        try {
            image = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Environment/Pillar.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
