package Item;

import Characters.Type;

import java.awt.image.BufferedImage;
import java.util.HashSet;

public class Sword extends Weapon {

    public Sword(String name, int might, int weight, WeaponLevel level, int hit, int uses, int crit, int range_1, int range_2, int exp, boolean physical, int price, BufferedImage image, HashSet<Type> slayer) {
        super(name, might, weight, level, hit, uses, crit, range_1, range_2, exp, physical, price, image, slayer);
    }
    public Sword(String name, int might, int weight, WeaponLevel level, int hit, int uses, int crit, int range_1, int range_2, int exp, boolean physical,
                 int price, BufferedImage image, HashSet<Type> slayer,boolean drop) {
        super(name, might, weight, level, hit, uses, crit, range_1, range_2, exp, physical, price, image, slayer,drop);
    }

    @Override
    public WeaponType getType() {
        return WeaponType.Sword;
    }

    @Override
    public Sword getCopy() {
        return new Sword(this.name, this.might, this.weight, this.level, this.hit, this.Uses, this.crit, this.range_1, this.range_2, this.exp, this.physical, this.price, this.image, this.slayer,this.isDropable());
    }
}
