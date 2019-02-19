package Item;

import Characters.Type;
import Item.Envir_Items.Throne;

import java.awt.image.BufferedImage;
import java.util.HashSet;

public class Thunder extends Weapon {
    public Thunder(String name, int might, int weight, WeaponLevel level, int hit, int uses, int crit, int range_1, int range_2, int exp, boolean physical, int price,BufferedImage image, HashSet<Type> slayer) {
        super(name, might, weight, level, hit, uses, crit, range_1, range_2, exp, physical, price,image, slayer);
        slayer.add(Type.Flying);
    }
    public Thunder(String name, int might, int weight, WeaponLevel level, int hit, int uses, int crit, int range_1, int range_2, int exp,
                   boolean physical, int price,BufferedImage image, HashSet<Type> slayer,boolean drop) {
        super(name, might, weight, level, hit, uses, crit, range_1, range_2, exp, physical, price,image, slayer,drop);
        slayer.add(Type.Flying);
    }

    @Override
    public WeaponType getType() {
        return WeaponType.Thunder;
    }

    @Override
    public Thunder getCopy() {
        return new Thunder(this.name, this.might, this.weight, this.level, this.hit, this.Uses, this.crit,
                this.range_1, this.range_2, this.exp, this.physical, this.price,this.image, this.slayer,this.isDropable());
    }
}
