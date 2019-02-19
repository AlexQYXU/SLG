package Item.Envir_Items;

import Item.Envir_Item;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Throne extends Envir_Item {
    public Throne(int X, int Y) {
        this.name = "Throne";
        this.X = X;
        this.Y = Y;
        this.width = 1;
        this.height = 2;
        this.DEF_Bonus = 3;
        this.AVO_Bonus = 30;
        this.HEAL_Bonus = 20;
        try {
            image = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Environment/Throne.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public BufferedImage draw() {
        return image;
    }


    //    @Override
//    public BufferedImage draw()   {
//
//    }
}
