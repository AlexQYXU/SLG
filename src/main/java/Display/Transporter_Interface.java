package Display;

import Characters.Character;
import Characters.Transporter;
import Item.*;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Transporter_Interface extends JLayeredPane {
    Transporter transporter;
    Character user;
    String path = System.getProperty("user.dir") + "/src/main/resources/Icons";
    BufferedImage background;
    BufferedImage cursor;
    BufferedImage background_top;
    BufferedImage RankSword;
    BufferedImage RankLance;
    BufferedImage RankAxe;
    BufferedImage RankBow;
    BufferedImage RankWind;
    BufferedImage RankFire;
    BufferedImage RankThunder;
    BufferedImage RankStaff;
    BufferedImage shadow;
    BufferedImage Item;

    BufferedImage ItemBox;
    BufferedImage TransporterBox;
    BufferedImage Menu;
    BufferedImage item_cursor;
    BufferedImage InfoBox;
    int z_mark;
    int cursor_x, cursor_y;
    int top;
    Item aim;

    int textwidth;

    public Transporter_Interface() {
        try {
            background = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Menu/Fog.png"));
            cursor = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Cursor/MenuCursor.png"));
            background_top = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Menu/background_ver2.png"));
            RankSword = ImageIO.read(new File(path + "/GCNRankSword.png"));
            RankLance = ImageIO.read(new File(path + "/GCNRankLance.png"));
            RankAxe = ImageIO.read(new File(path + "/GCNRankAxe.png"));
            RankBow = ImageIO.read(new File(path + "/GCNRankBow.png"));
            RankWind = ImageIO.read(new File(path + "/GCNRankWind.png"));
            RankFire = ImageIO.read(new File(path + "/GCNRankFire.png"));
            RankThunder = ImageIO.read(new File(path + "/GCNRankThunder.png"));
            RankStaff = ImageIO.read(new File(path + "/GCNRankStaff.png"));
            shadow = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Cursor/shadow.png"));
            Item = ImageIO.read(new File(path + "/Vulnerary.png"));
            ItemBox = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Menu/ExchangeMenu.png"));
            Menu = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Characters/MenuTileset.png"));
            InfoBox = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Menu/infoBox.png"));
            TransporterBox = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Menu/Transporter.png"));
            item_cursor = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Cursor/ItemMenu_Cursor_1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Dialog", 1, 12));
        g.drawImage(background, 0, 256, null);
        g.drawImage(background_top, 0, 0, null);
        g.drawImage(Menu, 32, 244 + 32, null);
        g.drawImage(Menu, 32, 244 + 64, null);
        if (user.getBag().isEmpty()) {
            g.setColor(Color.GRAY);
        }
        drawCentre(g, "寄存", 64, 296);
        g.setColor(Color.WHITE);
        if (user.getBag().size() == 5) {
            g.setColor(Color.GRAY);
        }
        g.setColor(Color.WHITE);
        drawCentre(g, "取出", 64, 328);

        showInfo(g, user, 10, 340);
        show_Transporter(g, 160, 288);
        g.drawImage(InfoBox, 0, 467, null);
        switch (z_mark) {
            case 0:
                g.drawImage(cursor, 32, 276 + 32 * cursor_y, null);
                break;
            case 1:
                g.drawImage(item_cursor, 10, 360 + 20 * cursor_y, null);
                break;
            case 2:
                g.drawImage(item_cursor, 182, 338 + 20 * cursor_y, null);
                break;
            default:
                break;
        }
        if (aim != null) {
            if (aim.getDescription() != null) {
                g.drawString(aim.getDescription(), 10, 512 - 30);
            }
            System.out.println(aim.getName());
        }
    }

    public void setTransporter(Transporter transporter) {
        this.transporter = transporter;
    }

    public void setUser(Character user) {
        this.user = user;
    }

    private void showInfo(Graphics g, Character c, int x, int y) {

        g.drawImage(c.getCharacter_Image().getSubimage(0, 0, 32, 32), 10 + x, y, 32, 32, null);
        g.drawString(c.getName(), 42 + x, 18 + y);
        g.drawImage(ItemBox, x, 20 + y, null);
        if (!c.getBag().isEmpty()) {
            int num = 0;
            for (Item item : c.getBag()) {
                if (item.isDropable()) {
                    g.setColor(Color.RED);
                } else {
                    g.setColor(Color.WHITE);
                }
                g.drawImage(item.getImage(), x, y + 15 + 20 * num, null);
                g.drawString(item.getName(), 20 + x, y + 15 + 20 * (num + 1));
                if (item instanceof Consumables) {
                    drawRight(g, ((Consumables) item).getUses() + "/", 120 + x, y + 15 + 20 * (num + 1));
                    drawRight(g, Integer.toString(((Consumables) item).getUSES()), 140 + x, y + 15 + 20 * (num + 1));
                }
                num++;
            }
        }
    }

    private void show_Transporter(Graphics g, int x, int y) {
        g.drawImage(RankSword, x, y, null);
        g.drawImage(RankLance, x + 20, y, null);
        g.drawImage(RankAxe, x + 40, y, null);
        g.drawImage(RankBow, x + 60, y, null);
        g.drawImage(RankWind, x + 80, y, null);
        g.drawImage(RankFire, x + 100, y, null);
        g.drawImage(RankThunder, x + 120, y, null);
        g.drawImage(RankStaff, x + 140, y, null);
        g.drawImage(Item, x + 160, y, null);
        for (int i = 0; i < 9; i++) {
            if (i != cursor_x) {
                g.drawImage(shadow, x + 20 * i, y, null);
            }
        }
        g.drawImage(TransporterBox, x + 20, 50 + y, null);
        for (int i = 0; i < 6; i++) {
            if (transporter.getList(cursor_x).size() > top + i) {
                g.drawString(Integer.toString(i+top), x,65 + y + 20 * i);
                drawItem(g, transporter.getList(cursor_x).get(top + i), x + 20, 50 + y + 20 * i);
            }
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

    public void setZ_mark(int z_mark) {
        this.z_mark = z_mark;
    }

    public int getZ_mark() {
        return z_mark;
    }

    public void setCursor_y(int cursor_y) {
        this.cursor_y = cursor_y;
    }

    public int getCursor_y() {
        return cursor_y;
    }

    public void ItemBox_Up() {
        if (cursor_y > 0) {
            cursor_y--;
        } else {
            cursor_y = user.getBag().size() - 1;
        }
    }

    public void ItemBox_Down() {
        if (cursor_y < user.getBag().size() - 1) {
            cursor_y++;
        } else {
            cursor_y = 0;
        }
    }

    public void TransporterBox_Up() {
        if (cursor_y > 0) {
            cursor_y--;
        } else {
            if (top > 0) {
                top--;
            } else {
                top = Math.max(0,transporter.getList(cursor_x).size() - 6);
                cursor_y = Math.max(Math.min(transporter.getList(cursor_x).size() - 1, 5), 0);
            }

        }
    }
    public void TransporterBox_Down() {
        if (cursor_y < Math.min(transporter.getList(cursor_x).size() - 1, 5)) {
            cursor_y++;
        } else {
            if (top < transporter.getList(cursor_x).size() - 6) {
                top++;
            } else {
                top = 0;
                cursor_y = 0;
            }

        }
    }
    public void TransporterBox_Left() {
        if (cursor_x > 0) {
            cursor_x--;
        } else {
            cursor_x = 8;
        }
    }

    public void TransporterBox_Right() {
        if (cursor_x < 8) {
            cursor_x++;
        } else {
            cursor_x = 0;
        }
    }



    public void store() {
        user.getBag().remove(aim);
        transporter.store(aim);
        if (cursor_y > user.getBag().size() - 1) {
            cursor_y = Math.max(0, user.getBag().size() - 1);
        }
        System.out.println("store item. cursor_y now is " + cursor_y);
    }

    public void take_out() {
        if (user.getBag().size() < 5) {
            user.gainItem(aim);
            transporter.getList(cursor_x).remove(aim);
            if (cursor_y > transporter.getList(cursor_x).size() - 1) {
                cursor_y = Math.max(0, transporter.getList(cursor_x).size() - 1);
            }
        }
    }

    public void refreshAim() {
        if (z_mark == 1) {
            if (!user.getBag().isEmpty())
                aim = user.getBag().get(cursor_y);
        } else {
            aim = transporter.getItem(cursor_x, cursor_y + top);
        }
    }

    public void drawItem(Graphics g, Item item, int x, int y) {
        g.drawImage(item.getImage(), x, y, 20,20,null);
        g.drawString(item.getName(), x + 20, y + 15);
        if (item instanceof Consumables) {
            drawRight(g, ((Consumables) item).getUses() + "/", x + 120, y + 15);
            drawRight(g, Integer.toString(((Consumables) item).getUSES()), x + 140, y + 15);
            System.out.println(x + 120);
        }
    }

    public void clean() {
        aim = null;
        cursor_y = 0;
        cursor_x = 0;
        z_mark = 0;
    }
}
