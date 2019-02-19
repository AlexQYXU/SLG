package Display;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Message_Window extends JLayeredPane {
    BufferedImage Message_Window;
    String message;
    int textwidth;

    public Message_Window() {
        try {
            Message_Window = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Menu/Message_Window.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paint(Graphics g) {
        setFont(new Font("Dialog", 1, 12));
        g.setColor(Color.WHITE);
        g.drawImage(Message_Window, 0, 0, null);
        drawCentre(g, message, 80, 25);


    }

    public void drawCentre(Graphics g, String text, int x, int y) {
        textwidth = g.getFontMetrics().stringWidth(text);
        g.drawString(text, x - textwidth / 2, y);
    }

    public void drawMessage(String text) {
        message = text;
    }
}
