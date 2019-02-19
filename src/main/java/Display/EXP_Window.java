package Display;

import Characters.Character;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class EXP_Window extends JLayeredPane {
    BufferedImage window;
    BufferedImage exp_bar;
    BufferedImage Exp_bar;
    Character c;
    int exp;

    public EXP_Window() {
        try {
            window = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Menu/EXP_Window.png"));
            exp_bar = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Menu/exp.png"));
            Exp_bar = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Menu/Exp_bar.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Dialog", 1, 12));
        g.drawImage(window, 0, 0, null);
        g.drawImage(Exp_bar, 30, 15, null);
        g.drawImage(exp_bar.getSubimage(0, 0, 5 + 2 * exp, 12), 30, 15, null);
        g.drawString(Integer.toString(exp), 100, 25);
    }

    public void setexp(int val) {
        this.exp = val;
    }

    public int getexp() {
        return exp;
    }

    public void setC(Character c) {
        this.c = c;
    }

    public Character getC() {
        return c;
    }

    public void exp_plus() {
        exp++;
        if (exp > 100) {
            exp -= 100;
        }
    }

    public void clean() {
        c = null;
        exp = 0;
    }

}
