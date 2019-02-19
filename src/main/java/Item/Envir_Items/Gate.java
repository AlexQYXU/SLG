package Item.Envir_Items;

import Characters.Character;
import Characters.Job;
import Item.*;
import Item.Key.GateKey;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Gate extends Envir_Item implements Lock {

    protected boolean locked;

    public Gate(int X, int Y) {
        this.name = "Gate";
        this.setX(X);
        this.setY(Y);
        this.width = 1;
        this.height = 1;
        this.locked = true;
        try {
            image = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Environment/Gate.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isLocked() {
        return locked;
    }

    @Override
    public Object unlock(Object key) {
        if (locked == false) return this;
        if (key instanceof GateKey) {
            locked = false;
            ((Consumables) key).consume();
            return this;
        } else if (key instanceof Staff && ((Staff) key).getMagicEffect() instanceof UnlockMagic) {
            locked = false;
            ((Staff) key).consume();
        } else if (key instanceof Character && ((Character) key).getJob() == Job.Thief) {
            locked = false;
            return this;
        }
        return null;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    @Override
    public BufferedImage draw() {
        return super.draw();
    }
}
