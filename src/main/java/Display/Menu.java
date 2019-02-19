package Display;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Menu extends JLayeredPane {
    int row = 1;

    ArrayList<String> list = new ArrayList<>();
    cursor cursor = new cursor();
    public int[] cursor_y = new int[5];

    public void subOption(int i, String name) throws IOException {
        list.add(name);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 13));
        try {
            int i = 0;
            for (String str : list) {
                g.drawImage(ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Characters/MenuTileset.png")), 0, 32 * i, 64, 32, null);
                g.drawString(str, 0, 32 * i + 20);
                i++;
            }
            g.drawImage(cursor.draw(), 0, 32 * cursor.y, 64, 32, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class cursor {
        int y = 0;
        String path = System.getProperty("user.dir") + "/src/main/resources/Cursor/MenuCursor.png";

        public BufferedImage draw() throws IOException {
            return ImageIO.read(new File(path));
        }
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getcursory() {
        return cursor.y;
    }

    public void shift_cursor(int val) {
        cursor.y += val;
    }

    public void setcursory(int val) {
        cursor.y = val;
    }

    public int[] getCursor_y() {
        return this.cursor_y;
    }

    public ArrayList<String> getList() {
        return this.list;
    }
}
