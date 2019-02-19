package Display;

import Characters.Character;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Level_UP extends JLayeredPane {
    Character c;
    BufferedImage background;
    BufferedImage arrow;
    int textwidth;
    boolean[] up = new boolean[8];
    Font font = new Font("Dialog", 1, 12);

    public Level_UP() {
        try {
            background = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Menu/Level_UP.png"));
            arrow = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Icons/arrowIcon_up.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paint(Graphics g) {
        g.setFont(font);
        g.setColor(Color.WHITE);
        g.drawImage(background, 0, 0, null);
        drawInfo(g);
    }

    public void setC(Character c) {
        this.c = c;
    }

    private void drawInfo(Graphics g) {
        g.drawString(c.getJob().toString(), 50, 25);
        g.drawString("Lv", 140, 25);
        drawRight(g, Integer.toString(c.getLv()), 170, 25);
        g.drawString("HP", 20, 85);
        g.drawString("Str", 20, 115);
        g.drawString("Mag", 20, 145);
        g.drawString("Skl", 20, 175);
        g.drawString("Spd", 120, 85);
        g.drawString("Luk", 120, 115);
        g.drawString("Def", 120, 145);
        g.drawString("Res", 120, 175);
        drawRight(g, Integer.toString(c.getHP()), 70, 85);
        drawRight(g, Integer.toString(c.getStr()), 70, 115);
        drawRight(g, Integer.toString(c.getMag()), 70, 145);
        drawRight(g, Integer.toString(c.getSkl()), 70, 175);
        drawRight(g, Integer.toString(c.getSpd()), 170, 85);
        drawRight(g, Integer.toString(c.getLuk()), 170, 115);
        drawRight(g, Integer.toString(c.getDef()), 170, 145);
        drawRight(g, Integer.toString(c.getRes()), 170, 175);
        g.setColor(Color.GREEN);
        for (int i = 0; i <8; i++) {
            if (up[i]) {
                g.drawImage(arrow, 80 + i / 4 * 100, 55 + i % 4 * 30, null);
                System.out.println(80 + i / 4 * 100);
                g.drawString("+1", 80 + i / 4 * 100, 85 + i % 4 * 30);
            }
        }
        g.setColor(Color.WHITE);
    }

    private void drawRight(Graphics g, String text, int x, int y) {
        textwidth = g.getFontMetrics().stringWidth(text);
        g.drawString(text, x - textwidth, y);
    }

    public boolean[] getUp() {
        return up;
    }

    public void clean() {
        c = null;
        for (int i = 0; i < 8; i++) {
            up[i] = false;
        }
    }
}
