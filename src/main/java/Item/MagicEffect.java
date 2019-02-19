package Item;
import Characters.Character;

public interface MagicEffect {

    MagicEffect NO_EFFECT = new MagicEffect() {
        @Override
        public boolean isApplicable(Object destination) {
            return false;
        }

        @Override
        public void applyEffect(Object destination, Character user) {
            // do nothing!
        }
    };
    /**
     * Check if this effect is applicable to given objective, e,g, Item.HealMagic Effect applies to Ally but not Enemy
     * This method may be moved to Item.Item
     * @param destination is either Characters.Character or Environmental Element e.g. Chest, Door,
     * @return
     */
    boolean isApplicable(Object destination);


    void applyEffect(Object destination, Character user);

}
