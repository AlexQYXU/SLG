import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Preparation_Interface extends JLayeredPane {
    BufferedImage background;
    BufferedImage cursor;
    int cursor_y;
    Font font = new Font("Dialog", 0, 12);
    Font font_2 = new Font("Dialog", 1, 20);
    Chapter chapter;
    String[] description;


    public Preparation_Interface() {
        try {
            background = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Menu/Fog.png"));
            cursor = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Cursor/ItemMenu_Cursor_1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(background, 0, 256, null);
        g.setFont(font_2);
        g.drawString("Battle Preparation", 20, 276);
        g.setFont(font);
        g.drawString("Z: Confirm",288,300);
        g.drawString("X: Cancel",288,320);
        g.drawString("Characters", 20, 356);
        g.drawString("Items", 20, 376);
        g.drawString("Market", 20, 396);
        g.drawString("Map", 20, 416);
        g.drawString("SAVE", 20, 436);
        g.drawString("Press Enter to start chapter", 100, 500);
        g.setColor(Color.WHITE);
        g.drawImage(cursor, 20, 341 + 20 * cursor_y, null);
        g.setColor(Color.BLACK);

    }

    public void shift_CursorY(int val) {

        if (cursor_y + val < 0) {
            cursor_y += val + 5;
        } else if (cursor_y + val > 4) {
            cursor_y += val - 5;
        } else {
            cursor_y += val;
        }
    }
}
