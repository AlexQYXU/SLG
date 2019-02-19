package Display;

import Characters.Character;
import Item.Item;
import Item.Staff;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Staff_Menu extends JLayeredPane {
     Character user;
     Object target;
     boolean menu_switch;
     int cursor_y;
     int size;
     Staff aim_staff;
     ArrayList<Staff> staff = new ArrayList<>();
     int textwidth;

    @Override
    public void paint(Graphics g) {
        try {
            BufferedImage ItemBox = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Menu/ExchangeMenu.png"));
            BufferedImage cursor = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Cursor/ItemMenu_Cursor_1.png"));
            BufferedImage AbilityBox = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Menu/Ability_Menu.png"));
            g.setColor(Color.WHITE);
            g.setFont(new Font("Dialog", 0, 12));
            if (menu_switch) {
                g.drawImage(ItemBox, 32, 60, null);
                g.drawImage(AbilityBox, 172, 160, null);
                size = 0;
                for (Staff staff : staff) {
                    g.drawImage(staff.getImage(), 32, 60 + 20 * size, null);
                    g.drawString(staff.getName(), 62, 75 + 20 * size);
                    drawRight(g, staff.getUses() + "/", 157, 75 + 20 * size);
                    drawRight(g, Integer.toString(staff.getUSES()), 172, 75 + 20 * size);
//                    g.drawString(String.valueOf(staff.getUses()), 132, 75 + 20 * size);
                    if (staff == user.getEquiped_Weapon()) {
                        g.setFont(new Font("Dialog", Font.BOLD, 8));
                        g.drawString("E", 47, 75 + 20 * size);
                        g.setFont(new Font("Dialog", 0, 12));
                    }
                    if (size == cursor_y) {
                        aim_staff = staff;
                        g.drawString(staff.getDescription(), 172, 180);
                    }
                    size++;
                }
                g.drawImage(cursor, 32, 60 + 20 * cursor_y, cursor.getWidth(), cursor.getHeight(), null);
            } else {
                if (target instanceof Character) {
                    showInfo(g, (Character) target);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  void setUser(Character user) {
        this.user = user;
    }

    public  void setTarget(Object target) {
        this.target = target;
    }

    public  void setMenu_switch(boolean menu_switch) {
        this.menu_switch = menu_switch;
    }

    public  void setCursor_y(int cursor_y) {
        this.cursor_y = cursor_y;
    }

    public  void setSize(int size) {
        this.size = size;
    }

    public  void setAim_staff(Staff aim_staff) {
        this.aim_staff = aim_staff;
    }

    public  Character getUser() {
        return user;
    }

    public  Object getTarget() {
        return target;
    }

    public  boolean isMenu_switch() {
        return menu_switch;
    }

    public  int getCursor_y() {
        return cursor_y;
    }

    public  int getsize() {
        return size;
    }

    public  Staff getAim_staff() {
        return aim_staff;
    }

    public  ArrayList<Staff> getStaff() {
        return staff;
    }

    public  void setStaff(ArrayList<Staff> staff) {
        this.staff = staff;
    }


    public void addStaff(Staff staff) {
        if(!this.staff.contains(staff)){
        this.staff.add(staff);}
    }

    public void clearList() {
        staff.clear();
    }

    public void refresh_Staff() {
        int i = 0;
        for (Staff staff : staff) {
            if (cursor_y == i) {
                aim_staff = staff;
            }
            i++;
        }
    }

    public void equip() {
        int i = 0;
        for (Staff s : staff) {
            if (cursor_y == i) {
                user.getBag().remove(s);
                staff.remove(s);
                user.getBag().add(0, s);
                staff.add(0, s);
                user.setEquiped_Weapon(s);
                cursor_y = 0;
                break;
            }
            i++;
        }
    }

    private void drawRight(Graphics g, String text, int x, int y) {
        textwidth = g.getFontMetrics().stringWidth(text);
        g.drawString(text, x - textwidth, y);
    }

    private void showInfo(Graphics g, Character c) {
        g.setFont(new Font("Dialog", 1, 20));
        g.setColor(Color.WHITE);
        g.drawImage(c.getCharacter_Image().getSubimage(0, 0, 32, 32), 200, 20, null);
        g.drawString(c.getName(), 240, 35);
        drawRight(g, Integer.toString(c.gethp()), 250, 55);
        g.drawString("/" + c.getHP(), 260, 55);
    }

    public void shift_CursorY(int val) {
        if (cursor_y + val >= staff.size()) {
            cursor_y = cursor_y + val - staff.size();
        }
        if (cursor_y + val < 0) {
            cursor_y = cursor_y + staff.size() + val;
        }
    }

    public void clean() {
        user = null;
        target = null;
        menu_switch = false;
        cursor_y = 0;
        size = 0;
        aim_staff = null;
        staff.clear();
    }
}
