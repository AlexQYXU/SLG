package Item;

public class BowBuilder extends WeaponBuilder<Bow> {

    @Override
    public Bow build() {
        return new Bow(this.name, this.might, this.weight, this.level, this.hit, this.uses, this.crit, this.range_1, this.range_2, this.exp, this.physical, this.price,this.image,this.slayer);
    }
}
