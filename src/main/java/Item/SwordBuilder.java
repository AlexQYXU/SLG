package Item;

public class SwordBuilder extends WeaponBuilder<Sword> {

    @Override
    public Sword build() {
        return new Sword(this.name, this.might, this.weight, this.level, this.hit, this.uses, this.crit, this.range_1, this.range_2, this.exp, this.physical,this.price,this.image,this.slayer);
    }
}
