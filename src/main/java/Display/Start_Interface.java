package Display;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Start_Interface extends JLayeredPane {
    BufferedImage background;
    BufferedImage cursor;
    BufferedImage menu;
    int cursor_y;
    int textwidth;
    Font font = new Font("Dialog", 1, 12);

    public Start_Interface() {
        try {
            background = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Menu/Fog.png"));
            cursor = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Cursor/MenuCursor.png"));
            menu = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Characters/MenuTileset.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paint(Graphics g) {
        System.out.println(cursor_y);
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawImage(background, 0, 256, null);
        g.drawImage(menu, 144, 352, null);
        g.drawImage(menu, 144, 384, null);
        g.drawImage(menu, 144, 416, null);
        drawCentre(g, "New Game", 176, 370);
        drawCentre(g, "Continue", 176, 402);
        drawCentre(g, "Quit", 176, 434);
        g.drawImage(cursor, 144, 352 + 32 * cursor_y, null);
    }

    private void drawCentre(Graphics g, String text, int x, int y) {
        textwidth = g.getFontMetrics().stringWidth(text);
        g.drawString(text, x - textwidth / 2, y);
    }

    public void Cursor_UP() {
        if (cursor_y > 0) {
            cursor_y--;
        } else {
            cursor_y = 2;
        }
    }

    public void Cursor_DOWN() {
        if (cursor_y < 2) {
            cursor_y++;
        } else {
            cursor_y = 0;
        }
    }

    public int getCursor_y() {
        return cursor_y;
    }
}
