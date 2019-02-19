package Item.Envir_Items;

import Characters.Character;
import Item.Envir_Item;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Fort extends Envir_Item implements Reinforcement {
    Character inside;
    int start_turn;
    int end_turn;
    int step;

    public Fort(int X, int Y, int start_turn, int step, int end_turn, Character inside) {
        try {
            image = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Environment/Fort.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        width = 1;
        height = 1;
        this.name = "Fort";
        this.X = X;
        this.Y = Y;
        this.start_turn = start_turn;
        this.end_turn = end_turn;
        this.step = step;
        this.inside = inside;
        this.DEF_Bonus = 2;
        this.AVO_Bonus = 20;
        this.HEAL_Bonus = 10;
    }

    public Character getInside() {
        return inside;
    }

    public int getStart_turn() {
        return start_turn;
    }

    public int getEnd_turn() {
        return end_turn;
    }

    public int getStep() {
        return step;
    }

    @Override
    public Character JumpOut(int turn) {
        if (turn >= start_turn && turn <= end_turn && (turn - start_turn) / step == 0) {
            return inside.getCopy();
        } else {
            return null;
        }
    }
}
