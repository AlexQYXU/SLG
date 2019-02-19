import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class File_Interface extends JLayeredPane {
    BufferedImage background;
    BufferedImage cursor;
    BufferedImage filebox;
    int cursor_y;
    int textwidth;
    ArrayList<SAVE> saves;


    public File_Interface() {
        try {
            background = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Menu/Fog.png"));
            cursor = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Cursor/LongCursor.png"));
            filebox = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Menu/Message_Window.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setSaves(ArrayList<SAVE> saves) {
        this.saves = saves;
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(background, 0, 256, null);
        g.drawImage(filebox, 76, 352, null);
        g.drawImage(filebox, 76, 402, null);
        g.drawImage(filebox, 76, 452, null);
        for (int i = 0; i < 3; i++) {
            if (saves.size() > i) {
                drawCentre(g, "Chapter " + saves.get(i).chapters.size(), 176, 375 + 50 * i);
            } else {
                drawCentre(g, "NO DATA", 176, 375 + 50 * i);
            }
        }
        g.drawImage(cursor, 76, 356 + 50 * cursor_y, 224, 32, null);

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

    public void reset() {
        cursor_y = 0;
    }
}
