package Item;

public class FireBuilder extends WeaponBuilder<Fire> {
    @Override
    public Fire build() {
        return new Fire(this.name, this.might, this.weight, this.level, this.hit, this.uses, this.crit, this.range_1, this.range_2, this.exp, this.physical,this.price,this.image,this.slayer);
    }
}
