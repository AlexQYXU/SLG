package Characters;

import Item.Weapon;
import Item.WeaponLevel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;

public class CharacterBuilder<T extends Character> {
    static String path = System.getProperty("user.dir") + "/src/main/resources/Characters/";
    protected final String name;
    protected final Gender gender;
    protected Camp camp = Camp.UNKNOWN;
    protected Job job;
    protected int x, y;
    protected int lv = 1;
    protected int exp = 0;
    protected int HP = 10, str = 1, mag = 1, skl = 1, spd = 1, luk = 1, def = 1, res = 0;
    protected int move = 3;
    protected int build = 6;
    protected HashSet<Type> types = new HashSet<>();
    protected WeaponLevel[] weapon_ability = new WeaponLevel[8];
    protected AI ai = AI.Neutral;
    protected String profile_path;
    protected String image_path;

    public CharacterBuilder(String name, Gender gender) {
        this.name = name;
        this.gender = gender;
        for (int i = 0; i < 8; i++) {
            weapon_ability[i] = WeaponLevel.NA;
        }
    }

    public CharacterBuilder<T> job(Job job) {
        switch (job) {
            case Fighter:
                move = 5;
                weapon_ability[2] = WeaponLevel.E;
                break;
            case Knight:
                move = 4;
                weapon_ability[1] = WeaponLevel.E;
                weapon_ability[0] = WeaponLevel.E;
                types.add(Type.Armor);
                break;
            case Cavalier:
                move = 7;
                weapon_ability[0] = WeaponLevel.E;
                weapon_ability[1] = WeaponLevel.E;
                types.add(Type.Mounted);
                break;
            case Scout:
                move = 6;
                weapon_ability[0] = WeaponLevel.E;
                break;
            case Archer:
                move = 5;
                weapon_ability[3] = WeaponLevel.E;
                break;
            case Mercenary:
                move = 5;
                weapon_ability[0] = WeaponLevel.E;
                break;
            case Cleric:
                move = 5;
                weapon_ability[7] = WeaponLevel.E;
                break;
            case Thief:
                move = 6;
                weapon_ability[0] = WeaponLevel.E;
                break;
            case Lord:
                move = 5;
                weapon_ability[0] = WeaponLevel.D;
                break;
            case Mage:
                move = 5;
                weapon_ability[5] = WeaponLevel.E;
                break;
            case Soldier:
                move = 5;
                weapon_ability[1] = WeaponLevel.E;
                break;
            default:
                job = Job.Villager;
                weapon_ability[0] = WeaponLevel.E;
                move = 4;
                break;
        }
        this.job = job;
        return this;
    }

    public CharacterBuilder lv(int val) {
        if (lv >= 1 && lv <= 20)
            lv = val;
        return this;
    }


    public CharacterBuilder para(int hp, int str, int mag, int skl, int spd, int luk, int def, int res) {
        if (hp > 0) this.HP = hp;
        //STR and MAG cannot be both zero, unless this character is convoy...
        if (str > 0 || mag > 0) {
            this.str = str;
            this.mag = mag;
        }
        if (skl > 0) this.skl = skl;
        if (spd > 0) this.spd = spd;
        if (luk > 0) this.luk = luk;
        if (def > 0) this.def = def;
        //Resistivity (Magic Defence) can be zero.
        if (res >= 0) this.res = res;
        return this;
    }

    public CharacterBuilder weapon_ability(WeaponLevel[] weapon_ability) {
        for (int i = 0; i < 8; i++) {
            if (weapon_ability[i] != null) {
                this.weapon_ability[i] = weapon_ability[i];
            }
        }
        return this;
    }

    public CharacterBuilder camp(Camp c) {
        camp = c;
        if (c == Camp.MONSTER) {
            types.add(Type.Monster);
        }
        return this;
    }


    public CharacterBuilder exp(int val) {
        if (val >= 0 && val < 100) exp = val;
        return this;
    }

    public CharacterBuilder build(int val) {
        if (val > 0) {
            build = val;
        }
        return this;
    }

    public CharacterBuilder x(int val) {
        if (val >= 0)
            x = val;
        return this;
    }

    public CharacterBuilder y(int val) {
        if (val >= 0) y = val;
        return this;
    }

    public CharacterBuilder profile(String address) {
//        String profile_name = job.toString() + "_" + val + ".png";
//        profile = ImageIO.read(new File(path + profile_name));
        profile_path = address;
        return this;
    }

    public CharacterBuilder profile(int val) {
        profile_path = path + "Profile/" + job.toString() + "_" + val + ".png";
        return this;
    }

    public CharacterBuilder character_image(String address) {
        image_path = address;
        return this;
    }

    public CharacterBuilder character_image(int val) {
        image_path = path + "Character/" + job.toString() + val + ".png";
        return this;
    }

    public CharacterBuilder ai(AI ai) {
        this.ai = ai;
        return this;
    }

    public Character build() {
        return new Character(this.name, this.gender, this.camp, this.job, this.lv, this.exp, this.x, this.y, this.weapon_ability,
                this.profile_path, this.image_path, this.HP, this.str, this.mag, this.skl, this.spd, this.luk, this.def, this.res, this.build, this.move, this.types, this.ai);
    }

}
