import Characters.Character;
import Characters.CharacterTypeAdapter;
import Characters.Shop;
import Characters.ShopTypeAdapter;
import Item.Envir_Item;
import Item.Envir_Items.*;
import Item.HealingItem;
import Item.TypeAdapters.*;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class ChapterTypeAdapter extends TypeAdapter<Chapter> {
    Gson gson = new GsonBuilder().registerTypeAdapter(Map.class, new MapTypeAdapter()).registerTypeAdapter(Character.class, new CharacterTypeAdapter()).registerTypeAdapter(Chest.class, new ChestTypeAdapter())
            .registerTypeAdapter(Gate.class, new GateTypeAdapter()).registerTypeAdapter(Pillar.class, new PillarTypeAdapter()).registerTypeAdapter(Throne.class, new ThroneTypeAdapter())
            .registerTypeAdapter(Shop.class, new ShopTypeAdapter()).registerTypeAdapter(HealingItem.class, new HealingItemTypeAdapter()).registerTypeAdapter(Fort.class, new FortTypeAdapter())
            .registerTypeAdapter(Stair.class, new StairTypeAdapter()).registerTypeAdapter(Hill.class, new HillTypeAdapter()).registerTypeAdapter(Forest.class, new ForestTypeAdapter())
            .registerTypeAdapter(Castle.class, new CastleTypeAdapter()).create();
    JsonParser parser = new JsonParser();

    @Override
    public void write(JsonWriter out, Chapter chapter) throws IOException {
        out.beginObject();
        JsonArray enemy = new JsonArray();
        for (Character en : chapter.ENEMY) {
            enemy.add(gson.toJson(en, Character.class));
        }
        out.name("Chapter_No").value(chapter.Chapter_No);

        out.name("ENEMY").value(enemy.toString());

        JsonArray partner = new JsonArray();
        for (Character p : chapter.PARTNER) {
            partner.add(gson.toJson(p, Character.class));
        }
        out.name("PARTNER").value(partner.toString());

        JsonArray monster = new JsonArray();
        for (Character mon : chapter.MONSTER) {
            monster.add(gson.toJson(mon, Character.class));
        }
        out.name("MONSTER").value(monster.toString());

        JsonArray envir = new JsonArray();
        for (Envir_Item item : chapter.ITEM) {
            envir.add(gson.toJson(item, item.getClass()));
        }
        out.name("Envir_Item").value(envir.toString());
        JsonArray grave = new JsonArray();
        for (Character d : chapter.Grave) {
            grave.add(gson.toJson(d, Character.class));
        }
        out.name("Grave").value(grave.toString());
        out.name("Map").value(gson.toJson(chapter.map, Map.class));
        out.name("Shop").value(gson.toJson(chapter.shop, Shop.class));
        out.name("Objective").value(chapter.objective.name());
        out.name("Seize Point").value(gson.toJson(chapter.seize_point));
        JsonArray points = new JsonArray();
        for (Node n : chapter.escape_points) {
            points.add(gson.toJson(n));
        }
        out.name("Escape Points").value(points.toString());
        out.name("Turn Limit").value(chapter.turn_limit);
        out.name("Boss").value(chapter.ENEMY.indexOf(chapter.Boss));
        out.endObject();
    }

    @Override
    public Chapter read(JsonReader in) throws IOException {
        Chapter chapter = new Chapter();
        in.beginObject();
        while (in.hasNext()) {
            switch (in.nextName()) {
                case "Chapter_No":
                    chapter.Chapter_No = in.nextInt();
                    break;
                case "ENEMY":
                    chapter.ENEMY = returnCharacters(in.nextString());
                    break;
                case "PARTNER":
                    chapter.PARTNER = returnCharacters(in.nextString());
                    break;
                case "MONSTER":
                    chapter.MONSTER = returnCharacters(in.nextString());
                    break;
                case "Grave":
                    chapter.Grave = returnCharacters(in.nextString());
                    break;
                case "Envir_Item":

                    JsonArray array = parser.parse(in.nextString()).getAsJsonArray();
                    System.out.println(array.toString());
                    for (JsonElement e : array) {
                        System.out.println("e is " + e.getAsString());
                        Class<?> klass = null;
                        JsonObject o = parser.parse(e.getAsString()).getAsJsonObject();
                        JsonPrimitive prim = (JsonPrimitive) o.get("Class Name");
                        try {
                            klass = Class.forName(prim.getAsString());
                        } catch (ClassNotFoundException ce) {
                            ce.printStackTrace();
                        }
                        chapter.ITEM.add((Envir_Item) gson.fromJson(e.getAsString(), klass));
                    }
                    break;
                case "Map":
                    chapter.map = gson.fromJson(in.nextString(), Map.class);
                    break;
                case "Shop":
                    chapter.shop = gson.fromJson(in.nextString(), Shop.class);
                    break;
                case "Objective":
                    chapter.objective = Victory_Condition.valueOf(in.nextString());
                    break;
                case "Seize Point":
                    chapter.seize_point = gson.fromJson(in.nextString(), Node.class);
                    break;
                case "Escape Points":
                    JsonArray jarray = parser.parse(in.nextString()).getAsJsonArray();
                    for (JsonElement e : jarray) {
                        chapter.escape_points.add(gson.fromJson(e.getAsString(), Node.class));
                    }
                    break;
                case "Turn Limit":
                    chapter.turn_limit = in.nextInt();
                    break;
                case "Boss":
                    chapter.Boss = chapter.ENEMY.get(in.nextInt());
                    break;
                default:
                    break;
            }
        }
        in.endObject();
        return chapter;
    }

    private final ArrayList<Character> returnCharacters(String string) {
        System.out.println(string);
        ArrayList<Character> list = new ArrayList<>();
        JsonArray array = parser.parse(string).getAsJsonArray();
        for (JsonElement element : array) {
            list.add(gson.fromJson(element.getAsString(), Character.class));
        }
        return list;
    }
}
