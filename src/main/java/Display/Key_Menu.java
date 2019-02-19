package Display;

import Characters.Character;
import Item.Envir_Item;
import Item.Item;
import Item.Key.ChestKey;
import Item.Key.GateKey;
import Item.Keys;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Key_Menu extends JLayeredPane {
    protected Character user;
    protected Envir_Item target;
    protected boolean menu_switch;
    protected int cursor_y;
    protected int size;
    protected ArrayList<Keys> keys = new ArrayList<>();
    protected Keys aim_key;


    @Override
    public void paint(Graphics g) {
        try {
            System.out.println("key menu");
            BufferedImage ItemBox = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Menu/ExchangeMenu.png"));
            BufferedImage cursor = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Cursor/ItemMenu_Cursor_1.png"));
            g.setColor(Color.WHITE);
            g.setFont(new Font("Dialog", 0, 12));
            if (menu_switch) {
                System.out.println("in key menu");
                g.drawImage(ItemBox, 32, 60, null);
                size = 0;
                for (Keys key : keys) {
                    g.drawImage(key.getImage(), 32, 60 + 20 * size, null);
                    g.drawString(key.getName(), 62, 75 + 20 * size);
                    g.drawString(String.valueOf(key.getUses()), 152, 75 + 20 * size);

                    if (size == cursor_y) {
                        aim_key = key;
                        size++;
                    }
                    g.drawImage(cursor, 32, 60 + 20 * cursor_y, cursor.getWidth(), cursor.getHeight(), null);
                }
            }else{
                System.out.println("???");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Keys getAim_key() {
        return aim_key;
    }

    public void setMenu_switch(boolean t){
        menu_switch = t;
    }

    public void setUser(Character user) {
        this.user = user;
    }

    public void setCursor_y(int i) {
        cursor_y = i;
    }

    public void GateKey_List() {
        keys.clear();
        for (Item item : user.getBag()) {
            if (item instanceof GateKey) {
                keys.add((GateKey) item);
            }
        }
    }

    public void ChestKey_List() {
        keys.clear();
        for (Item item : user.getBag()) {
            if (item instanceof ChestKey) {
                keys.add((ChestKey) item);
            }
        }

    }

    public void setTarget(Envir_Item target) {
        this.target = target;
    }

    public Envir_Item getTarget() {
        return target;
    }

    public ArrayList<Keys> getKeys() {
        return keys;
    }


    public boolean isMenu_switch() {
        return menu_switch;
    }

    public void shift_CursorY(int val) {
        if (cursor_y + val >= keys.size()) {
            cursor_y = cursor_y + val - keys.size();
        }
        if (cursor_y + val < 0) {
            cursor_y = cursor_y + keys.size() + val;
        }
    }

    public void clean() {
        user = null;
        target = null;
        menu_switch = false;
        cursor_y = 0;
        size = 0;
        aim_key = null;
        keys.clear();
    }
}
