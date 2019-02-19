package Item.TypeAdapters;

import Item.*;
import Item.Envir_Items.Chest;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class ChestTypeAdapter extends TypeAdapter<Chest> {
    Gson gson = new GsonBuilder().registerTypeAdapter(Axe.class, new AxeTypeAdapter()).registerTypeAdapter(Sword.class, new SwordTypeAdapter()).registerTypeAdapter(Lance.class, new LanceTypeAdapter())
            .registerTypeAdapter(Bow.class, new BowTypeAdapter()).registerTypeAdapter(Wind.class, new WindTypeAdapter()).registerTypeAdapter(Fire.class, new FireTypeAdapter())
            .registerTypeAdapter(Thunder.class, new ThunderTypeAdapter()).registerTypeAdapter(Staff.class, new StaffTypeAdapter()).registerTypeAdapter(HealingItem.class, new HealingItemTypeAdapter()).create();
    JsonParser parser = new JsonParser();

    @Override
    public void write(JsonWriter out, Chest chest) throws IOException {
        out.beginObject();
        out.name("Class Name").value(Chest.class.getName());
        out.name("X").value(chest.getX());
        out.name("Y").value(chest.getY());
        out.name("isLocked").value(chest.isLocked());
        if (chest.getTreasure() != null) {
            out.name("Treasure").value(gson.toJson(chest.getTreasure()));
        } else {
            out.name("Treasure").value("Empty");
        }
        out.endObject();
    }

    @Override
    public Chest read(JsonReader in) throws IOException {
        in.beginObject();
        int X = 0, Y = 0;
        Item treasure = null;
        boolean locked = false;
        while (in.hasNext()) {
            switch (in.nextName()) {
                case "Class Name":
                    in.nextString();
                    break;
                case "X":
                    X = in.nextInt();
                    break;
                case "Y":
                    Y = in.nextInt();
                    break;
                case "isLocked":
                    locked = in.nextBoolean();
                    break;
                case "Treasure":
                    String item = in.nextString();
                    if (item == "Empty") {
                        treasure = null;
                    } else {
                        Class<?> klass = null;
                        JsonObject o = parser.parse(item).getAsJsonObject();
                        JsonPrimitive prim = (JsonPrimitive) o.get("Class Name");
                        try {
                            klass = Class.forName(prim.getAsString());
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        treasure = (Item) gson.fromJson(o, klass);
                        System.out.println("Treasure is " + treasure.getName());
                    }
                    break;
                default:
                    break;
            }
        }
        in.endObject();
        if (treasure == null && locked == false) {
            return Chest.emptyChest(X, Y);
        } else {
            return new Chest(X, Y, treasure);
        }
    }

    public static void main(String[] args) {
        ChestTypeAdapter adapter = new ChestTypeAdapter();
        try {
            Chest chest = new Chest(1, 1, Weapon.Iron_Weapon(WeaponType.Sword));
            Gson gson = new GsonBuilder().registerTypeAdapter(Chest.class, adapter).create();
            String string = gson.toJson(chest, Chest.class);
            System.out.println(string);
            Chest one = gson.fromJson(string, Chest.class);
           System.out.println(gson.toJson(one,Chest.class));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
