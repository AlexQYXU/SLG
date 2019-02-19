import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Chapter_Interface extends JLayeredPane {
    BufferedImage fog;
    BufferedImage chapter_info;

    BufferedImage chapter_box;
    SAVE save;
    Chapter chapter;
    int chapter_no;
    int cursor_y;
    int textwidth;

    public Chapter_Interface() {
        try {
            fog = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Menu/Fog.png"));
            chapter_info = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Menu/chapter_info.png"));
            chapter_box = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Menu/Message_Window.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(fog, 0, 0, null);
        g.drawImage(fog, 0, 256, null);
        g.drawImage(chapter_info, 0, 0, null);
        g.drawString("Chapter " + chapter.Chapter_No, 150, 50);
        drawCentre(g, "Objective ", 88, 100);
        switch (chapter.objective) {
            case Defeat:
                drawCentre(g, "Defeat the Boss", 88, 140);
                break;
            case Seize:
                drawCentre(g, "Seize", 88, 140);
                break;
            case Rout:
                drawCentre(g, "Rout the enemy", 88, 140);
                break;
            case Survive:
                drawCentre(g, "Survive for " + chapter.turn_limit + " turns", 88, 140);
                break;
            case Escape:
                drawCentre(g, "Escape", 88, 140);
                break;
            default:
                drawCentre(g, "Unknown", 88, 140);
                break;
        }
        drawCentre(g, "Player " + chapter.map.original_position.size(), 224, 95);
        drawCentre(g, "Enemy " + chapter.ENEMY.size(), 304, 95);
        drawCentre(g, chapter.Boss.getName(), 220, 145);
        drawCentre(g, "Lv " + chapter.Boss.getLv(), 220, 175);
        g.drawImage(chapter.Boss.getProfile(), 270, 130, 60, 60, null);


        g.drawString("Z : select chapter", 32, 288);
        g.drawString("chapters unlocked: " + save.chapters.size(), 176, 288);
        g.drawImage(chapter_box, 64, 296, null);
        drawCentre(g, "Chapter " + chapter.Chapter_No, 176, 321);
        drawCentre(g, chapter.map.map, 176, 416);
    }

    public void Cursor_Left() {
        chapter_no = save.chapters.indexOf(chapter);
        if (chapter_no != 0) {
            chapter = save.chapters.get(chapter_no - 1);
        }
    }

    public void Cursor_Right() {
        chapter_no = save.chapters.indexOf(chapter);
        if (chapter_no != save.chapters.size() - 1) {
            chapter = save.chapters.get(chapter_no + 1);
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

    private void drawCentre(Graphics g, BufferedImage map, int x, int y) {
        System.out.println(map == null);
        g.drawImage(map, x - map.getWidth() / 12, y - map.getHeight() / 12, map.getWidth() / 6, map.getHeight() / 6, null);
    }

    public void setSave(SAVE save) {
        this.save = save;
        chapter = save.chapters.get(save.chapters.size() - 1);
    }

    public Chapter getChapter() {
        return chapter;
    }
}
