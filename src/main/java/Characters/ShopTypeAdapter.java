package Characters;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.HashSet;

import Item.TypeAdapters.*;
import Item.*;

public class ShopTypeAdapter extends TypeAdapter<Shop> {
    Gson gson = new GsonBuilder().registerTypeAdapter(Axe.class, new AxeTypeAdapter()).registerTypeAdapter(Sword.class, new SwordTypeAdapter())
            .registerTypeAdapter(Lance.class, new LanceTypeAdapter()).registerTypeAdapter(Bow.class, new BowTypeAdapter())
            .registerTypeAdapter(Wind.class, new WindTypeAdapter()).registerTypeAdapter(Fire.class, new FireTypeAdapter()).registerTypeAdapter(Staff.class,new StaffTypeAdapter())
            .registerTypeAdapter(Thunder.class, new ThunderTypeAdapter()).registerTypeAdapter(HealingItem.class,new HealingItemTypeAdapter()).create();
    JsonParser parser = new JsonParser();

    @Override
    public void write(JsonWriter out, Shop shop) throws IOException {
        out.beginObject();
        JsonArray list = new JsonArray();
        for (Item item : shop.goodslist) {
            System.out.println(item.getName());
            list.add(gson.toJson(item, item.getClass()));
        }
        out.name("Goods").value(list.toString());
        out.endObject();
    }

    @Override
    public Shop read(JsonReader in) throws IOException {
        in.beginObject();
        HashSet<Item> list = new HashSet<Item>();
        while (in.hasNext()) {
            switch (in.nextName()) {
                case "Goods":
                    JsonArray array = parser.parse(in.nextString()).getAsJsonArray();

                    for (JsonElement e : array) {
                        JsonObject o = parser.parse(e.getAsString()).getAsJsonObject();
                        JsonPrimitive prim = o.get("Class Name").getAsJsonPrimitive();
                        Class<?> klass = null;
                        try {
                            klass = Class.forName(prim.getAsString());
                        } catch (ClassNotFoundException ce) {
                            ce.printStackTrace();
                        }
                        Item item = (Item) gson.fromJson(e.getAsString(), klass);
                        list.add(item);
                    }
                    break;
                default:
                    break;
            }
        }
        in.endObject();
        return new Shop(list);
    }
}
