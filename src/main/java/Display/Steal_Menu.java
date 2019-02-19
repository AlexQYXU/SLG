package Display;

import Characters.Character;
import Item.Item;
import Item.Consumables;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Steal_Menu extends JLayeredPane {

    Character thief;
    Character target;
    int cursor_y;
    boolean showMenu;
    int textwidth;
    BufferedImage ItemBox;
    BufferedImage cursor;
    Font font = new Font("Dialog", 1, 12);

    public Steal_Menu() {
        try {
            cursor = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Cursor/ItemMenu_Cursor_1.png"));
            ItemBox = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Menu/ExchangeMenu.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(font);
        if (target != null) showInfo(g, target, 32);
        if (showMenu) {
            g.drawImage(cursor, 32, 20 + 20 * cursor_y, null);
        } else {

        }
    }

    public int getCursor_y() {
        return cursor_y;
    }

    public void setThief(Character thief) {
        this.thief = thief;
    }

    public void setTarget(Character target) {
        this.target = target;
    }

    public Character getTarget() {
        return target;
    }

    public void setCursor_y(int cursor_y) {
        this.cursor_y = cursor_y;
    }

    public void setShowMenu(boolean showMenu) {
        this.showMenu = showMenu;
    }

    public boolean isShowMenu() {
        return showMenu;
    }

    public void Cursor_Down() {
        if (cursor_y < target.getBag().size() - 1) {
            cursor_y++;
        } else {
            cursor_y = 0;
        }
    }

    public void Cursor_Up() {
        if (cursor_y > 0) {
            cursor_y--;
        } else {
            cursor_y = target.getBag().size() - 1;
        }
    }

    private void showInfo(Graphics g, Character c, int x) {


        g.drawImage(ItemBox, x, 20, ItemBox.getWidth(), ItemBox.getHeight(), null);
        if (!c.getBag().isEmpty()) {
            int i = 0;
            for (Item item : c.getBag()) {
                g.drawImage(item.getImage(), x, 20 + 20 * i, 20, 20, null);
                g.drawString(item.getName(), 30 + x, 35 + 20 * i);

                if (item == c.getEquiped_Weapon()) {
                    g.setFont(new Font("Dialog", Font.BOLD, 8));
                    g.drawString("E", x + 15, 35 + 20 * i);
                    g.setFont(new Font("Dialog", 0, 12));
                }

                if (item instanceof Consumables) {
                    drawRight(g, ((Consumables) item).getUses() + "/", x + 125, 35 + 20 * i);
                    drawRight(g, Integer.toString(((Consumables) item).getUSES()), x + 140, 35 + 20 * i);
                }

                i++;
            }
        }
        g.drawImage(c.getCharacter_Image().getSubimage(0, 0, 32, 32), 10 + x, 0, 32, 32, null);
        g.drawString(c.getName(), 42 + x, 18);
    }

    private void drawRight(Graphics g, String text, int x, int y) {
        textwidth = g.getFontMetrics().stringWidth(text);
        g.drawString(text, x - textwidth, y);
    }

    public Item check_Steal(){
        Item item = target.getBag().get(cursor_y);
        if(item != target.getEquiped_Weapon() && item.getWeight()<thief.getStr()){
            return item;
        }
        return null;
    }
}
