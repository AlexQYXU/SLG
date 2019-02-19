package Item;

import Characters.Character;

public class HealMagic implements MagicEffect {

    private final int val;

    public HealMagic(int val) {
        this.val = val;
    }

    @Override
    public boolean isApplicable(Object destination) {
        if (destination == null) return false;

        if (destination instanceof Character && ((Character) destination).gethp() < ((Character) destination).getHP()) {
            return true;
        }
        System.out.println(destination.getClass());
        return false;
    }

    @Override
    public void applyEffect(Object destination, Character user) {
        if (destination instanceof Character) {
            Character c = (Character) destination;
            if (c.getHP() > c.gethp()) {
                c.sethp(Math.min(c.getHP(), c.gethp() + val + user.getMag()));
            }
        }
    }

    public int getVal() {
        return val;
    }
}
