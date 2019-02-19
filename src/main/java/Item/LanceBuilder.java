package Item;

public class LanceBuilder extends WeaponBuilder<Lance> {
    @Override
    public Lance build() {
        return new Lance(this.name, this.might, this.weight, this.level, this.hit,this.uses, this.crit, this.range_1, this.range_2, this.exp, this.physical,this.price,this.image,this.slayer);
    }
}
