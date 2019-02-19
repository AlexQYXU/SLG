package Display;

import Characters.Character;
import Characters.Type;
import Item.Item;
import Item.WeaponLevel;
import Item.Consumables;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Market_Interface extends JLayeredPane {
    String path = System.getProperty("user.dir") + "/src/main/resources/Icons";
    BufferedImage background;
    BufferedImage background_top;
    BufferedImage cursor;
    BufferedImage window;
    BufferedImage RankSword;
    BufferedImage RankLance;
    BufferedImage RankAxe;
    BufferedImage RankBow;
    BufferedImage RankWind;
    BufferedImage RankFire;
    BufferedImage RankThunder;
    BufferedImage RankStaff;
    BufferedImage balloon;
    BufferedImage coin;
    BufferedImage menu;
    BufferedImage infoBox;
    ArrayList<Character> team;
    int cursor_x, cursor_y;
    Character aim;
    int Y;
    Font font = new Font("Dialog", 1, 12);
    Font small = new Font("Dialog",1,10);
    int textwidth;
    boolean option;

    public Market_Interface() {
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
            balloon = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Menu/Balloon.png"));
            coin = ImageIO.read(new File(path + "/Coin.png"));
            menu = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Characters/MenuTileset.png"));
            infoBox = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Menu/infoBox.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(background, 0, 256, null);
        g.drawImage(window, 35, 315, null);
        g.drawImage(infoBox, 0, 472, null);
        int x = 0;
        int y = 0;

        g.setColor(Color.WHITE);
        for (Character c : team) {
            g.setFont(font);
            if (32 * (y - Y) >= 0) {
                g.drawImage(c.getCharacter_Image().getSubimage(0, 0, 32, 32), 40 + 90 * (x % 3), 32 * (y - Y) + 256 + 64, null);
                g.drawImage(balloon, 96 + 90 * (x % 3), 32 * (y - Y) + 256 + 44, null);
                if (c.getWant() != null) {
                    g.drawImage(c.getWant().getImage(), 101 + 90 * (x % 3), 32 * (y - Y) + 256 + 44, null);
                } else {
                    g.drawImage(coin, 101 + 90 * (x % 3), 32 * (y - Y) + 256 + 44, null);
                }
                g.drawString(c.getName(), 32 + 40 + 90 * (x % 3), 32 * (y - Y) + 15 + 256 + 64);
                g.setColor(Color.WHITE);
                if (cursor_x == x % 3 && cursor_y == y) {
                    aim = c;
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

                    g.drawImage(c.getProfile(), 0, 0, c.getProfile().getWidth(), c.getProfile().getHeight(), null);
                    g.drawImage(c.getCharacter_Image().getSubimage(0, 0, 32, 32), 96, 0, null);
                    g.drawString("HP", 96, 70);
                    drawRight(g, c.gethp() + "/" + c.getHP(), 156, 70);
                    g.drawString(c.getName(), 150, 20);
                    g.drawString(c.getJob().toString(), 250, 20);
                    drawRight(g, Integer.toString(c.getLv()), 136, 45);
                    drawRight(g, Integer.toString(c.getExp()), 166, 45);
                    drawRight(g, Integer.toString(c.getMove()), 156, 95);
                    drawRight(g, Integer.toString(c.getStr()), 50, 116);
                    drawRight(g, Integer.toString(c.getMag()), 50, 136);
                    drawRight(g, Integer.toString(c.getSkl()), 50, 156);
                    drawRight(g, Integer.toString(c.getSpd()), 50, 176);
                    drawRight(g, Integer.toString(c.getLuk()), 50, 196);
                    drawRight(g, Integer.toString(c.getDef()), 50, 216);
                    drawRight(g, Integer.toString(c.getRes()), 50, 236);
                    int n = 0;
                    for (Type type : c.getTypes()) {
                        g.drawString(type.toString(), 276 + 30 * n, 95);
                        n++;
                    }
                    if (c.getWeapon_ability() != null) {
                        int i = 0;
                        for (WeaponLevel lv : c.getWeapon_ability()) {
                            if (lv == WeaponLevel.NA) {
                                g.drawString("-", 110, 113 + 20 * i);
                            } else {
                                g.drawString(lv.toString(), 110, 113 + 20 * i);
                            }
                            i++;
                        }
                    }
                    if (!c.getBag().isEmpty()) {
                        int num = 0;
                        for (Item item : c.getBag()) {
                            if (item.isDropable()) {
                                g.setColor(Color.RED);
                            } else {
                                g.setColor(Color.WHITE);
                            }
                            g.drawImage(item.getImage(), 150, 96 + 20 * num, 20, 20, null);
                            g.drawString(item.getName(), 180, 116 + 20 * num);
                            if (item instanceof Consumables) {
                                drawRight(g, ((Consumables) item).getUses() + "/", 330, 116 + 20 * num);
                                drawRight(g, Integer.toString(((Consumables) item).getUSES()), 350, 116 + 20 * num);
                            }
                            num++;
                        }
                    }
                    g.setColor(Color.WHITE);
                    if (c.getEquiped_Weapon() == null) {
                        drawRight(g, "--", 266, 45);
                        drawRight(g, "--", 336, 45);
                        drawRight(g, "--", 266, 70);
                        drawRight(g, "--", 336, 70);
                        drawRight(g, "--", 266, 95);
                    } else {
                        drawRight(g, Integer.toString(c.getMight() + c.getEquiped_Weapon().getMight()), 266, 45);
                        drawRight(g, Integer.toString(c.getEquiped_Weapon().getCrit() + c.getSkl() / 2), 336, 45);
                        drawRight(g, Integer.toString(c.getEquiped_Weapon().getHit() + c.getSkl() * 2 + c.getLuk()), 266, 70);
                        int atk_spd = c.getSpd() - Math.max(c.getEquiped_Weapon().getWeight() - c.getStr(), 0);
                        drawRight(g, Integer.toString(atk_spd * 2 + c.getLuk()), 336, 70);
                        if (c.getEquiped_Weapon().getRange_1() == c.getEquiped_Weapon().getRange_2()) {
                            drawRight(g, Integer.toString(c.getEquiped_Weapon().getRange_1()), 266, 95);
                        } else {
                            drawRight(g, c.getEquiped_Weapon().getRange_1() + " - " + c.getEquiped_Weapon().getRange_2(), 266, 95);
                        }
                    }
                    if (c.getWant() != null) {
                        g.drawImage(c.getWant().getImage(), 0, 472, null);
                        g.drawString(c.getName() + " wants " + c.getWant().getName(), 32, 492);
                        g.setFont(small);
                        g.drawString("(Store what the mercenary wants into the Transporter)",0,507);
                        g.setFont(font);
                    } else {
                        g.drawImage(coin, 0, 472, null);
                        g.drawString(c.getName() + " wants 500G", 32, 492);
                    }
                }

                x++;
                y = x / 3;

            }
        }
        g.drawImage(cursor, 40 + 90 * cursor_x, 32 * cursor_y + 256 + 64, null);
        if (option) {
            g.drawImage(menu, 144, 266, null);
            drawCentre(g, "Employ", 176, 286);
        }

    }

    private void drawCentre(Graphics g, String text, int x, int y) {
        textwidth = g.getFontMetrics().stringWidth(text);
        g.drawString(text, x - textwidth / 2, y);
    }


    public boolean isOption() {
        return option;
    }

    public void setOption(boolean option) {
        this.option = option;
    }

    public Character getAim() {
        return aim;
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

    public void drawRight(Graphics g, String text, int x, int y) {
        textwidth = g.getFontMetrics().stringWidth(text);
        g.drawString(text, x - textwidth, y);
    }

    public void setTeam(ArrayList<Character> team) {
        this.team = team;
    }

    public void clean() {
        cursor_y = 0;
        cursor_x = 0;
        Y = 0;
        option = false;
    }
}
