package Item.Envir_Items;

import Characters.Character;
import Item.Envir_Item;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Stair extends Envir_Item implements Reinforcement {
    Character inside;
    int start_turn;
    int end_turn;
    int step;

    public Stair(int X, int Y, int start_turn, int step, int end_turn, Character inside) {
        try {
            image = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Environment/Stair.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        width = 1;
        height = 1;
        this.name = "Stair";
        this.X = X;
        this.Y = Y;
        this.start_turn = start_turn;
        this.end_turn = end_turn;
        this.step = step;
        this.inside = inside;
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
        if (turn >= start_turn && turn <= end_turn && (turn - start_turn) % step == 0 && inside!=null) {
            System.out.println(inside.getName());
            return inside.getCopy();
        } else {
            System.out.println("null");
            return null;
        }
    }
}
