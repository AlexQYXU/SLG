package Display;

import Characters.Character;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HP_Window extends JLayeredPane {
    Character c;
    int hp;
    int HP;
    private  BufferedImage window;
    private  BufferedImage hp_bar;
    private  BufferedImage HP_bar;

    private Font font = new Font("Dialog",1,12);


    public HP_Window() {
        try {
            window = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Menu/HP_Window.png"));
            hp_bar = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Menu/hp.png"));
            HP_bar = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Menu/HP_bar.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paint(Graphics g) {
        g.setFont(font);
        g.setColor(Color.WHITE);
        g.drawString(c.getName(), 0, 15);
        g.drawImage(window, 0, 20, null);
        drawHP(g);
    }

    private void drawHP(Graphics g) {
        hp = Math.max(hp, 0);
        if (HP <= 30) {
            g.drawImage(HP_bar.getSubimage(0, 0, 4 * HP + 1, 12), 2, 36, null);
            g.drawImage(hp_bar.getSubimage(0, 0, 4 * hp + 1, 12), 2, 36, null);
        } else {
            g.drawImage(HP_bar.getSubimage(0, 0, 4 * (HP - 30) + 1, 12), 2, 28, null);
            g.drawImage(HP_bar, 2, 44, null);
            g.drawImage(hp_bar.getSubimage(0, 0, 4 * Math.max(hp - 30, 0), 12), 2, 28, null);
            g.drawImage(hp_bar.getSubimage(0, 0, 4 * Math.min(hp, 30), 12), 2, 44, null);

        }
    }

    public Character getC() {
        return c;
    }

    public void setC(Character c) {
        this.c = c;
    }

    public void sethp(int hp) {
        this.hp = hp;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public int gethp() {
        return hp;
    }

    public void hp_plus() {
        hp++;
    }

    public void hp_minus() {
        hp--;
    }
}
