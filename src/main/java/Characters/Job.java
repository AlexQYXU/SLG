package Characters;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public enum Job {
    Lord,Fighter, Archer, Cavalier, Knight, Scout, Mercenary, Thief,Mage, Cleric,Soldier,Villager;

    public static void main(String[] args) {
        try {
            String path = System.getProperty("user.dir") + "/src/main/java/Characters/";
            System.out.println(Job.Fighter.toString());
            String profile_name = Job.Fighter.toString() + "_" + 1 + ".png";
            System.out.println(profile_name);
            BufferedImage profile = ImageIO.read(new File(path + profile_name));
        } catch (IOException e) {
        }
    }

}
