package Display;

import Characters.Character;
import Item.Item;
import Item.Consumables;
import jdk.nashorn.api.tree.WhileLoopTree;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Store_Menu extends JLayeredPane {
    BufferedImage ItemBox;
    BufferedImage Menu;
    BufferedImage cursor;
    Character c;
    Item drop;
    int cursor_y;
    int textwidth;


    public Store_Menu() {
        try {
            ItemBox = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Menu/Transporter.png"));
            cursor = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Cursor/ItemMenu_Cursor_1.png"));
            Menu = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Characters/MenuTileset.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Dialog", 1, 12));
        g.drawImage(ItemBox, 32, 32, null);
        g.drawImage(cursor, 32, 32 + 20 * cursor_y, null);
        int i = 0;
        for (Item item : c.getBag()) {
            drawItem(g, item, 32, 32 + 20 * i);
            i++;
        }
        if (drop != null) {
            drawItem(g, drop, 32, 132);
        }
    }


    public void drawItem(Graphics g, Item item, int x, int y) {
        g.drawImage(item.getImage(), x, y, 20, 20, null);
        g.drawString(item.getName(), x + 20, y + 15);
        if (item instanceof Consumables) {
            drawRight(g, ((Consumables) item).getUses() + "/", x + 120, y + 15);
            drawRight(g, Integer.toString(((Consumables) item).getUSES()), x + 140, y + 15);
            System.out.println(x + 120);
        }
    }

    private void drawRight(Graphics g, String text, int x, int y) {
        textwidth = g.getFontMetrics().stringWidth(text);
        g.drawString(text, x - textwidth, y);
    }

    private void drawCentre(Graphics g, String text, int x, int y) {
        textwidth = g.getFontMetrics().stringWidth(text);
        g.drawString(text, x - textwidth / 2, y);
    }

    public void ItemBox_Up() {
        if (cursor_y > 0) {
            cursor_y--;
        } else {
            cursor_y = c.getBag().size() - 1;
        }
    }

    public int getCursor_y() {
        return cursor_y;
    }

    public void ItemBox_Down() {
        if (cursor_y < c.getBag().size() - 1) {
            cursor_y++;
        } else {
            cursor_y = 0;
        }
    }

    public void setC(Character c) {
        this.c = c;
    }

    public void setDrop(Item drop) {
        this.drop = drop;
    }

    public void clean() {
        c = null;
        drop = null;
        cursor_y = 0;
    }
}
