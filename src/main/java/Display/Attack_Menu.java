package Display;

import Characters.Character;
import Item.Item;
import Item.Weapon;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Attack_Menu extends JLayeredPane {
    Character character_1;
    Character character_2;
    int cursor_y;
    int size;
    boolean showMenu;
    Weapon aim_weapon;
    ArrayList<Weapon> weapons = new ArrayList<>();
    int textwidth;
    protected BufferedImage arrowIcon_up;
    protected BufferedImage arrowIcon_down;
    protected BufferedImage weapon_triangle;
    BufferedImage ItemBox;
    BufferedImage cursor;
    BufferedImage Ability;
    BufferedImage Battle_Menu;

    private int hp_1;
    private int hp_2;
    private int damage_1;
    private int damage_2;
    private int hit_1;
    private int hit_2;
    private int crit_1;
    private int crit_2;
    private int turn_1;
    private int turn_2;

    public Attack_Menu() {
        try {
            arrowIcon_up = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Icons/arrowIcon_up.png"));
            arrowIcon_down = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Icons/arrowIcon_down.png"));
            weapon_triangle = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Icons/weapon_triangle.png"));
            ItemBox = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Menu/ExchangeMenu.png"));
            cursor = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Cursor/ItemMenu_Cursor_1.png"));
            Ability = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Menu/Ability_Menu.png"));
            Battle_Menu = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Menu/Battle_Menu.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Dialog", 0, 12));
        if (showMenu) {
            g.drawImage(ItemBox, 32, 60, null);
            g.drawImage(Ability, 172, 160, null);
            size = 0;

            for (Weapon weapon : weapons) {
                g.drawImage(weapon.getImage(), 32, 60 + 20 * size, null);
                g.drawString(weapon.getName(), 62, 75 + 20 * size);
                drawRight(g, weapon.getUses() + "/", 157, 75 + 20 * size);
                drawRight(g, Integer.toString(weapon.getUSES()), 172, 75 + 20 * size);
                if (weapon == character_1.getEquiped_Weapon()) {
                    g.setFont(new Font("Dialog", Font.BOLD, 8));
                    g.drawString("E", 47, 75 + 20 * size);
                    g.setFont(new Font("Dialog", 0, 12));
                }
                if (size == cursor_y) {
                    aim_weapon = weapon;
                    int atk;
                    if (aim_weapon.getMight() > 0) {
                        if (aim_weapon.isPhysical() == true) {
                            atk = aim_weapon.getMight() + character_1.getStr();
                        } else {
                            atk = aim_weapon.getMight() + character_1.getMag();
                        }
                        g.drawString("Atk  " + atk, 172, 180);
                    }
                }
                size++;
            }

            g.drawImage(cursor, 32, 60 + 20 * cursor_y, cursor.getWidth(), cursor.getHeight(), null);
        } else {
            g.drawImage(Battle_Menu, 32, 34, null);
            drawCentre(g, character_1.getName(), 79, 49);
            drawCentre(g, aim_weapon.getName(), 79, 69);
            g.drawImage(aim_weapon.getImage(), 34, 40, null);
            drawCentre(g, character_2.getName(), 79, 169);
            if (character_2.getEquiped_Weapon() != null) {
                drawCentre(g, character_2.getEquiped_Weapon().getName(), 79, 189);
                g.drawImage(character_2.getEquiped_Weapon().getImage(), 34, 160, null);
            }
            g.setColor(Color.GRAY);
            g.setFont(new Font("Dialog", 1, 12));
            drawCentre(g, "HP", 79, 89);
            drawRight(g, Integer.toString(hp_1), 119, 89);
            drawRight(g, Integer.toString(hp_2), 59, 89);
            drawCentre(g, "Atk", 79, 109);
            drawRight(g, Integer.toString(damage_1), 119, 109);
            drawRight(g, Integer.toString(damage_2), 59, 109);
            drawCentre(g, "Hit", 79, 129);
            drawRight(g, Integer.toString(hit_1), 119, 129);
            drawRight(g, Integer.toString(hit_2), 59, 129);
            drawCentre(g, "Crit", 79, 149);
            drawRight(g, Integer.toString(crit_1), 119, 149);
            drawRight(g, Integer.toString(crit_2), 59, 149);

            draw_ArrowIcon(g, character_1.first_Weapon(), character_2.first_Weapon());

            if (turn_1 == 2) {
                g.setColor(Color.WHITE);
                drawRight(g, "x 2", 139, 109);
                g.setColor(Color.GRAY);
            }
            if (turn_2 == 2) {
                g.setColor(Color.WHITE);
                drawRight(g, "2 x", 59, 109);
                g.setColor(Color.GRAY);
            }
            g.drawImage(weapon_triangle, 288, 160, null);

        }

    }

    public void drawCentre(Graphics g, String text, int x, int y) {
        textwidth = g.getFontMetrics().stringWidth(text);
        g.drawString(text, x - textwidth / 2, y);
    }

    public void drawRight(Graphics g, String text, int x, int y) {
        textwidth = g.getFontMetrics().stringWidth(text);
        g.drawString(text, x - textwidth, y);
    }

    public void Weapon_List() {
        weapons.clear();
        for (Item item : character_1.getBag()) {
            if (item instanceof Weapon && character_1.can_Equip(item)) {
                weapons.add((Weapon) item);
            }
        }
    }

    public void refresh_Weapon() {
        int i = 0;
        for (Weapon weapon : weapons) {
            if (cursor_y == i) {
                aim_weapon = weapon;
            }
            i++;
        }
    }


    public void equip() {
        int i = 0;
        for (Weapon w : weapons) {
            if (cursor_y == i) {
                character_1.getBag().remove(w);
                weapons.remove(w);
                character_1.getBag().add(0, w);
                weapons.add(0, w);
                character_1.setEquiped_Weapon(w);
                cursor_y = 0;
                break;
            }
            i++;
        }
    }


    public void draw_ArrowIcon(Graphics g, Weapon w1, Weapon w2) {
        BufferedImage image1 = null;
        BufferedImage image2 = null;
        if (w1 != null && w2 != null) {
            if (w1.isDominant(w2)) {
                image1 = arrowIcon_up;
                image2 = arrowIcon_down;
                String type_2 = "Item." + w2.getType();
                if (type_2 == w2.Priority_Type()) {
                    image1 = arrowIcon_down;
                    image2 = arrowIcon_up;
                }
            } else if (w2.isDominant(w1)) {
                image1 = arrowIcon_down;
                image2 = arrowIcon_up;
                String type_1 = "Item." + w1.getType();
                if (type_1 == w1.Priority_Type()) {
                    image1 = arrowIcon_up;
                    image2 = arrowIcon_down;
                }
            }
        }
        if (image1 != null) {
            g.drawImage(image1, 54, 50, null);
            g.drawImage(image2, 54, 170, null);
        }

    }

    public boolean isShowMenu() {
        return showMenu;
    }

    public void setShowMenu(boolean showMenu) {
        this.showMenu = showMenu;
    }

    public void setCursor_y(int cursor_y) {
        this.cursor_y = cursor_y;
    }

    public void setCharacter_1(Character character_1) {
        this.character_1 = character_1;
    }

    public void setCharacter_2(Character character_2) {
        this.character_2 = character_2;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setAim_weapon(Weapon aim_weapon) {
        this.aim_weapon = aim_weapon;
    }

    public void setWeapons(ArrayList<Weapon> weapons) {
        this.weapons = weapons;
    }

    public void setTextwidth(int textwidth) {
        this.textwidth = textwidth;
    }

    public Character getCharacter_1() {
        return character_1;
    }

    public Character getCharacter_2() {
        return character_2;
    }

    public int getCursor_y() {
        return cursor_y;
    }

    public int getsize() {
        return size;
    }

    public Weapon getAim_weapon() {
        return aim_weapon;
    }

    public ArrayList<Weapon> getWeapons() {
        return weapons;
    }

    public int getTextwidth() {
        return textwidth;
    }

    public void shift_CursorY(int val) {
        cursor_y += val;
    }

    public void setHp_1(int hp_1) {
        this.hp_1 = hp_1;
    }

    public void setHp_2(int hp_2) {
        this.hp_2 = hp_2;
    }

    public void setDamage_1(int damage_1) {
        this.damage_1 = damage_1;
    }

    public void setDamage_2(int damage_2) {
        this.damage_2 = damage_2;
    }

    public void setHit_1(int hit_1) {
        this.hit_1 = hit_1;
    }

    public void setHit_2(int hit_2) {
        this.hit_2 = hit_2;
    }

    public void setCrit_1(int crit_1) {
        this.crit_1 = crit_1;
    }

    public void setCrit_2(int crit_2) {
        this.crit_2 = crit_2;
    }

    public void setTurn_1(int turn_1) {
        this.turn_1 = turn_1;
    }

    public void setTurn_2(int turn_2) {
        this.turn_2 = turn_2;
    }
}
