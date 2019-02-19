package Item.Envir_Items;

import Item.Envir_Item;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Forest extends Envir_Item {
    public Forest(int X, int Y) {
        try {
            name = "Forest";
            image = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Environment/Null.png"));
            this.X = X;
            this.Y = Y;
            width = 1;
            height = 1;
            this.DEF_Bonus = 0;
            this.AVO_Bonus = 10;
            this.HEAL_Bonus = 0;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
