package Display;

import Characters.Character;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Dialogue_Window extends JLayeredPane {
    BufferedImage window;
    int window_x = 352;
    int window_y;
    int z_mark;
    String text;
    Character c1, c2;
    int width = window_x - 20;
    int length;
    int sum;
    boolean in_conversation;


    @Override
    public void paint(Graphics g) {
        System.out.println(text);
        g.setFont(new Font("Dialog", 1, 20));
        g.setColor(Color.WHITE);
        if (c1 != null) {
            g.drawImage(c1.getProfile(), 0, 0, null);
        }
        if (c2 != null) {
            g.drawImage(c2.getProfile(), 160, 0, null);
        }
        String[] texts = text.split(" ");
        int i = 0;
        sum = 0;
        for (String str : texts) {
            length = g.getFontMetrics().stringWidth(str+" ");
            if (sum + length <= width) {
                g.drawString(str + " ", 10 + sum, 116 + 20 * i);
                sum += length;
            } else {
                i++;
                g.drawString(str + " ", 10, 116 + 20 * i);
                sum = length;
            }
        }
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setC1(Character c1) {
        this.c1 = c1;
    }

    public void setC2(Character c2) {
        this.c2 = c2;
    }

    public void setIn_conversation(boolean in_conversation) {
        this.in_conversation = in_conversation;
    }

    public boolean isIn_conversation() {
        return in_conversation;
    }

    public void clean() {
        c1 = null;
        c2 = null;
    }
}
