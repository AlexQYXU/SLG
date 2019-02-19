package Display;

import Characters.Character;
import Characters.Type;
import Item.Item;
import Item.WeaponLevel;
import Item.Consumables;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Item_Interface extends JLayeredPane {
    String path = System.getProperty("user.dir") + "/src/main/resources/Icons";
    ArrayList<Character> team;
    BufferedImage background;
    BufferedImage cursor;
    BufferedImage subcursor;
    BufferedImage window;
    BufferedImage background_top;
    BufferedImage RankSword;
    BufferedImage RankLance;
    BufferedImage RankAxe;
    BufferedImage RankBow;
    BufferedImage RankWind;
    BufferedImage RankFire;
    BufferedImage RankThunder;
    BufferedImage RankStaff;
    BufferedImage menu;
    int cursor_x, cursor_y;
    int sub_y;
    int Y;
    int textwidth;
    Character aim;
    Character c1, c2;


    boolean in_Menu;
    boolean in_sub;


    Font font = new Font("Dialog", 1, 12);

    public Item_Interface() {
        try {
            background = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Menu/Fog.png"));
            cursor = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Cursor/Character_Cursor.png"));
            window = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Menu/Character_Window.png"));
            background_top = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Menu/background_ver2.png"));
            RankSword = ImageIO.read(new File(path + "/GCNRankSword.png"));
            RankLance = ImageIO.read(new File(path + "/GCNRankLance.png"));
            RankAxe = ImageIO.read(new File(path + "/GCNRankAxe.png"));
            RankBow = ImageIO.read(new File(path + "/GCNRankBow.png"));
            RankWind = ImageIO.read(new File(path + "/GCNRankWind.png"));
            RankFire = ImageIO.read(new File(path + "/GCNRankFire.png"));
            RankThunder = ImageIO.read(new File(path + "/GCNRankThunder.png"));
            RankStaff = ImageIO.read(new File(path + "/GCNRankStaff.png"));
            menu = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Characters/MenuTileset.png"));
            subcursor = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Cursor/MenuCursor.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paint(Graphics g) {

        g.drawImage(background, 0, 256, null);
        g.setColor(Color.WHITE);
        if (!in_sub) {

            g.drawImage(window, 35, 315, null);
            int x = 0;
            int y = 0;
            for (Character c : team) {
                g.setFont(font);
                if (32 * (y - Y) >= 0) {
                    g.drawImage(c.getCharacter_Image().getSubimage(0, 0, 32, 32), 40 + 90 * (x % 3), 32 * (y - Y) + 256 + 64, null);
                    g.drawString(c.getName(), 32 + 40 + 90 * (x % 3), 32 * (y - Y) + 15 + 256 + 64);

                    g.setColor(Color.WHITE);
                    if (cursor_x == x % 3 && cursor_y == y) {
                        aim = c;
                    }
                    x++;
                    y = x / 3;

                }
            }
            g.drawImage(cursor, 40 + 90 * cursor_x, 32 * cursor_y + 256 + 64, null);
            if (in_Menu) {
                g.drawImage(menu, 250, 300, null);
                g.drawImage(menu, 250, 332, null);
                g.drawImage(menu, 250, 364, null);
                g.drawImage(menu, 250, 396, null);
                drawCentre(g, "Exchange", 283, 315);
                drawCentre(g, "Item", 283, 347);
                drawCentre(g, "Transporter", 283, 379);
                drawCentre(g, "Shop", 283, 411);
                g.drawImage(subcursor, 250, 300 + 32 * sub_y, null);
            }
        }

        if (c1 != null) {
            g.drawImage(cursor, 40 + 90 * (team.indexOf(c1) % 3), 32 * (team.indexOf(c1) / 3) + 256 + 64, null);
        }
        g.drawImage(background_top, 0, 0, background.getWidth(), background.getHeight(), null);
        g.setFont(new Font("Arial", Font.BOLD, 15));

        g.setColor(Color.BLACK);
        g.drawString("Lv", 96, 45);
        g.drawString("E", 136, 45);
        g.drawString("Move", 96, 95);
        g.drawString("HP", 96, 70);
        g.drawString("ATK", 186, 45);
        g.drawString("CRT", 276, 45);
        g.drawString("HIT", 186, 70);
        g.drawString("AVO", 276, 70);
        g.drawString("Range", 186, 95);

        g.setColor(Color.ORANGE);
        g.drawString("Str", 0, 116);
        g.drawString("Mag", 0, 136);
        g.drawString("Skl", 0, 156);
        g.drawString("Spd", 0, 176);
        g.drawString("Luk", 0, 196);
        g.drawString("Def", 0, 216);
        g.drawString("Res", 0, 236);
        g.setColor(Color.WHITE);

        g.drawImage(RankSword, 80, 96, null);
        g.drawImage(RankLance, 80, 116, null);
        g.drawImage(RankAxe, 80, 136, null);
        g.drawImage(RankBow, 80, 156, null);
        g.drawImage(RankWind, 80, 176, null);
        g.drawImage(RankFire, 80, 196, null);
        g.drawImage(RankThunder, 80, 216, null);
        g.drawImage(RankStaff, 80, 236, null);

        g.drawImage(aim.getProfile(), 0, 0, aim.getProfile().getWidth(), aim.getProfile().getHeight(), null);
        g.drawImage(aim.getCharacter_Image().getSubimage(0, 0, 32, 32), 96, 0, null);
        g.drawString("HP", 96, 70);
        drawRight(g, aim.gethp() + "/" + aim.getHP(), 156, 70);
        g.drawString(aim.getName(), 150, 20);
        g.drawString(aim.getJob().toString(), 250, 20);
        drawRight(g, Integer.toString(aim.getLv()), 136, 45);
        drawRight(g, Integer.toString(aim.getExp()), 166, 45);
        drawRight(g, Integer.toString(aim.getMove()), 156, 95);
        drawRight(g, Integer.toString(aim.getStr()), 50, 116);
        drawRight(g, Integer.toString(aim.getMag()), 50, 136);
        drawRight(g, Integer.toString(aim.getSkl()), 50, 156);
        drawRight(g, Integer.toString(aim.getSpd()), 50, 176);
        drawRight(g, Integer.toString(aim.getLuk()), 50, 196);
        drawRight(g, Integer.toString(aim.getDef()), 50, 216);
        drawRight(g, Integer.toString(aim.getRes()), 50, 236);
        int n = 0;
        for (Type type : aim.getTypes()) {
            g.drawString(type.toString(), 276 + 30 * n, 95);
            n++;
        }
        if (aim.getWeapon_ability() != null) {
            int i = 0;
            for (WeaponLevel lv : aim.getWeapon_ability()) {
                if (lv == WeaponLevel.NA) {
                    g.drawString("-", 110, 113 + 20 * i);
                } else {
                    g.drawString(lv.toString(), 110, 113 + 20 * i);
                }
                i++;
            }
        }
        if (!aim.getBag().isEmpty()) {
            int num = 0;
            for (Item item : aim.getBag()) {
                if (item.isDropable()) {
                    g.setColor(Color.RED);
                } else {
                    g.setColor(Color.WHITE);
                }
                g.drawImage(item.getImage(), 150, 96 + 20 * num, null);
                g.drawString(item.getName(), 180, 116 + 20 * num);
                if (item instanceof Consumables) {
                    drawRight(g, ((Consumables) item).getUses() + "/", 330, 116 + 20 * num);
                    drawRight(g, Integer.toString(((Consumables) item).getUSES()), 350, 116 + 20 * num);
                }
                num++;
            }
        }
        g.setColor(Color.WHITE);
        if (aim.getEquiped_Weapon() == null) {
            drawRight(g, "--", 266, 45);
            drawRight(g, "--", 336, 45);
            drawRight(g, "--", 266, 70);
            drawRight(g, "--", 336, 70);
            drawRight(g, "--", 266, 95);
        } else {
            drawRight(g, Integer.toString(aim.getMight() + aim.getEquiped_Weapon().getMight()), 266, 45);
            drawRight(g, Integer.toString(aim.getEquiped_Weapon().getCrit() + aim.getSkl() / 2), 336, 45);
            drawRight(g, Integer.toString(aim.getEquiped_Weapon().getHit() + aim.getSkl() * 2 + aim.getLuk()), 266, 70);
            int atk_spd = aim.getSpd() - Math.max(aim.getEquiped_Weapon().getWeight() - aim.getStr(), 0);
            drawRight(g, Integer.toString(atk_spd * 2 + aim.getLuk()), 336, 70);
            if (aim.getEquiped_Weapon().getRange_1() == aim.getEquiped_Weapon().getRange_2()) {
                drawRight(g, Integer.toString(aim.getEquiped_Weapon().getRange_1()), 266, 95);
            } else {
                drawRight(g, aim.getEquiped_Weapon().getRange_1() + " - " + aim.getEquiped_Weapon().getRange_2(), 266, 95);
            }
        }
    }


    public void shift_CursorX(int val) {
        if (cursor_x + val > 2) {
            cursor_x = cursor_x + val - 3;
        } else if (cursor_x + val < 0) {
            cursor_x = cursor_x + val + 3;
        } else {
            cursor_x += val;
        }
    }

    public void setCursor_y(int cursor_y) {
        this.cursor_y = cursor_y;
    }

    public void setCursor_x(int cursor_x) {
        this.cursor_x = cursor_x;
    }

    public void resetCursor() {
        cursor_x = team.indexOf(c1) % 3;
        cursor_y = team.indexOf(c1) / 3;
    }

    public void shift_CursorY(int val) {
        if (cursor_y + val > 2) {
            if (Y < team.size() / 3 - 3) {
                Y = Y + cursor_y + val - 2;
            } else {
                if (cursor_y + val == 3) {
                    cursor_y += val;
                } else {
                    Y = 0;
                    cursor_y = 0;
                }
            }
        } else if (cursor_y + val < 1) {
            if (Y > 0) {
                Y = Y + cursor_y + val - 1;
            } else {
                if (cursor_y + val == 0) {
                    cursor_y += val;
                } else {
                    Y = Math.max(team.size() / 3 - 3, 0);
                    cursor_y = 3;
                }
            }
        } else {
            cursor_y += val;
        }
    }

    public boolean isIn_sub() {
        return in_sub;
    }

    public void setIn_sub(boolean in_sub) {
        this.in_sub = in_sub;
    }

    public void sub_ShiftUP() {
        if (sub_y > 0) {
            sub_y--;
        } else {
            sub_y = 3;
        }
    }

    public void sub_ShiftDown() {
        if (sub_y < 3) {
            sub_y++;
        } else {
            sub_y = 0;
        }
    }

    public void setTeam(ArrayList<Character> team) {
        this.team = team;
    }

    public void drawRight(Graphics g, String text, int x, int y) {
        textwidth = g.getFontMetrics().stringWidth(text);
        g.drawString(text, x - textwidth, y);
    }

    public void drawCentre(Graphics g, String text, int x, int y) {
        textwidth = g.getFontMetrics().stringWidth(text);
        g.drawString(text, x - textwidth / 2, y);
    }

    public Character getC1() {
        return c1;
    }

    public Character getC2() {
        return c2;
    }

    public void setC1(Character c1) {
        this.c1 = c1;
    }

    public void setC2(Character c2) {
        this.c2 = c2;
    }

    public int getSub_y() {
        return sub_y;
    }

    public Character getAim() {
        return aim;
    }

    public void setIn_Menu(boolean in_Menu) {
        this.in_Menu = in_Menu;
    }

    public boolean isIn_Menu() {
        return in_Menu;
    }

    public void setSub_y(int sub_y) {
        this.sub_y = sub_y;
    }

    public void clean() {
        cursor_y = 0;
        cursor_x = 0;
        c1 = null;
        c2 = null;
        aim = null;
        Y = 0;
    }
}
