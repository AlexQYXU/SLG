package Item;

public class ThunderBuilder extends WeaponBuilder<Thunder> {
    @Override
    public Thunder build() {
        return new Thunder(this.name, this.might, this.weight, this.level, this.hit, this.uses, this.crit, this.range_1, this.range_2, this.exp, this.physical, this.price, this.image, this.slayer);
    }
}
