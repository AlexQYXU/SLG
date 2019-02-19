package Item;

import Characters.Character;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class Ornament extends Item implements Equipable {
    String address = System.getProperty("user.dir") + "/src/main/resources/Icons/";
    private int HP;
    private int Str;
    private int Mag;
    private int Skl;
    private int Spd;
    private int Luk;
    private int Def;
    private int Res;

    public Ornament(String name, int HP, int Str, int Mag, int Skl, int Spd, int Luk, int Def, int Res) throws IOException {
        this.image = ImageIO.read(new File(address + name + ".png"));
        this.name = name;
        this.HP = HP;
        this.Str = Str;
        this.Mag = Mag;
        this.Skl = Skl;
        this.Spd = Spd;
        this.Luk = Luk;
        this.Def = Def;
        this.Res = Res;
    }

    @Override
    public void equip(Character c) {
        if (this == c.getEquiped_Ornament()) {
            c.setEquiped_Ornament(null);
        } else {
            c.setEquiped_Ornament(this);
        }
    }

    public int getHP() {
        return HP;
    }

    public int getStr() {
        return Str;
    }

    public int getMag() {
        return Mag;
    }

    public int getSkl() {
        return Skl;
    }

    public int getSpd() {
        return Spd;
    }

    public int getLuk() {
        return Luk;
    }

    public int getDef() {
        return Def;
    }

    public int getRes() {
        return Res;
    }
}
