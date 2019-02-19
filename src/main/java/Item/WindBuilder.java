package Item;

public class WindBuilder extends WeaponBuilder<Wind> {
    @Override
    public Wind build() {
        return new Wind(this.name, this.might, this.weight, this.level, this.hit, this.uses, this.crit, this.range_1, this.range_2, this.exp, this.physical, this.price, this.image, this.slayer);
    }
}
