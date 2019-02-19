package Display;

import Characters.Character;
import Characters.Shop;
import Characters.Transporter;
import Item.Item;
import Item.Consumables;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Shop_Interface extends JLayeredPane {
    BufferedImage ShopBox;
    BufferedImage long_cursor;
    BufferedImage background;
    BufferedImage infoBox;
    BufferedImage menu;
    BufferedImage short_cursor;
    Shop shop;
    ArrayList<Item> list;
    Transporter transporter;
    Character c;
    Item aim;
    int asset;
    int top;
    int cursor_y;
    int sub_y;
    int textwidth;
    int z_mark;
    boolean buy;
    boolean sale;

    public Shop_Interface() {
        try {
            background = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Menu/Fog.png"));
            ShopBox = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Menu/ShopBox.png"));
            long_cursor = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Cursor/LongCursor.png"));
            infoBox = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Menu/infoBox.png"));
            menu = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Characters/MenuTileset.png"));
            short_cursor = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Cursor/MenuCursor.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(background, 0, 256, null);
        System.out.println("z_mark: " + z_mark);
        switch (z_mark) {
            case 0:
                g.setFont(new Font("Dialog", 1, 15));
                g.setColor(Color.WHITE);
                g.drawImage(menu, 144, 352, null);
                g.drawImage(menu, 144, 384, null);
                drawCentre(g, "Buy", 176, 370);
                drawCentre(g, "Sale", 176, 402);
                g.drawImage(short_cursor, 144, 352 + 32 * cursor_y, null);
                break;
            case 1:

                g.drawImage(ShopBox, 32, 320, null);
                g.drawImage(infoBox, 0, 472, null);
                g.setColor(Color.ORANGE);
                g.setFont(new Font("Dialog", 1, 15));
                g.drawString("Name", 62, 310);
                g.drawString("Uses", 182, 310);
                drawRight(g, "Price", 320, 310);
                g.setColor(Color.WHITE);
                for (int i = 0; i < 5; i++) {
                    if (top + i < shop.getGoodslist().size()) {
                        drawItem(g, list.get(i + top), i);
                    }
                }
                g.drawString("Asset " + asset + "G", 250, 280);
                g.drawImage(long_cursor, 32, 320 + 25 * cursor_y, null);
                aim = list.get(top + cursor_y);

                if (buy) {
                    g.drawImage(menu, 144, 352, null);
                    g.drawImage(menu, 144, 384, null);
                    drawCentre(g, "Yes", 176, 370);
                    drawCentre(g, "No", 176, 402);
                    g.drawImage(short_cursor, 144, 352 + 32 * sub_y, null);
                }
                break;
            case 2:

                g.drawImage(ShopBox, 32, 320, null);
                g.drawImage(infoBox, 0, 472, null);
                g.setColor(Color.ORANGE);
                g.setFont(new Font("Dialog", 1, 15));
                g.drawString("Name", 62, 310);
                g.drawString("Uses", 182, 310);
                drawRight(g, "Price", 320, 310);
                g.setColor(Color.WHITE);
                for (int i = 0; i < 5; i++) {
                    if (i < c.getBag().size()) {
                        drawItem(g, c.getBag().get(i), i);
                    }
                }
                g.drawString("Asset " + asset + "G", 250, 280);
                g.drawImage(long_cursor, 32, 320 + 25 * cursor_y, null);
                aim = c.getBag().get(cursor_y);
                System.out.println(aim.getName());

                if (sale) {
                    g.drawImage(menu, 144, 352, null);
                    g.drawImage(menu, 144, 384, null);
                    drawCentre(g, "Yes", 176, 370);
                    drawCentre(g, "No", 176, 402);
                    g.drawImage(short_cursor, 144, 352 + 32 * sub_y, null);
                }
                break;
        }


    }

    public void setShop(Shop shop) {
        this.shop = shop;
        list = new ArrayList<Item>(shop.getGoodslist());
    }

    public void setTransporter(Transporter transporter) {
        this.transporter = transporter;
    }

    private void drawItem(Graphics g, Item item, int i) {
        g.drawImage(item.getImage(), 32, 320 + 25 * i, null);
        g.drawString(item.getName(), 62, 320 + 15 + 25 * i);

        if (item instanceof Consumables) {
            g.drawString(Integer.toString(((Consumables) item).getUses()), 182, 320 + 15 + 25 * i);
            drawRight(g, item.getPrice() * ((Consumables) item).getUses() / ((Consumables) item).getUSES() + "G", 320, 320 + 15 + 25 * i);
        } else {
            drawRight(g, item.getPrice() + "G", 320, 320 + 15 + 25 * i);
        }

    }

    private void drawCentre(Graphics g, String text, int x, int y) {
        textwidth = g.getFontMetrics().stringWidth(text);
        g.drawString(text, x - textwidth / 2, y);
    }

    private void drawRight(Graphics g, String text, int x, int y) {
        textwidth = g.getFontMetrics().stringWidth(text);
        g.drawString(text, x - textwidth, y);
    }

    public int getCursor_y() {
        return cursor_y;
    }

    public void setC(Character c) {
        this.c = c;
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

    public boolean isBuy() {
        return buy;
    }

    public void setBuy(boolean buy) {
        this.buy = buy;
    }

    public boolean isSale() {
        return sale;
    }

    public void setSale(boolean sale) {
        this.sale = sale;
    }

    public void ShopBox_Up() {
        if (cursor_y > 0) {
            cursor_y--;
        } else {
            if (top > 0) {
                top--;
            } else {
                top = Math.max(0, list.size() - 5);
                cursor_y = Math.min(4, Math.max(0, list.size() - 1));
            }
        }
    }

    public void ShopBox_Down() {
        if (cursor_y < Math.min(4, list.size() - 1)) {
            cursor_y++;
        } else {
            if (top < list.size() - 5) {
                top++;
            } else {
                top = 0;
                cursor_y = 0;
            }
        }
    }

    public void ItemBox_Up() {
        if (cursor_y > 0) {
            cursor_y--;
        } else {
            cursor_y = c.getBag().size() - 1;
        }
    }

    public void ItemBox_Down() {
        if (cursor_y < c.getBag().size() - 1) {
            cursor_y++;
        } else {
            cursor_y = 0;
        }
    }

    public int getSub_y() {
        return sub_y;
    }

    public void setSub_y(int sub_y) {
        this.sub_y = sub_y;
    }

    public Item buy() {
        if (c.getBag().size() < 5) {
            c.gainItem(aim.getCopy());
        } else {
            transporter.store(aim.getCopy());
        }
        System.out.println("send " + aim.getName());
        //and pay...
        return aim.getCopy();
    }

    public Item sale() {
        c.getBag().remove(aim);
        System.out.println("sale " + aim.getName());
        //money
        return aim;
    }

    public void setAsset(int asset) {
        this.asset = asset;
    }

    public int getPirce() {
        return aim.getPrice();
    }
}
