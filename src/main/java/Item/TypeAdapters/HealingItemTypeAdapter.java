package Item.TypeAdapters;

import Item.HealingItem;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HealingItemTypeAdapter extends TypeAdapter<HealingItem> {
    String path = System.getProperty("user.dir") + "/src/main/resources/Icons/";

    @Override
    public void write(JsonWriter out, HealingItem item) throws IOException {
        out.beginObject();
        out.name("Class Name").value(HealingItem.class.getName());
        out.name("Name").value(item.getName());
        out.name("Value").value(item.getVal());
        out.name("uses").value(item.getUses());
        out.name("Uses").value(item.getUSES());
        out.name("Price").value(item.getPrice());
        out.name("Dropable").value(item.isDropable());
        out.endObject();
    }

    @Override
    public HealingItem read(JsonReader in) throws IOException {
        String name = "";
        int val = 0;
        int uses = 0;
        int Uses = 0;
        int price = 0;
        BufferedImage image = null;
        boolean drop = false;
        in.beginObject();
        while (in.hasNext()) {
            switch (in.nextName()) {
                case "Class Name":
                    in.nextString();
                    break;
                case "Name":
                    name = in.nextString();
                    image = ImageIO.read(new File(path + name + ".png"));
                    break;
                case "Value":
                    val = in.nextInt();
                    break;
                case "uses":
                    uses = in.nextInt();
                    break;
                case "Uses":
                    Uses = in.nextInt();
                    break;
                case "Price":
                    price = in.nextInt();
                    break;
                case "Dropable":
                    drop = in.nextBoolean();
                    break;
                default:
                    break;
            }
        }
        in.endObject();
        HealingItem item = new HealingItem(name, val, Uses, price, image,drop);
        item.setUses(uses);
        return item;
    }
}
