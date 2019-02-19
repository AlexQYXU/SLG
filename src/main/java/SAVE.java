import Characters.Character;
import Characters.Transporter;

import java.util.ArrayList;
import java.util.LinkedList;

public class SAVE {
    ArrayList<Character> PLAYER;
    ArrayList<Chapter> chapters;
    ArrayList<Character> Market;
    ArrayList<Character> Grave;
    int asset;
    Transporter transporter;

    public SAVE(ArrayList<Chapter> chapters, ArrayList<Character> player, ArrayList<Character> grave, ArrayList<Character> market, int asset, Transporter transporter) {
        this.chapters = chapters;
        this.PLAYER = player;
        this.Grave = grave;
        this.Market = market;
        this.asset = asset;
        this.transporter = transporter;
    }

}
