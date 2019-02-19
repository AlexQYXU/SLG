package Item;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Stack;

import Characters.Character;
import Characters.Type;
import Item.TypeAdapters.FireTypeAdapter;
import Item.TypeAdapters.StaffTypeAdapter;
import Item.TypeAdapters.StairTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public abstract class Weapon extends Consumables implements Equipable {
    protected final int might;
    protected final int weight;
    protected final WeaponLevel level;
    protected final int hit;
    protected final int crit;
    protected final int range_1, range_2;
    protected final int exp;
    protected final boolean physical;
    protected HashSet<Type> slayer = new HashSet<>();

    // below parameters may change during game, not final
    private java.lang.Character user;


    public Weapon(String name, int might, int weight, WeaponLevel level, int hit, int Uses, int crit
            , int range_1, int range_2, int exp, boolean physical, int price, BufferedImage image, HashSet<Type> slayer, boolean drop) {
        this.name = name;
        this.might = might;
        this.weight = weight;
        this.level = level;
        this.hit = hit;
        this.Uses = Uses;
        this.uses = Uses;
        this.crit = crit;
        this.range_1 = range_1;
        this.range_2 = range_2;
        this.exp = exp;
        this.physical = physical;
        // by default
        this.equipable = true;
        this.image = image;
        this.slayer = slayer;
        this.price = price;
        this.setDropable(drop);
    }

    public Weapon(String name, int might, int weight, WeaponLevel level, int hit, int Uses, int crit
            , int range_1, int range_2, int exp, boolean physical, int price, BufferedImage image, HashSet<Type> slayer) {
        this.name = name;
        this.might = might;
        this.weight = weight;
        this.level = level;
        this.hit = hit;
        this.Uses = Uses;
        this.uses = Uses;
        this.crit = crit;
        this.range_1 = range_1;
        this.range_2 = range_2;
        this.exp = exp;
        this.physical = physical;
        // by default
        this.equipable = true;
        this.image = image;
        this.slayer = slayer;
        this.price = price;
    }

    // Q: mostly performing Characters.Character, why in {@Item.Equipable}?
    // TODO consider put a new method in Characters.Character to do equip
    @Override
    public void equip(Character c) {

        if (c.getEquiped_Weapon() == this) {
            c.setEquiped_Weapon(null);
        } else {
            c.getBag().remove(this);
            c.getBag().add(0, this);
            c.setEquiped_Weapon(this);
        }
    }

    // TODO this is not builder pattern
//    private Item.Weapon(Item.WeaponBuilder builder) {
//        this.name = builder.name;
//        this.physical = builder.physical;
//        this.might = builder.might;
//        this.weight = builder.weight;
//        this.hit = builder.hit;
//        this.crit = builder.crit;
//        this.level = builder.level;
//        this.range_1 = builder.range_1;
//        this.range_2 = builder.range_2;
//        this.exp = builder.exp;
//        this.image = builder.image;
//        this.uses = builder.uses;
//        this.equipable = true;//All weapons should be Item.Equipable.
//    }


    public abstract Weapon getCopy();

    public boolean Slayer_check(Character c) {
        for (Type type : slayer) {
            if (c.getTypes().contains(type)) {
                return true;
            }
        }
        return false;
    }

    public HashSet<Type> getSlayer() {
        return slayer;
    }

    public abstract WeaponType getType();

    public WeaponLevel getLevel() {
        return this.level;
    }

    static String path = System.getProperty("user.dir") + "/src/main/resources/Icons";

    public static Weapon Iron_Weapon(WeaponType type) throws IOException {

        switch (type) {
            case Sword:
                return new SwordBuilder().name("Iron Sword").might(5).weight(7).hit(90).crit(0).range(1, 1).level(WeaponLevel.E).exp(1).uses(35).physical(true).price(560).image(ImageIO.read(new File(path + "/Iron Sword.png"))).build();
            case Lance:
                return new LanceBuilder().name("Iron Lance").might(7).weight(8).hit(80).crit(0).range(1, 1).uses(45).level(WeaponLevel.E).exp(1).physical(true).price(360).image(ImageIO.read(new File(path + "/Iron Lance.png"))).build();
            case Axe:
                return new AxeBuilder().name("Iron Axe").might(8).weight(10).hit(75).crit(0).range(1, 1).level(WeaponLevel.E).exp(1).uses(45).physical(true).price(270).image(ImageIO.read(new File(path + "/Iron Axe.png"))).build();
            case Bow:
                return new BowBuilder().name("Iron Bow").might(6).weight(5).hit(85).crit(0).range(2, 2).uses(45).level(WeaponLevel.E).exp(1).physical(true).price(540).image(ImageIO.read(new File(path + "/Iron Bow.png"))).build();

            default:
                return null;
        }

    }

    public static Weapon Armor_Special(WeaponType type) throws IOException {

        switch (type) {
            case Sword:
                return new SwordBuilder().name("Armor Slayer").might(8).weight(17).hit(80).crit(0).range(1, 1).level(WeaponLevel.D).slayer(Type.Armor).physical(true).exp(2).uses(18).price(1260).image(ImageIO.read(new File(path + "/Armor Slayer.png"))).build();

            case Lance:
                return new LanceBuilder().name("Heavy Spear").might(8).weight(18).hit(80).crit(0).range(1, 1).uses(16).price(1260).level(WeaponLevel.D).slayer(Type.Armor).physical(true).exp(2).image(ImageIO.read(new File(path + "/Heavy Spear.png"))).build();

            case Axe:
                return new AxeBuilder().name("Hammer").might(10).weight(20).hit(55).crit(0).range(1, 1).level(WeaponLevel.D).price(800).slayer(Type.Armor).physical(true).exp(2).uses(20).image(ImageIO.read(new File(path + "/Hammer.png"))).build();

            default:
                return null;
        }

    }

    public static Weapon Steel_Weapon(WeaponType type) throws IOException {
        switch (type) {
            case Sword:
                return new SwordBuilder().name("Steel Sword").might(8).weight(12).hit(75).crit(0).range(1, 1).level(WeaponLevel.D).exp(2).uses(35).physical(true).price(700).
                        image(ImageIO.read(new File(path + "/Steel Sword.png"))).build();
            case Lance:
                return new LanceBuilder().name("Steel Lance").might(10).weight(13).hit(70).crit(0).range(1, 1).uses(35).level(WeaponLevel.E).exp(2).physical(true).price(560).
                        image(ImageIO.read(new File(path + "/Steel Lance.png"))).build();
            case Axe:
                return new AxeBuilder().name("Steel Axe").might(11).weight(15).hit(65).crit(0).range(1, 1).level(WeaponLevel.D).exp(2).uses(35).physical(true).price(420).
                        image(ImageIO.read(new File(path + "/Steel Axe.png"))).build();
            case Bow:
                return new BowBuilder().name("Steel Bow").might(9).weight(9).hit(70).crit(0).range(2, 2).uses(35).level(WeaponLevel.D).exp(2).physical(true).price(840).
                        image(ImageIO.read(new File(path + "/Steel Bow.png"))).build();
            default:
                return null;
        }
    }

    public static Weapon Ranged_Weapon(WeaponType type) throws IOException {
        switch (type) {
            case Sword:
                return new SwordBuilder().name("Knife").might(2).weight(2).hit(65).crit(0).range(1, 2).uses(25).level(WeaponLevel.D).exp(2).physical(true).price(500).
                        image(ImageIO.read(new File(path + "/Knife.png"))).build();
            case Lance:
                return new LanceBuilder().name("Javelin").might(6).weight(11).hit(60).crit(0).range(1, 2).uses(25).level(WeaponLevel.E).exp(1).physical(true).price(500).
                        image(ImageIO.read(new File(path + "/Javelin.png"))).build();
            case Axe:
                return new AxeBuilder().name("Hand Axe").might(7).weight(13).hit(55).crit(0).range(1, 2).level(WeaponLevel.E).exp(1).uses(25).physical(true).price(375).
                        image(ImageIO.read(new File(path + "/Hand Axe.png"))).build();
            case Bow:
                return new BowBuilder().name("Long Bow").might(5).weight(10).hit(65).crit(0).range(2, 3).uses(20).level(WeaponLevel.D).exp(2).physical(true).price(2000).
                        image(ImageIO.read(new File(path + "/Long Bow.png"))).build();
            default:
                return null;
        }
    }

    public static Weapon Ranged_Weapon_LU(Class klass) throws IOException {
        switch (klass.getName()) {
            case "Item.Lance":
                return new LanceBuilder().name("Short Spear").might(9).weight(12).hit(70).crit(0).range(1, 2).uses(15).level(WeaponLevel.C).exp(2).physical(true).price(1950).
                        image(ImageIO.read(new File(path + "/Short Spear.png"))).build();
            case "Item.Axe":
                return new AxeBuilder().name("Short Axe").might(10).weight(13).hit(65).crit(0).range(1, 2).level(WeaponLevel.C).exp(1).uses(15).physical(true).price(1500).
                        image(ImageIO.read(new File(path + "/Short Axe.png"))).build();
            default:
                return null;
        }

    }

    public static Weapon Magic(WeaponType type, WeaponLevel lv) throws IOException {
        switch (type) {
            case Wind:
                switch (lv) {
                    case E:
                        return new WindBuilder().name("Wind").might(2).weight(1).hit(100).crit(0).range(1, 2).uses(40).level(WeaponLevel.E).price(520).exp(1).physical(false).
                                image(ImageIO.read(new File(path + "/Wind.png"))).build();
                    case D:
                        return new WindBuilder().name("Elwind").might(4).weight(2).hit(90).crit(0).range(1, 2).uses(30).level(WeaponLevel.D).price(1650).exp(2).physical(false).
                                image(ImageIO.read(new File(path + "/Elwind.png"))).build();
                    case C:
                        return new WindBuilder().name("Blizzard").might(8).weight(10).hit(75).crit(0).range(3, 10).uses(5).level(WeaponLevel.C).price(700).exp(3).physical(false).
                                image(ImageIO.read(new File(path + "/Blizzard.png"))).build();

                    default:
                        return null;
                }
            case Fire:
                switch (lv) {
                    case E:
                        return new FireBuilder().name("Fire").might(3).weight(3).hit(95).crit(0).range(1, 2).uses(40).price(560).level(WeaponLevel.E).exp(1).physical(false).
                                image(ImageIO.read(new File(path + "/Fire.png"))).build();
                    case D:
                        return new FireBuilder().name("Elfire").might(5).weight(5).hit(85).crit(0).range(1, 2).uses(30).price(1800).level(WeaponLevel.D).exp(2).physical(false).
                                image(ImageIO.read(new File(path + "/Elfire.png"))).build();
                    case C:
                        return new FireBuilder().name("Meteor").might(11).weight(11).hit(70).crit(0).range(3, 10).uses(5).price(750).level(WeaponLevel.D).exp(3).physical(false).
                                image(ImageIO.read(new File(path + "/Meteor.png"))).build();
                    default:
                        return null;
                }
            case Thunder:
                switch (lv) {
                    case E:
                        return new ThunderBuilder().name("Thunder").might(4).weight(3).hit(85).crit(5).range(1, 2).uses(40).price(600).level(WeaponLevel.E).exp(1).physical(false).
                                image(ImageIO.read(new File(path + "/Thunder.png"))).build();
                    case D:
                        return new ThunderBuilder().name("Elthunder").might(7).weight(6).hit(75).crit(10).range(1, 2).uses(30).price(1950).level(WeaponLevel.D).exp(2).physical(false).
                                image(ImageIO.read(new File(path + "/Elthunder.png"))).build();
                    case C:
                        return new ThunderBuilder().name("Bolting").might(13).weight(13).hit(65).crit(5).range(3, 10).uses(5).price(800).level(WeaponLevel.C).exp(3).physical(false).
                                image(ImageIO.read(new File(path + "/Bolting.png"))).build();
                    default:
                        return null;
                }

            default:
                return null;
        }
    }

    public static Staff HealingStaff(WeaponLevel lv) throws IOException {
        final String path = System.getProperty("user.dir") + "/src/main/resources/Icons";
        final StaffBuilder staffBuilder = new StaffBuilder();
        staffBuilder.level(lv).might(0).hit(100).range(1, 1);
        switch (lv) {
            case E:
                staffBuilder.withMagicEffect(new HealMagic(10)).name("Heal").weight(2).crit(0).hit(100).uses(40).exp(1).price(400).image(ImageIO.read(new File(path + "/Heal.png")));
                break;
            case D:
                staffBuilder.withMagicEffect(new HealMagic(20)).name("Mend").weight(4).hit(100).crit(10).uses(20).exp(3).price(800).image(ImageIO.read(new File(path + "/Mend.png")));
                break;
            case B:
                staffBuilder.withMagicEffect(new HealMagic(60)).name("Recover").weight(6).hit(100).crit(20).uses(20).exp(4).price(1200).image(ImageIO.read(new File(path + "/Recover.png")));
                break;
            default:
                throw new IllegalArgumentException("Healing Item.Staff of given Level [" + lv + "] does not exist!");
        }
        Staff staff = staffBuilder.build();
        staff.setDescription("Restore HP to one adjacent ally.");
        return staff;
    }

    public static Staff UnlockStaff() throws IOException {
        final String path = System.getProperty("user.dir") + "/src/main/resources/Icons";
        Staff staff = new StaffBuilder().withMagicEffect(new UnlockMagic()).name("Unlock").level(WeaponLevel.D).image(ImageIO.read(new File(path + "/Unlock.png"))).might(0).weight(7).hit(100).range(1, 2).uses(10).exp(5).price(1500).build();
        staff.setDescription("Open a gate or chest.");
        return staff;
    }

    public static Weapon Trainer(Class klass) throws IOException {
        switch (klass.getName()) {
            case "Item.Sword":
                return new SwordBuilder().name("Trainer").might(4).weight(3).hit(80).crit(0).range(1, 1).level(WeaponLevel.E).exp(1).uses(99)
                        .physical(true).price(230).image(ImageIO.read(new File(path + "/Trainer.png"))).build();
            case "Item.Axe":
                return new AxeBuilder().name("Practice Axe").might(6).weight(4).hit(80).crit(0).range(1, 1).level(WeaponLevel.E).exp(1).uses(99)
                        .physical(true).price(225).image(ImageIO.read(new File(path + "/Practice Axe.png"))).build();
            default:
                return null;
        }

    }

    public String Priority_Type() {
        switch (this.getClass().getName()) {
            case "Item.Sword":
                return "Item.Axe";
            case "Item.Lance":
                return "Item.Sword";
            case "Item.Axe":
                return "Item.Lance";
            case "Item.Bow":
                return "";
            case "Item.Wind":
                return "Item.Thunder";
            case "Item.Fire":
                return "Item.Wind";
            case "Item.Thunder":
                return "Item.Fire";
            case "Item.Staff":
                return "";
            default:
                return "";

        }
    }


    public boolean isDominant(Weapon w) {
        if (w == null) {
            return false;
        }
        String name_1 = this.getClass().getName();
        String name_2 = w.getClass().getName();
        switch (name_1) {
            case "Item.Sword":
                if (name_2 == "Item.Axe") {
                    return true;
                } else {
                    return false;
                }
            case "Item.Lance":
                if (name_2 == "Item.Sword") {
                    return true;
                } else {
                    return false;
                }
            case "Item.Axe":
                if (name_2 == "Item.Lance") {
                    return true;
                } else {
                    return false;
                }
            case "Item.Bow":
                return false;
            case "Item.Wind":
                if (name_2 == "Item.Thunder") {
                    return true;
                } else {
                    return false;
                }

            case "Item.Fire":
                if (name_2 == "Item.Wind") {
                    return true;
                } else {
                    return false;
                }

            case "Item.Thunder":
                if (name_2 == "Item.Fire") {
                    return true;
                } else {
                    return false;
                }

            case "Item.Staff":
                return false;

            default:
                return false;

        }
    }

    @Override
    public boolean equals(Object w) {
        if (w.getClass() != this.getClass()) {
            return false;
        }
        if (w instanceof Weapon) {
            if (!((Weapon) w).getName().equals(this.getName())) {
                return false;
            }
            if (((Weapon) w).might != this.might) {
                return false;
            }
            if (((Weapon) w).hit != this.hit) {
                return false;
            }
            if (((Weapon) w).crit != this.crit) {
                return false;
            }
            if (((Weapon) w).physical != this.physical) {
                return false;
            }
            if (((Weapon) w).Uses != this.Uses) {
                return false;
            }
            if (((Weapon) w).level != this.level) {
                return false;
            }
            if (!((Weapon) w).slayer.equals(this.slayer)) {
                return false;
            }
        }
        return true;
    }

    public int getMight() {
        return might;
    }

    public int getWeight() {
        return weight;
    }

    public int getHit() {
        return hit;
    }

    public int getCrit() {
        return crit;
    }

    public int getRange_1() {
        return range_1;
    }

    public int getRange_2() {
        return range_2;
    }

    public int getExp() {
        return exp;
    }

    public boolean isPhysical() {
        return physical;
    }

    public int getUses() {
        return uses;
    }

    public void setUses(int val) {
        uses = val;
    }

    public java.lang.Character getUser() {
        return user;
    }

    public static void main(String[] args) {
        try {
            Weapon fire = Weapon.Magic(WeaponType.Fire,WeaponLevel.E);
            Gson gson = new GsonBuilder().registerTypeAdapter(Fire.class, new FireTypeAdapter()).create();
            String str = gson.toJson(fire);
            System.out.println(str);
            Fire n = gson.fromJson(str,Fire.class);
            System.out.println(n.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
