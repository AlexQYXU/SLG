package Display;

import Characters.Character;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Cursor {
    static String path = System.getProperty("user.dir") + "/src/main/resources/Cursor";
    Character aim;
    int x = 0;
    int y = 0;

    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public BufferedImage draw() throws IOException {
        return (ImageIO.read(new File(path + "/Cursor.png")));
    }

    public int getx() {
        return x;
    }

    public int gety() {
        return y;
    }

    public Character getAim() {
        return aim;
    }

    public void setAim(Character aim) {
        this.aim = aim;
    }

    public void shift_x(int val) {
        x += val;
    }

    public void shift_y(int val) {
        y += val;
    }

    public void reset() {
        x = 0;
        y = 0;
    }

    public void setx(int x) {
        this.x = x;
    }

    public void sety(int y) {
        this.y = y;
    }
}

