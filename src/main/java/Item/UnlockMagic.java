package Item;

import Characters.Character;
import Item.Envir_Items.Chest;
import Item.Envir_Items.Gate;

public class UnlockMagic implements MagicEffect {

    @Override
    public boolean isApplicable(Object destination) {
        if (destination instanceof Lock && ((Lock) destination).isLocked()) {
            return true;
        }
        return false;
    }

    @Override
    public void applyEffect(Object destination, Character user) {
        if (destination instanceof Chest) {
            Object treasure = ((Chest) destination).unlock(this);
            if (treasure instanceof Item) {
                user.gainItem((Item) treasure);
            } else if (treasure instanceof Character) {
                user.Mimic_JumpOut((Chest) destination);
            }
        } else if (destination instanceof Gate) {

        }
    }
}
