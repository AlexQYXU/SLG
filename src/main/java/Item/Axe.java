package Item;

import Characters.Type;

import java.awt.image.BufferedImage;
import java.util.HashSet;

public class Axe extends Weapon {

    public Axe(String name, int might, int weight, WeaponLevel level, int hit, int uses, int crit, int range_1, int range_2, int exp,
               boolean physical, int price, BufferedImage image, HashSet<Type> slayer,boolean drop) {
        super(name, might, weight, level, hit, uses, crit, range_1, range_2, exp, physical, price, image, slayer,drop);
    }
    public Axe(String name, int might, int weight, WeaponLevel level, int hit, int uses, int crit, int range_1, int range_2, int exp,
               boolean physical, int price, BufferedImage image, HashSet<Type> slayer) {
        super(name, might, weight, level, hit, uses, crit, range_1, range_2, exp, physical, price, image, slayer);
    }

    @Override
    public WeaponType getType() {
        return WeaponType.Axe;
    }

    @Override
    public Axe getCopy() {
        return new Axe(this.name, this.might, this.weight, this.level, this.hit, this.Uses, this.crit, this.range_1, this.range_2
                , this.exp, this.physical, this.price, this.image, this.slayer,this.isDropable());
    }
}
