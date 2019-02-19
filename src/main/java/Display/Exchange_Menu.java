package Display;

import Characters.Character;
import Item.HealingItem;
import Item.Item;
import Item.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Exchange_Menu extends JLayeredPane {
    Character player_1;
    Character player_2;
    boolean main_exchange = false;
    boolean in_Pre;
    int cursor_x;
    int cursor_y;
    boolean catch_item_1;
    int item_1;
    int item_2;
    Character bag_1;
    Character bag_2;
    int textwidth;

    @Override
    public void paint(Graphics g) {
        try {
            g.setColor(Color.WHITE);
            if (player_2 != null) {
                if (main_exchange == false && !in_Pre) {
                    int x = 0;
                    if (player_2.x > 5) x = 168;
                    showInfo(g, player_2, x);
                } else {
                    BufferedImage cursor = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Cursor/ItemMenu_Cursor_1.png"));
                    showInfo(g, player_1, 0);
                    showInfo(g, player_2, 168);
                    g.drawImage(cursor, cursor_x, 20 + 20 * cursor_y, cursor.getWidth(), cursor.getHeight(), null);
                }
            }
            if (catch_item_1 == true) {
                int x;
                if (bag_1 == player_1) {
                    x = 0;
                } else {
                    x = 168;
                }
                g.drawImage(ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Cursor/ItemMenu_Cursor_1.png")), x, 20 + 20 * item_1, null);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showInfo(Graphics g, Character c, int x) throws IOException {
        BufferedImage ItemBox = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Menu/ExchangeMenu.png"));

        g.drawImage(ItemBox, x, 20, ItemBox.getWidth(), ItemBox.getHeight(), null);
        if (!c.getBag().isEmpty()) {
            int i = 0;
            for (Item item : c.getBag()) {
                g.drawImage(item.getImage(), x, 20 + 20 * i, 20, 20, null);
                g.drawString(item.getName(), 30 + x, 35 + 20 * i);

                if (item == player_2.getEquiped_Weapon()) {
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

    public void exchangeItem() {
        if (bag_1 == bag_2 && item_1 == item_2) {
            clean_exchange();
        } else {
            if (item_2 == bag_2.getBag().size()) {
                bag_2.getBag().add(bag_1.getBag().get(item_1));
                bag_1.getBag().remove(item_1);
                clean_exchange();
                player_1.setMovepoint(false);
            } else {
                Item t = bag_1.getBag().get(item_1);
                bag_1.getBag().set(item_1, bag_2.getBag().get(item_2));
                bag_2.getBag().set(item_2, t);
                clean_exchange();
                player_2.setMovepoint(false);
            }
        }
    }

    public void clean_exchange() {
        bag_1 = null;
        bag_2 = null;
        item_1 = 0;
        item_2 = 0;
        catch_item_1 = false;
    }

    private void drawRight(Graphics g, String text, int x, int y) {
        textwidth = g.getFontMetrics().stringWidth(text);
        g.drawString(text, x - textwidth, y);
    }

    public int bag_limit() {
        if (cursor_x > 0) {
            return Math.min(Character.getBag_size() - 1, player_2.getBag().size());
        } else {
            return Math.min(Character.getBag_size() - 1, player_1.getBag().size());
        }
    }

    public void setPlayer_1(Character player_1) {
        this.player_1 = player_1;
    }

    public void setPlayer_2(Character player_2) {
        this.player_2 = player_2;
    }

    public void setMain_exchange(boolean main_exchange) {
        this.main_exchange = main_exchange;
    }

    public void setCursor_x(int cursor_x) {
        this.cursor_x = cursor_x;
    }

    public void shift_CursorX() {
        cursor_x = 168 - cursor_x;
    }

    public void setCursor_y(int cursor_y) {
        this.cursor_y = cursor_y;
    }

    public void setCatch_item_1(boolean catch_item_1) {
        this.catch_item_1 = catch_item_1;
    }

    public void setItem_1(int item_1) {
        this.item_1 = item_1;
    }

    public void setItem_2(int item_2) {
        this.item_2 = item_2;
    }

    public void setBag_1(Character bag_1) {
        this.bag_1 = bag_1;
    }

    public void setBag_2(Character bag_2) {
        this.bag_2 = bag_2;
    }

    public Character getPlayer_1() {
        return player_1;
    }

    public Character getPlayer_2() {
        return player_2;
    }

    public boolean isMain_exchange() {
        return main_exchange;
    }

    public int getCursor_x() {
        return cursor_x;
    }

    public int getCursor_y() {
        return cursor_y;
    }

    public boolean isCatch_item_1() {
        return catch_item_1;
    }

    public int getItem_1() {
        return item_1;
    }

    public int getItem_2() {
        return item_2;
    }

    public Character getBag_1() {
        return bag_1;
    }

    public Character getBag_2() {
        return bag_2;
    }

    public void shift_CursorY(int val) {
        cursor_y += val;
    }
}
