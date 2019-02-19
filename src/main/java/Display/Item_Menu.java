package Display;

import Characters.Character;
import Characters.Type;
import Item.Equipable;
import Item.*;
import Item.Weapon;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Item_Menu extends JLayeredPane {
    Character c;
    public Item aim;
    int cursor_y = 0;
    int subcursor_y = 0;
    boolean equip = false;
    boolean use = false;
    boolean drop = false;
    int item_num;
    int textwidth;
    BufferedImage background;
    BufferedImage ItemMenu_sub;
    BufferedImage cursor;
    BufferedImage subcursor;
    BufferedImage arrowIcon_up;
    BufferedImage arrowIcon_down;


    public Item_Menu() {
        try {
            background = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Menu/ItemMenu.png"));
            ItemMenu_sub = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Menu/ItemMenu_sub.png"));
            cursor = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Cursor/ItemMenu_Cursor_1.png"));
            subcursor = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Cursor/ItemMenu_Cursor_2.png"));
            arrowIcon_up = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Icons/arrowIcon_up.png"));
            arrowIcon_down = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Icons/arrowIcon_down.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setAim(Item i) {
        this.aim = i;
    }

    public void setC(Character c) {
        this.c = c;
    }

    @Override
    public void paint(Graphics g) {

        g.setColor(Color.WHITE);
        g.drawImage(background, 0, 0, background.getWidth(), background.getHeight(), null);
        int i = 0;
        for (Item item : c.getBag()) {
            g.drawImage(item.getImage(), 0, 20 * i, 20, 20, null);
            g.drawString(item.getName(), 30, 15 + 20 * i);

            if (item == c.getEquiped_Weapon()) {
                g.setFont(new Font("Dialog", Font.BOLD, 8));
                g.drawString("E", 15, 15 + 20 * i);
                g.setFont(new Font("Dialog", 0, 12));
            }

            if (item instanceof Consumables) {
                drawRight(g, ((Consumables) item).getUses() + "/", 125, 15 + 20 * i);
                drawRight(g, Integer.toString(((Consumables) item).getUSES()), 140, 15 + 20 * i);
            }
            i++;
        }
        if (aim != null) {
            g.drawImage(cursor, 0, 20 * cursor_y, cursor.getWidth(), cursor.getHeight(), null);
            if (aim instanceof Weapon) {

                g.drawString(((Weapon) aim).getType().toString(), 0, 180);
                g.drawString(((Weapon) aim).getLevel().toString(), 40, 180);

                if (aim instanceof Staff) {
                    g.drawString(aim.getDescription(), 0, 175);
                } else {
                    g.drawString("Might  " + ((Weapon) aim).getMight(), 50, 180);
                    g.drawString("Hit  " + ((Weapon) aim).getHit(), 100, 180);
                    g.drawString("Crit  " + ((Weapon) aim).getCrit(), 150, 180);
                    g.drawString("Range  " + ((Weapon) aim).getRange_1() + "-" + ((Weapon) aim).getRange_2(), 190, 180);
                    g.drawString("Weight " + (aim).getWeight(), 0, 200);

                    if (((Weapon) aim).getSlayer() != null) {
                        g.setColor(Color.GREEN);
                        int n = 0;
                        for (Type type : ((Weapon) aim).getSlayer()) {
                            g.drawString(type.toString(), 70 + n * 50, 200);
                            n++;
                        }
                        g.setColor(Color.WHITE);
                    }
                }

            } else if (aim instanceof HealingItem) {
                g.drawString(aim.getName(), 0, 180);
                g.drawString("restore " + ((HealingItem) aim).getVal() + " HP.", 20, 200);
            }
        }

        int j = 0;
        if (equip == true) {
            String str;
            if (c.getEquiped_Weapon() != aim) {
                str = "Equip";
            } else {
                str = "Unequip";
            }
            g.drawImage(ItemMenu_sub, 120, 20 * j, ItemMenu_sub.getWidth(), ItemMenu_sub.getHeight(), null);
            drawCentre(g, str, 145, 15 + 20 * j);
            j++;
        }
        if (use == true) {
            g.drawImage(ItemMenu_sub, 120, 20 * j, ItemMenu_sub.getWidth(), ItemMenu_sub.getHeight(), null);
            drawCentre(g, "Use", 145, 15 + 20 * j);
            j++;
        }
        if (drop == true) {
            g.drawImage(ItemMenu_sub, 120, 20 * j, ItemMenu_sub.getWidth(), ItemMenu_sub.getHeight(), null);
            drawCentre(g, "Drop", 145, 15 + 20 * j);
            g.drawImage(subcursor, 120, 20 * subcursor_y, subcursor.getWidth(), subcursor.getHeight(), null);
        }

    }

    public void subMenu(boolean b) {
        subcursor_y = 0;
        if (b == true) {
            if (this.aim.isEquipable() && this.c.can_Equip(this.aim)) {
                equip = true;
            }
            if (this.aim.isUsable()) {
                use = true;
            }
            if (this.aim != null) drop = true;
        } else {
            equip = false;
            use = false;
            drop = false;
        }
    }

    public void useItem() {
        int[] row = new int[3];
        int i = 0;
        if (equip) {
            row[i] = 1;
            i++;
        }
        if (use) {
            row[i] = 2;
            i++;
        }
        if (drop) {
            row[i] = 3;
        }
        switch (row[subcursor_y]) {
            case 1:
                if (aim instanceof Equipable) {
                    ((Equipable) aim).equip(c);
                    cursor_y = 0;
                }
                break;
            case 2:
                if (aim instanceof Usable) {
                    ((Usable) aim).use(c);
                }
                break;
            case 3:
                c.getBag().remove(aim);
                if (cursor_y > 0) {
                    cursor_y--;
                    aim = c.getBag().get(cursor_y);
                }
                break;
        }
    }


    public void shift_CursorY(int val) {
        if (cursor_y + val > c.getBag().size() - 1) {
            cursor_y = cursor_y + val - c.getBag().size();
        } else if (cursor_y + val < 0) {
            cursor_y = cursor_y + val + c.getBag().size();
        } else {
            cursor_y += val;
        }
    }

    public void drawCentre(Graphics g, String text, int x, int y) {
        textwidth = g.getFontMetrics().stringWidth(text);
        g.drawString(text, x - textwidth / 2, y);
    }

    private void drawRight(Graphics g, String text, int x, int y) {
        textwidth = g.getFontMetrics().stringWidth(text);
        g.drawString(text, x - textwidth, y);
    }

    public void shift_SubCursorY(int val) {
        subcursor_y += val;
    }

    public void setCursor_y(int cursor_y) {
        this.cursor_y = cursor_y;
    }

    public void setSubcursor_y(int subcursor_y) {
        this.subcursor_y = subcursor_y;
    }

    public void setEquip(boolean equip) {
        this.equip = equip;
    }

    public void setUse(boolean use) {
        this.use = use;
    }

    public void setDrop(boolean drop) {
        this.drop = drop;
    }

    public Character getC() {
        return c;
    }

    public Item getAim() {
        return aim;
    }

    public int getCursor_y() {
        return cursor_y;
    }

    public int getSubcursor_y() {
        return subcursor_y;
    }

    public boolean isEquip() {
        return equip;
    }

    public boolean isUse() {
        return use;
    }

    public boolean isDrop() {
        return drop;
    }
}
