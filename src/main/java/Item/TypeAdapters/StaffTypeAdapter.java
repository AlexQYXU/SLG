package Item.TypeAdapters;

import Characters.Type;
import Item.*;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;

public class StaffTypeAdapter extends TypeAdapter<Staff> {
    String path = System.getProperty("user.dir") + "/src/main/resources/Icons/";
    Gson gson = new GsonBuilder().registerTypeAdapter(HealMagic.class, new HealMagicTypeAdapter()).registerTypeAdapter(UnlockMagic.class, new UnlockMagicTypeAdapter()).create();

    @Override
    public void write(JsonWriter out, Staff w) throws IOException {
        out.beginObject();
        out.name("Class Name").value(Staff.class.getName());
        out.name("Name").value(w.getName());
        out.name("Might").value(w.getMight());
        out.name("Weight").value(w.getWeight());
        out.name("Weapon Level").value(w.getLevel().toString());
        out.name("Hit").value(w.getHit());
        out.name("use").value(w.getUses());
        out.name("USE").value(w.getUSES());
        out.name("Crit").value(w.getCrit());
        out.name("Range_1").value(w.getRange_1());
        out.name("Range_2").value(w.getRange_2());
        out.name("EXP").value(w.getExp());
        out.name("Physical").value(w.isPhysical());
        out.name("Price").value(w.getPrice());
        JsonArray array = new JsonArray();
        for (Type type : w.getSlayer()) {
            array.add(type.name());
        }
        out.name("Slayer").value(array.toString());
        out.name("Dropable").value(w.isDropable());
        out.name("MagicEffect").value(gson.toJson(w.getMagicEffect(), w.getMagicEffect().getClass()));
        out.name("Description").value(w.getDescription());
        out.endObject();

    }

    @Override
    public Staff read(JsonReader in) throws IOException {

        String name = "";
        int might = 0;
        int weight = 0;
        WeaponLevel level = WeaponLevel.E;
        int hit = 0;
        int crit = 0;
        int use = 0;
        int Use = 0;
        int range_1 = 0;
        int range_2 = 0;
        int exp = 0;
        MagicEffect magicEffect = null;
        boolean physical = false;boolean drop=false;
        int price = 0;
        BufferedImage image = null;
        HashSet<Type> slayer = new HashSet<>();
        in.beginObject();
        JsonParser parser = new JsonParser();
        String description = null;
        while (in.hasNext()) {
            switch (in.nextName()) {
                case "Class Name":
                    in.nextString();
                    break;
                case "Name":
                    name = in.nextString();
                    break;
                case "Might":
                    might = in.nextInt();
                    break;
                case "Weight":
                    weight = in.nextInt();
                    break;
                case "Weapon Level":
                    level = WeaponLevel.valueOf(in.nextString());
                    break;
                case "Hit":
                    hit = in.nextInt();
                    break;
                case "use":
                    use = in.nextInt();
                    break;
                case "USE":
                    Use = in.nextInt();
                    break;
                case "Crit":
                    crit = in.nextInt();
                    break;
                case "Range_1":
                    range_1 = in.nextInt();
                    break;
                case "Range_2":
                    range_2 = in.nextInt();
                    break;
                case "EXP":
                    exp = in.nextInt();
                    break;
                case "Physical":
                    physical = in.nextBoolean();
                    break;
                case "Price":
                    price = in.nextInt();
                    break;
                case "Slayer":
                    JsonArray array = parser.parse(in.nextString()).getAsJsonArray();
                    for (JsonElement e : array) {
                        slayer.add(Type.valueOf(e.getAsString()));
                    }
                    break;
                case "Dropable":
                    drop = in.nextBoolean();
                    break;
                case "MagicEffect":
                    JsonObject object = parser.parse(in.nextString()).getAsJsonObject();
                    JsonPrimitive prim = (JsonPrimitive) object.get("Class Name");
                    Class<?> klass = null;
                    try {
                        klass = Class.forName(prim.getAsString());
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    magicEffect = (MagicEffect) gson.fromJson(object, klass);
                    break;
                case "Description":
                    description = in.nextString();
                    break;
                default:
                    break;
            }
        }
        in.endObject();
        System.out.println(path + name + ".png");
        image = ImageIO.read(new File(path + name + ".png"));
        Staff w = new Staff(name, might, weight, level, hit, Use, crit, range_1, range_2, exp, physical, price, image, slayer, magicEffect,drop);
        w.setDescription(description);
        w.setUses(use);
        return w;
    }
}
