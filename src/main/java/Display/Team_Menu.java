package Display;

import Characters.Character;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Team_Menu extends JLayeredPane {
    BufferedImage data_frame;
    BufferedImage cursor;
    ArrayList<Character> PLAYER;
    int cursor_x, cursor_y;
    int textwidth;
    Font font = new Font("Dialog", 1, 12);
    String[] head;
    int top;

    public Team_Menu() {
        try {
            String[] text = {"Importance Data", "Fighting Skill", "Equipment Data"};
            head = text;
            data_frame = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Menu/Data_Frame.png"));
            cursor = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Cursor/Ultra_Cursor.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawImage(data_frame, 0, 0, null);
        drawCentre(g, head[cursor_x], 93, 30);
        drawCentre(g, cursor_x + 1 + "/3", 310, 30);
        g.drawString("Name", 52, 70);
        int count = 0;
        Character pl;
        for (Character pc : PLAYER) {
            if (count < 5) {
                g.drawImage(pc.getCharacter_Image().getSubimage(0, 0, 32, 32), 10, 80 + 32 * count, null);
                g.drawString(pc.getName(), 52, 100 + 32 * count);
                count++;
            }
        }
        int num = 0;
        switch (cursor_x) {
            case 0:
                g.drawString("Job", 120, 70);
                g.drawString("LV", 200, 70);
                g.drawString("EX", 240, 70);
                g.drawString("HP", 300, 70);


                for (int i = top; i < top + 5; i++) {
                    if (PLAYER.size() > i) {
                        pl = PLAYER.get(i);
                        g.drawString(pl.getJob().toString(), 120, 100 + 32 * num);
                        g.drawString(Integer.toString(pl.getLv()), 200, 100 + 32 * num);
                        g.drawString(Integer.toString(pl.getExp()), 240, 100 + 32 * num);
                        g.drawString(Integer.toString(pl.gethp()), 280, 100 + 32 * num);
                        g.drawString("/" + pl.getHP(), 300, 100 + 32 * num);
                        num++;
                    }
                }
                break;
            case 1:
                g.drawString("Str", 120, 70);
                g.drawString("Mag", 150, 70);
                g.drawString("Skl", 180, 70);
                g.drawString("Spd", 210, 70);
                g.drawString("Luk", 240, 70);
                g.drawString("Def", 270, 70);
                g.drawString("Res", 300, 70);
                for (int i = top; i < top + 5; i++) {
                    if (PLAYER.size() > i) {
                        pl = PLAYER.get(i);
                        g.drawString(Integer.toString(pl.getStr()), 120, 100 + 32 * num);
                        g.drawString(Integer.toString(pl.getMag()), 150, 100 + 32 * num);
                        g.drawString(Integer.toString(pl.getSkl()), 180, 100 + 32 * num);
                        g.drawString(Integer.toString(pl.getSpd()), 210, 100 + 32 * num);
                        g.drawString(Integer.toString(pl.getLuk()), 240, 100 + 32 * num);
                        g.drawString(Integer.toString(pl.getDef()), 270, 100 + 32 * num);
                        g.drawString(Integer.toString(pl.getRes()), 300, 100 + 32 * num);
                        num++;
                    }
                }
                break;
            case 2:
                g.drawString("Weapon", 120, 70);
                g.drawString("Atk", 200, 70);
                g.drawString("Hit", 250, 70);
                g.drawString("Avo", 300, 70);
                for (int i = top; i < top + 5; i++) {
                    if (PLAYER.size() > i) {
                        pl = PLAYER.get(i);
                        if (pl.getEquiped_Weapon() != null) {
                            g.drawImage(pl.getEquiped_Weapon().getImage(), 120, 80 + 32 * num, null);
                            g.drawString(Integer.toString(pl.getAtk()), 200, 100 + 32 * num);
                            g.drawString(Integer.toString(pl.getHit()), 250, 100 + 32 * num);
                            g.drawString(Integer.toString(pl.getAvo()), 300, 100 + 32 * num);
                        } else {
                            g.drawString("---", 120, 100 + 32 * num);
                            g.drawString("--",200, 100 + 32 * num);
                            g.drawString("--",250, 100 + 32 * num);
                            g.drawString(Integer.toString(pl.getAvo()), 300, 100 + 32 * num);
                        }
                        num++;
                    }
                }
                break;
            default:
                break;
        }
        g.drawImage(cursor, 10, 97 + 32 * cursor_y, null);
        System.out.println(cursor_x + ":" + cursor_y);

    }

    private void drawRight(Graphics g, String text, int x, int y) {
        textwidth = g.getFontMetrics().stringWidth(text);
        g.drawString(text, x - textwidth, y);
    }

    private void drawCentre(Graphics g, String text, int x, int y) {
        textwidth = g.getFontMetrics().stringWidth(text);
        g.drawString(text, x - textwidth / 2, y);
    }

    public void setPLAYER(ArrayList<Character> PLAYER) {
        this.PLAYER = PLAYER;
    }

    public void left() {
        if (cursor_x == 0) {
            cursor_x = 2;
        } else {
            cursor_x--;
        }
    }

    public void right() {
        if (cursor_x == 2) {
            cursor_x = 0;
        } else {
            cursor_x++;
        }
    }

    public void up() {
        if (cursor_y == 0) {
            if (top > 0) {
                top--;
            } else {
                cursor_y = Math.min(4, PLAYER.size() - 1);
                top = Math.max(0, Math.max(4, PLAYER.size() - 1) - 4);
            }
        } else {
            cursor_y--;
        }
    }

    public void down() {
        if (cursor_y + top == PLAYER.size() - 1) {
            top = 0;
            cursor_y = 0;
        } else {
            if (cursor_y == 4) {
                top++;
            } else {
                cursor_y++;
            }
        }
    }

}
