package Item;

import Characters.Character;
import Characters.Type;

import java.awt.image.BufferedImage;
import java.util.HashSet;

/***
 * A special type of Weapon which usually deliver no damage to enemies.
 * Special effects includes Healing, Unlocking (doors or chests), Warping and Rescuing (may be considered as one effect)
 */

public class Staff<T extends MagicEffect> extends Weapon {

    final T magicEffect;

    public Staff(String name, int might, int weight, WeaponLevel level, int hit, int uses, int crit, int range_1, int range_2, int exp, boolean physical, int price, BufferedImage image, HashSet<Type> slayer, T magicEffect) {
        super(name, might, weight, level, hit, uses, crit, range_1, range_2, exp, physical, price, image, slayer);
        this.magicEffect = magicEffect;
    }


    public Staff(String name, int might, int weight, WeaponLevel level, int hit, int uses, int crit, int range_1, int range_2
            , int exp, boolean physical, int price, BufferedImage image, HashSet<Type> slayer, T magicEffect, boolean drop) {
        super(name, might, weight, level, hit, uses, crit, range_1, range_2, exp, physical, price, image, slayer, drop);
        this.magicEffect = magicEffect;
    }

    public void staff(Object o, Character user) {
        magicEffect.applyEffect(o, user);
        consume();
    }

    public T getMagicEffect() {
        return magicEffect;
    }

    @Override
    public WeaponType getType() {
        return WeaponType.Staff;
    }

    @Override
    public Staff getCopy() {
        return new Staff(this.name, this.might, this.weight, this.level, this.hit, this.Uses, this.crit, this.range_1, this.range_2, this.exp, this.physical, this.price, this.image, this.slayer, this.magicEffect, this.isDropable());
    }
}
