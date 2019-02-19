package Item;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class StaffBuilder extends WeaponBuilder<Staff> {

    private MagicEffect magicEffect;

    public StaffBuilder withMagicEffect(MagicEffect magicEffect) {
        this.magicEffect = magicEffect;
        return this;
    }

    public void reset() {
        super.reset();
        this.magicEffect = MagicEffect.NO_EFFECT;
    }


    @Override
    public Staff build() {
        return new Staff(this.name, this.might, this.weight, this.level, this.hit, this.uses, this.crit, this.range_1, this.range_2, this.exp, this.physical, this.price, this.image, this.slayer, this.magicEffect);
    }


    public static Staff generateExampleHealStaff(WeaponLevel lv) throws IOException {
        final Staff staff;
        final BufferedImage image = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Icons/Heal.png"));
        switch (lv) {
            case E:
                staff = new StaffBuilder().withMagicEffect(new HealMagic(10)).image(image).range(1, 1).level(WeaponLevel.E).uses(40).exp(1).name("HealMagic").build();
                staff.setDescription("Restore HP to one adjacent ally.");
                return staff;
        }

        return null;
    }

}
