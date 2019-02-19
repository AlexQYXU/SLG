package Characters;


import Item.*;
import Item.Envir_Items.Chest;
import Item.Weapon;
import Item.WeaponLevel;
import Item.WeaponType;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class Character {
    int dir;
    int subdir;
    int step;
    int dir_x, dir_y;
    static String path = System.getProperty("user.dir") + "/src/main/resources/Characters/";
    private String Name;
    private Job job;
    private Gender gender;
    String profile_path;
    String image_path;
    private int exp;
    private int lv;
    private int HP;
    private int hp;
    private int Str;
    private int Mag;
    private int Skl;
    private int Spd;
    private int Luk;
    private int Def;
    private int Res;
    private int[] growth;
    private int build;
    private Camp camp;
    private AI ai;
    boolean movepoint;
    WeaponLevel[] weapon_ability = new WeaponLevel[8];
    int[] weapon_exp = new int[8];
    public int x;
    public int y;
    private int move;
    BufferedImage character_Image;
    BufferedImage profile;
    int dest_x;
    int dest_y;
    Weapon equiped_Weapon;
    Ornament equiped_Ornament;
    ArrayList<Item> Bag = new ArrayList<>();
    static int bag_size = 5;
    HashSet<Type> types = new HashSet<>();
    private ArrayList<Weapon> weapons = new ArrayList<Weapon>();

    private int Envir_DEF;
    private int Envir_AVO;
    private int Envir_HP;
    private Item want;

    protected Character() {

    }

    public Character(String name, Gender gender, Camp camp, Job job, int lv, int exp, int x, int y,
                     WeaponLevel[] weapon_ability, String profile_path, String image_path, int HP,
                     int str, int mag, int skl, int spd, int luk, int def, int res, int build, int move, HashSet<Type> types, AI ai) {
        try {
            this.Name = name;
            this.gender = gender;
            this.camp = camp;
            this.job = job;
            this.lv = lv;
            this.exp = exp;
            this.x = x;
            this.y = y;
            this.weapon_ability = weapon_ability;
            this.profile_path = profile_path;
            this.image_path = image_path;
            this.profile = ImageIO.read(new File(profile_path));
            this.character_Image = ImageIO.read(new File(image_path));
            this.HP = HP;
            this.hp = HP;
            this.Str = str;
            this.Mag = mag;
            this.Skl = skl;
            this.Spd = spd;
            this.Luk = luk;
            this.Def = def;
            this.Res = res;
            this.build = build;
            this.move = move;
            this.types = types;
            this.ai = ai;
            for (int i = 0; i < 8; i++) {
                if (weapon_ability[i] == null) {
                    weapon_exp[i] = 0;
                } else {
                    switch (weapon_ability[i]) {
                        case E:
                            weapon_exp[i] = 1;
                            break;
                        case D:
                            weapon_exp[i] = 31;
                            break;
                        case C:
                            weapon_exp[i] = 71;
                            break;
                        case B:
                            weapon_exp[i] = 121;
                            break;
                        case A:
                            weapon_exp[i] = 181;
                            break;
                        case S:
                            weapon_exp[i] = 251;
                            break;
                        default:
                            weapon_exp[i] = 0;
                            break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage draw() {
        return this.getCharacter_Image().getSubimage(32 * getStep(), 32 * getDir(), 32, 32);
    }


    public Weapon getWeapon() {
        if (equiped_Weapon != null) {
            return equiped_Weapon;
        }
        for (Item item : Bag) {
            if (item instanceof Weapon && this.can_Equip(item)) {
                return (Weapon) item;
            }
        }
        return null;
    }

    public int[] getGrowth() {
        return growth;
    }

    public void setGrowth(int[] growth) {
        this.growth = growth;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public int getHP() {
        return HP;
    }

    public void sethp(int hp) {
        this.hp = hp;
    }

    public int gethp() {
        return hp;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Job getJob() {
        return job;
    }

    public int getMove() {
        return move;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setWant(Item want) {
        this.want = want;
    }

    public Item getWant() {
        return want;
    }

    public void setMove(int move) {
        this.move = move;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Gender getGender() {
        return gender;
    }

    public void gainItem(Item item) {
        Bag.add(item);
        item.setOwner(this);
    }

    public static int getBag_size() {
        return bag_size;
    }

    public int getLv() {
        return lv;
    }

    public void setLv(int lv) {
        this.lv = lv;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int[] getWeapon_exp() {
        return weapon_exp;
    }

    public void setWeapon_exp(int[] weapon_exp) {
        this.weapon_exp = weapon_exp;
    }

    public void addWeapon_exp(int no, int exp) {
        System.out.println(weapon_exp[no] + " get " + exp + " exp");

        this.weapon_exp[no] += exp;
        if (weapon_exp[no] > getWeaponEXP_UL(weapon_ability[no])) {
            System.out.println("Weapon LEVEL UP!");
            Weapon_LevelUp(no);
        }
    }

    public int getWeaponEXP_UL(WeaponLevel lv) {
        switch (lv) {
            case E:
                return 30;
            case D:
                return 70;
            case C:
                return 120;
            case B:
                return 180;
            case A:
                return 250;
            default:
                return Integer.MAX_VALUE;
        }
    }

    public void equip(Weapon weapon) {
        if (can_Equip(weapon)) {
            weapon.equip(this);
        }
    }

    public void Ability_UP(int i) {
        switch (i) {
            case 0:
                this.HP++;
                break;
            case 1:
                this.Str++;
                break;
            case 2:
                this.Mag++;
                break;
            case 3:
                this.Skl++;
                break;
            case 4:
                this.Spd++;
                break;
            case 5:
                this.Luk++;
                break;
            case 6:
                this.Def++;
                break;
            case 7:
                this.Res++;
                break;
            default:
                break;
        }
    }

    public void setWeapon_ability(int no, WeaponLevel lv) {
        if (no < 8 && no >= 0) {
            this.weapon_ability[no] = lv;
            switch (lv) {
                case E:
                    weapon_exp[no] = 1;
                    break;
                case D:
                    weapon_exp[no] = 31;
                    break;
                case C:
                    weapon_exp[no] = 71;
                    break;
                case B:
                    weapon_exp[no] = 121;
                    break;
                case A:
                    weapon_exp[no] = 181;
                    break;
                case S:
                    weapon_exp[no] = 251;
                    break;
                default:
                    weapon_exp[no] = 0;
                    break;
            }
        }
    }

    public int getStr() {
        return Str;
    }

    public void setStr(int str) {
        Str = str;
    }

    public int getMag() {
        return Mag;
    }

    public void setMag(int mag) {
        Mag = mag;
    }

    public int getSkl() {
        return Skl;
    }

    public void setSkl(int skl) {
        Skl = skl;
    }

    public int getSpd() {
        return Spd;
    }

    public void setSpd(int spd) {
        Spd = spd;
    }

    public int getLuk() {
        return Luk;
    }

    public void setLuk(int luk) {
        Luk = luk;
    }

    public int getDef() {
        return Def;
    }

    public void setDef(int def) {
        Def = def;
    }

    public int getRes() {
        return Res;
    }

    public void setRes(int res) {
        Res = res;
    }

    public int getBuild() {
        return build;
    }

    public void setBuild(int build) {
        this.build = build;
    }

    public ArrayList<Item> getBag() {
        return Bag;
    }

    public BufferedImage getProfile() {
        return profile;
    }

    public WeaponLevel[] getWeapon_ability() {
        return weapon_ability;
    }

    public void setWeapon_ability(WeaponLevel[] weapon_ability) {
        this.weapon_ability = weapon_ability;
    }

    public void setDir_x(int dir_x) {
        this.dir_x = dir_x;
    }

    public int getDir_x() {
        return dir_x;
    }

    public void setDir_y(int dir_y) {
        this.dir_y = dir_y;
    }

    public int getDir_y() {
        return dir_y;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    public int getDir() {
        return dir;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public void setEquiped_Weapon(Weapon equiped_Weapon) {
        this.equiped_Weapon = equiped_Weapon;
    }

    public Weapon getEquiped_Weapon() {
        return equiped_Weapon;
    }

    public Ornament getEquiped_Ornament() {
        return equiped_Ornament;
    }

    public void setEquiped_Ornament(Ornament equiped_Ornament) {
        this.equiped_Ornament = equiped_Ornament;
    }

    public void setMovepoint(boolean movepoint) {
        this.movepoint = movepoint;
    }

    public boolean getMovepoint() {
        return movepoint;
    }

    public boolean canMove() {
        return movepoint;
    }

    public int getSubdir() {
        return subdir;
    }

    public void setSubdir(int subdir) {
        this.subdir = subdir;
    }

    public void setCamp(Camp camp) {
        this.camp = camp;
    }

    public Camp getCamp() {
        return camp;
    }

    public void setEnvir_HP(int envir_HP) {
        Envir_HP = envir_HP;
    }

    public int getEnvir_DEF() {
        return Envir_DEF;
    }

    public int getEnvir_AVO() {
        return Envir_AVO;
    }

    public int getEnvir_HP() {
        return Envir_HP;
    }

    public BufferedImage getCharacter_Image() {
        return character_Image;
    }


    public void move(int x, int y) {
        this.dest_x = x;
        this.dest_y = y;
    }

    public void move(Direction dir) {
        this.x += dir.x;
        this.y += dir.y;
    }


    public void fold(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void getDamage(int val) {
        hp -= val;
        if (hp < 0) hp = 0;
    }


    public void Weapon_LevelUp(WeaponType type) {
        int i = 0;
        switch (type) {
            case Sword:
                i = 0;
                break;
            case Lance:
                i = 1;
                break;
            case Axe:
                i = 2;
                break;
            case Bow:
                i = 3;
                break;
            case Wind:
                i = 4;
                break;
            case Fire:
                i = 5;
                break;
            case Thunder:
                i = 6;
                break;
            case Staff:
                i = 7;
                break;
        }
        if (weapon_ability[i] != null) {
            WeaponLevel[] levels = WeaponLevel.values();
            weapon_ability[i] = levels[weapon_ability[i].ordinal() + 1];
        } else {
            weapon_ability[i] = WeaponLevel.E;
        }
    }

    public void Weapon_LevelUp(int i) {
        if (weapon_ability[i] != null) {
            WeaponLevel[] levels = WeaponLevel.values();
            weapon_ability[i] = levels[weapon_ability[i].ordinal() + 1];
        } else {
            weapon_ability[i] = WeaponLevel.E;
        }
    }

    public boolean can_Equip(Item item) {
        int i = 0;
        if (item instanceof Weapon) {
            switch (((Weapon) item).getClass().getName()) {
                case "Item.Sword":
                    i = 0;
                    break;
                case "Item.Lance":
                    i = 1;
                    break;
                case "Item.Axe":
                    i = 2;
                    break;
                case "Item.Bow":
                    i = 3;
                    break;
                case "Item.Wind":
                    i = 4;
                    break;
                case "Item.Fire":
                    i = 5;
                    break;
                case "Item.Thunder":
                    i = 6;
                    break;
                case "Item.Staff":
                    i = 7;
                    break;
                default:
                    break;
            }
            if (weapon_ability[i] != null && weapon_ability[i].compareTo(((Weapon) item).getLevel()) >= 0) {
                return true;
            }
        }
        return false;
    }

    public void setEnvir_DEF(int val) {
        this.Envir_DEF = val;
    }

    public void setEnvir_AVO(int val) {
        this.Envir_AVO = val;
    }

    public void cleanEnvir_Bonus() {
        this.Envir_AVO = 0;
        this.Envir_DEF = 0;
    }


    public static void Mimic_JumpOut(Chest chest) {

    }

    public Weapon first_Weapon() {
        if (this.equiped_Weapon != null) {
            return equiped_Weapon;
        } else {
            for (Item item : this.Bag) {
                if (item instanceof Weapon && this.can_Equip(item)) {
                    this.equip((Weapon) item);
                    return (Weapon) item;
                }
            }
        }
        return null;

    }

    public int getMight() {
        if (equiped_Weapon == null) return 0;
        if (this.equiped_Weapon.isPhysical()) {
            return Str;
        } else {
            return Mag;
        }
    }

    public int getAtk() {
        if (equiped_Weapon == null) return 0;
        return getMight() + equiped_Weapon.getMight();
    }

    public int getHit() {
        if (equiped_Weapon == null) return 0;
        return equiped_Weapon.getHit() + Skl * 2 + Luk;
    }

    public int getAvo() {
        int atk_spd = Spd - Math.max(equiped_Weapon.getWeight() - Str, 0);
        return atk_spd * 2 + Luk;
    }

    public int getDefence(Weapon w) {
        if (w == null) return Def;
        if (w.isPhysical()) {
            return Def;
        } else {
            return Res;
        }
    }

    public void heal(int val) {
        if (hp < HP) {
            hp = Math.min(hp + val, HP);
        }
    }

    public AI getAi() {
        return ai;
    }

    public void setAi(AI ai) {
        this.ai = ai;
    }

    public HashSet<Type> getTypes() {
        return types;
    }

    public ArrayList<Weapon> getWeapons() {
        weapons.clear();
        for (Item item : Bag) {
            if (item instanceof Weapon && this.can_Equip(item)) {
                weapons.add((Weapon) item);
            }
        }
        return weapons;
    }

    @Override
    public String toString() {
        return this.getName();
    }

    public Character getCopy() {
        Character copy = new Character(this.Name, this.gender, this.getCamp(), this.job, this.getLv(), this.getExp(), this.x, this.y, this.weapon_ability, this.profile_path, this.image_path, this.HP, this.Str, this.Mag, this.Skl, this.Spd, this.Luk, this.Def, this.Res, this.build, this.move, this.types, this.ai);
        for (Item item : this.getBag()) {
            copy.gainItem(item.getCopy());
        }
        return copy;
    }
}

