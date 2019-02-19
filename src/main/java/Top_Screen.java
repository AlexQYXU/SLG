import Characters.Character;
import Characters.Type;
import Item.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Top_Screen extends JPanel {
    private GBA gba;
    static Character character;
    static Envir_Item terrain;
    BufferedImage background;
    BufferedImage background_2;
    BufferedImage chapter_info;

    BufferedImage RankSword;
    BufferedImage RankLance;
    BufferedImage RankAxe;
    BufferedImage RankBow;
    BufferedImage RankWind;
    BufferedImage RankFire;
    BufferedImage RankThunder;
    BufferedImage RankStaff;
    int textwidth;
    String path = System.getProperty("user.dir") + "/src/main/resources/Icons";
    private boolean show_chapterinfo;


    public Top_Screen(GBA gba) throws IOException {
        background = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Menu/background_ver2.png"));
        background_2 = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Menu/Fog.png"));
        chapter_info = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Menu/chapter_info.png"));
        RankSword = ImageIO.read(new File(path + "/GCNRankSword.png"));
        RankLance = ImageIO.read(new File(path + "/GCNRankLance.png"));
        RankAxe = ImageIO.read(new File(path + "/GCNRankAxe.png"));
        RankBow = ImageIO.read(new File(path + "/GCNRankBow.png"));
        RankWind = ImageIO.read(new File(path + "/GCNRankWind.png"));
        RankFire = ImageIO.read(new File(path + "/GCNRankFire.png"));
        RankThunder = ImageIO.read(new File(path + "/GCNRankThunder.png"));
        RankStaff = ImageIO.read(new File(path + "/GCNRankStaff.png"));
        this.gba = gba;

    }


    @Override
    public void paint(Graphics g) {
        if (!show_chapterinfo) {
            g.drawImage(background, 0, 0, background.getWidth(), background.getHeight(), null);
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
            if (character != null) {

                g.drawImage(character.getProfile(), 0, 0, 96, 96, null);
                g.drawImage(character.getCharacter_Image().getSubimage(0, 0, 32, 32), 96, 0, null);
                drawRight(g, character.gethp() + "/" + character.getHP(), 156, 70);
                g.drawString(character.getName(), 150, 20);
                g.drawString(character.getJob().toString(), 250, 20);
                drawRight(g, Integer.toString(character.getLv()), 136, 45);
                drawRight(g, Integer.toString(character.getExp()), 166, 45);
                drawRight(g, Integer.toString(character.getMove()), 156, 95);
                drawRight(g, Integer.toString(character.getStr()), 50, 116);
                drawRight(g, Integer.toString(character.getMag()), 50, 136);
                drawRight(g, Integer.toString(character.getSkl()), 50, 156);
                drawRight(g, Integer.toString(character.getSpd()), 50, 176);
                drawRight(g, Integer.toString(character.getLuk()), 50, 196);
                drawRight(g, Integer.toString(character.getDef()), 50, 216);
                drawRight(g, Integer.toString(character.getRes()), 50, 236);
                int n = 0;
                for (Type type : character.getTypes()) {
                    g.setColor(Color.YELLOW);
                    g.drawString(type.toString(), 276 + 30 * n, 95);
                    n++;
                }
                g.setColor(Color.WHITE);

                if (character.getWeapon_ability() != null) {
                    int i = 0;
                    for (WeaponLevel lv : character.getWeapon_ability()) {
                        if (lv == WeaponLevel.NA) {
                            g.drawString("-", 110, 113 + 20 * i);
                        } else {
                            g.drawString(lv.toString(), 110, 113 + 20 * i);
                        }
                        i++;
                    }
                }
                if (!character.getBag().isEmpty()) {
                    int num = 0;
                    for (Item item : character.getBag()) {
                        if (item.isDropable()) {
                            g.setColor(Color.RED);
                        } else {
                            g.setColor(Color.WHITE);
                        }
                        if (num < 5) {
                            g.drawImage(item.getImage(), 150, 96 + 20 * num, null);
                            g.drawString(item.getName(), 180, 116 + 20 * num);
                            if (item instanceof Consumables) {
                                drawRight(g, ((Consumables) item).getUses() + "/", 330, 116 + 20 * num);
                                drawRight(g, Integer.toString(((Consumables) item).getUSES()), 350, 116 + 20 * num);
                            }
                            num++;
                        }
                    }
                }
                g.setColor(Color.WHITE);
                if (character.getEquiped_Weapon() == null) {
                    drawRight(g, "--", 266, 45);
                    drawRight(g, "--", 336, 45);
                    drawRight(g, "--", 266, 70);
                    drawRight(g, "--", 336, 70);
                    drawRight(g, "--", 266, 95);
                } else {
                    drawRight(g, Integer.toString(character.getMight() + character.getEquiped_Weapon().getMight()), 266, 45);
                    drawRight(g, Integer.toString(character.getEquiped_Weapon().getCrit() + character.getSkl() / 2), 336, 45);
                    drawRight(g, Integer.toString(character.getEquiped_Weapon().getHit() + character.getSkl() * 2 + character.getLuk()), 266, 70);
                    int atk_spd = character.getSpd() - Math.max(character.getEquiped_Weapon().getWeight() - character.getStr(), 0);
                    drawRight(g, Integer.toString(atk_spd * 2 + character.getLuk()), 336, 70);
                    if (character.getEquiped_Weapon().getRange_1() == character.getEquiped_Weapon().getRange_2()) {
                        drawRight(g, Integer.toString(character.getEquiped_Weapon().getRange_1()), 266, 95);
                    } else {
                        drawRight(g, character.getEquiped_Weapon().getRange_1() + " - " + character.getEquiped_Weapon().getRange_2(), 266, 95);
                    }
                }
            }
            if (terrain != null) {
                g.setColor(Color.WHITE);
                g.drawString(terrain.getName(), 160, 240);
                g.setColor(Color.GRAY);
                g.drawString("DEF", 220, 230);
                drawRight(g, Integer.toString(terrain.getDEF_Bonus()), 250, 250);
                g.drawString("AVO", 260, 230);
                drawRight(g, Integer.toString(terrain.getAVO_Bonus()) + "%", 290, 250);
                g.drawString("HEAL", 300, 230);
                drawRight(g, Integer.toString(terrain.getHEAL_Bonus()) + "%", 330, 250);
            }

        } else {
            g.drawImage(background_2, 0, 0, null);
            g.drawImage(chapter_info, 0, 0, null);
            g.drawString("Chapter " + gba.chapter.Chapter_No, 150, 50);
            g.drawString(gba.hour + " : " + gba.min + " : " + gba.second, 250, 230);
            drawCentre(g, "Objective", 88, 100);
            switch (gba.chapter.objective) {
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
                    drawCentre(g, "Survive for " + gba.chapter.turn_limit + " turns", 88, 140);
                    break;
                case Escape:
                    drawCentre(g, "Escape", 88, 140);
                    break;
                default:
                    drawCentre(g, "Unknown", 88, 140);
                    break;
            }
            drawCentre(g, "Turn: " + gba.turn, 88, 200);
            drawCentre(g, "Player " + gba.PLAYER.size(), 224, 95);
            drawCentre(g, "Enemy " + gba.ENEMY.size(), 304, 95);
            drawCentre(g, gba.chapter.Boss.getName(), 220, 145);
            drawCentre(g, "Lv " + gba.chapter.Boss.getLv(), 220, 175);
            g.drawImage(gba.chapter.Boss.getProfile(), 270, 130, 60, 60, null);
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

    protected void setCharacter(Character character) {
        Top_Screen.character = character;
    }

    protected Character getCharacter() {
        return character;
    }

    protected void setTerrain(Envir_Item terrain) {
        Top_Screen.terrain = terrain;
    }

    protected void switch_info() {
        show_chapterinfo = !show_chapterinfo;
    }
}
