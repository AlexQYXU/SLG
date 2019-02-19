package Item.Envir_Items;

import Characters.Camp;
import Item.Envir_Item;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Castle extends Envir_Item {
    public Castle(int X, int Y) {
        try {
            name = "Castle";
            image = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Environment/Null.png"));
            this.X = X;
            this.Y = Y;
            width = 1;
            height = 1;
            this.DEF_Bonus = 3;
            this.AVO_Bonus = 20;
            this.HEAL_Bonus = 10;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
