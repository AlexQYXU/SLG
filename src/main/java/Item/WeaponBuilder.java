package Item;

import Characters.Type;

import java.awt.image.BufferedImage;
import java.util.HashSet;

/***
 * This is an abstract builder of all Weapons including both Physical and Magical {@link Equipable}
 * @param <T>
 */

public abstract class WeaponBuilder<T extends Weapon> {
    protected String name;
    protected int might;
    protected int weight;
    protected WeaponLevel level;
    protected int hit;
    protected int crit;
    protected int range_1;
    protected int range_2;
    protected int uses;
    protected int Uses;
    protected int exp;
    protected boolean physical = true;
    protected BufferedImage image;
    protected int price;
    protected HashSet<Type> slayer = new HashSet<>();

    public WeaponBuilder<T> name(String name) {
        this.name = name;
        return this;
    }

    public WeaponBuilder<T> might(int val) {
        this.might = val;
        return this;
    }

    public WeaponBuilder<T> physical(boolean val) {
        this.physical = val;
        return this;
    }

    public WeaponBuilder<T> price(int val) {
        this.price = val;
        return this;
    }

    public WeaponBuilder<T> level(WeaponLevel lv) {
        this.level = lv;
        return this;
    }

    public WeaponBuilder<T> weight(int val) {
        this.weight = val;
        return this;
    }


    public WeaponBuilder<T> hit(int val) {
        if (val > 0) this.hit = val;
        return this;
    }

    public WeaponBuilder<T> crit(int val) {
        if (val >= 0) this.crit = val;
        return this;
    }

    public WeaponBuilder<T> range(int val_1, int val_2) {
        if (val_1 > 0 && val_1 <= val_2) {
            this.range_1 = val_1;
            this.range_2 = val_2;
        }
        return this;
    }

    public WeaponBuilder<T> exp(int val) {
        if (val >= 0) this.exp = val;
        return this;
    }

    public WeaponBuilder<T> uses(int val) {
        if (val > 0) this.Uses = val;
        this.uses = val;
        return this;
    }

    public WeaponBuilder<T> image(BufferedImage image) {
        this.image = image;
        return this;
    }

    public WeaponBuilder<T> slayer(Type type) {
        this.slayer.add(type);
        return this;
    }

    public abstract T build();

    // TODO reset basic parameters
    protected void reset() {

    }


}

