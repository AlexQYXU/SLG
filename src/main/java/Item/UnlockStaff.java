package Item;

import Characters.Type;

import java.awt.image.BufferedImage;
import java.util.HashSet;

public class UnlockStaff extends Staff<UnlockMagic> {

    public UnlockStaff(String name, int might, int weight, WeaponLevel level, int hit, int uses, int crit, int range_1, int range_2, int exp, boolean physical,int price, BufferedImage image, HashSet<Type> slayer, UnlockMagic magic) {
        super(name, might, weight, level, hit, uses, crit, range_1, range_2, exp, physical, price,image,slayer ,magic);
    }
}
