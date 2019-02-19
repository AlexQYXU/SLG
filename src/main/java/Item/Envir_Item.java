package Item;

import Characters.Character;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class Envir_Item extends Item {
    protected int X;
    protected int Y;
    protected int DEF_Bonus;
    protected int AVO_Bonus;
    protected int HEAL_Bonus;
    protected int width;
    protected int height;
    protected Character unit;

    public int getY() {
        return Y;
    }

    public void setY(int y) {
        Y = y;
    }

    public int getX() {
        return X;
    }

    public void setX(int x) {
        X = x;
    }


    public BufferedImage draw() {
        return image;
    }

    public void getBonus() {
        unit.setEnvir_DEF(DEF_Bonus);
        unit.setEnvir_AVO(AVO_Bonus);
    }

    public int getDEF_Bonus() {
        return DEF_Bonus;
    }

    public int getAVO_Bonus() {
        return AVO_Bonus;
    }

    public int getHEAL_Bonus() {
        return HEAL_Bonus;
    }

    public void setUnit(Character unit) {
        this.unit = unit;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public Envir_Item getCopy() {
        return null;
    }
}
