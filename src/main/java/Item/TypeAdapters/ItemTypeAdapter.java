package Item.TypeAdapters;

import com.google.gson.*;
import Item.*;

import java.lang.reflect.Type;

public class ItemTypeAdapter implements JsonSerializer<Item>, JsonDeserializer<Item> {
    Class<?> klass = null;
    private static final AxeTypeAdapter axe = new AxeTypeAdapter();
    private static final BowTypeAdapter bow = new BowTypeAdapter();
    private static final FireTypeAdapter fire = new FireTypeAdapter();
    private static final LanceTypeAdapter lance = new LanceTypeAdapter();
    private static final SwordTypeAdapter sword = new SwordTypeAdapter();
    private static final ThunderTypeAdapter thunder = new ThunderTypeAdapter();
    private static final WindTypeAdapter wind = new WindTypeAdapter();
    private static final Gson gson = new GsonBuilder().registerTypeAdapter(Axe.class, axe).registerTypeAdapter(Sword.class, sword).registerTypeAdapter(Lance.class, lance)
            .registerTypeAdapter(Bow.class, bow).registerTypeAdapter(Wind.class, wind).registerTypeAdapter(Fire.class, fire).registerTypeAdapter(Thunder.class, thunder).create();

    @Override
    public Item deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonPrimitive prim = (JsonPrimitive) jsonElement.getAsJsonObject().get("Class Name");
        try {
            klass = Class.forName(prim.getAsString());
            System.out.println("deserialize Class :" + klass.getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return jsonDeserializationContext.deserialize(jsonElement, klass);
    }

    @Override
    public JsonElement serialize(Item item, Type type, JsonSerializationContext jsonSerializationContext) {
        return null;
    }
}
