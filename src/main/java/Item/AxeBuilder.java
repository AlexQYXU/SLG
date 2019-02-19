package Item;

public class AxeBuilder extends WeaponBuilder<Axe> {
    @Override
    public Axe build() {
        return new Axe(this.name, this.might, this.weight, this.level, this.hit, this.uses, this.crit, this.range_1, this.range_2, this.exp, this.physical,this.price,this.image,this.slayer);
    }
}
