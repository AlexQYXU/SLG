package Characters;

import Item.*;
import Item.TypeAdapters.*;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;

public class TransporterTypeAdapter extends TypeAdapter<Transporter> {
    Gson gson = new GsonBuilder().registerTypeAdapter(Axe.class, new AxeTypeAdapter()).registerTypeAdapter(Sword.class, new SwordTypeAdapter())
            .registerTypeAdapter(Lance.class, new LanceTypeAdapter()).registerTypeAdapter(Bow.class, new BowTypeAdapter())
            .registerTypeAdapter(Wind.class, new WindTypeAdapter()).registerTypeAdapter(Fire.class, new FireTypeAdapter())
            .registerTypeAdapter(Thunder.class, new ThroneTypeAdapter()).registerTypeAdapter(HealingItem.class, new HealingItemTypeAdapter()).create();
    JsonParser parser = new JsonParser();

    @Override
    public void write(JsonWriter out, Transporter transporter) throws IOException {
        out.beginObject();
        out.name("swords").value(toJString(transporter.swords));
        out.name("lances").value(toJString(transporter.lances));
        out.name("axes").value(toJString(transporter.axes));
        out.name("bows").value(toJString(transporter.bows));
        out.name("winds").value(toJString(transporter.winds));
        out.name("fires").value(toJString(transporter.fires));
        out.name("thunders").value(toJString(transporter.thunders));
        out.name("staff").value(toJString(transporter.staff));
        out.name("others").value(toJString(transporter.others));
        out.endObject();
    }

    @Override
    public Transporter read(JsonReader in) throws IOException {
        JsonArray array;
        Transporter transporter = new Transporter();
        ArrayList<Sword> swords = new ArrayList<>();
        ArrayList<Lance> lances = new ArrayList<>();
        ArrayList<Axe> axes = new ArrayList<>();
        ArrayList<Bow> bows = new ArrayList<>();
        ArrayList<Wind> winds = new ArrayList<>();
        ArrayList<Fire> fires = new ArrayList<>();
        ArrayList<Thunder> thunders = new ArrayList<>();
        ArrayList<Staff> staff = new ArrayList<>();
        ArrayList<Item> others = new ArrayList<>();
        in.beginObject();
        while (in.hasNext()) {
            switch (in.nextName()) {
                case "swords":
                    array = parser.parse(in.nextString()).getAsJsonArray();
                    for (JsonElement e : array) {
                        swords.add(gson.fromJson(e.getAsString(), Sword.class));
                    }
                    transporter.setSwords(swords);
                    break;
                case "lances":
                    array = parser.parse(in.nextString()).getAsJsonArray();
                    for (JsonElement e : array) {
                        lances.add(gson.fromJson(e.getAsString(), Lance.class));
                    }
                    transporter.setLances(lances);
                    break;
                case "axes":
                    array = parser.parse(in.nextString()).getAsJsonArray();
                    for (JsonElement e : array) {
                        axes.add(gson.fromJson(e.getAsString(), Axe.class));
                    }
                    transporter.setAxes(axes);
                    break;
                case "bows":
                    array = parser.parse(in.nextString()).getAsJsonArray();
                    for (JsonElement e : array) {
                        bows.add(gson.fromJson(e.getAsString(), Bow.class));
                    }
                    transporter.setBows(bows);
                    break;
                case "winds":
                    array = parser.parse(in.nextString()).getAsJsonArray();
                    for (JsonElement e : array) {
                        winds.add(gson.fromJson(e.getAsString(), Wind.class));
                    }
                    transporter.setWinds(winds);
                    break;
                case "fires":
                    array = parser.parse(in.nextString()).getAsJsonArray();
                    for (JsonElement e : array) {
                        fires.add(gson.fromJson(e.getAsString(), Fire.class));
                    }
                    transporter.setFires(fires);
                    break;
                case "thunders":
                    array = parser.parse(in.nextString()).getAsJsonArray();
                    for (JsonElement e : array) {
                        thunders.add(gson.fromJson(e.getAsString(), Thunder.class));
                    }
                    transporter.setThunders(thunders);
                    break;
                case "staff":
                    array = parser.parse(in.nextString()).getAsJsonArray();
                    for (JsonElement e : array) {
                        staff.add(gson.fromJson(e.getAsString(), Staff.class));
                    }
                    transporter.setStaff(staff);
                    break;
                case "others":
                    array = parser.parse(in.nextString()).getAsJsonArray();
                    for (JsonElement e : array) {
                        JsonObject o = parser.parse(e.getAsString()).getAsJsonObject();
                        JsonPrimitive prim = (JsonPrimitive) o.get("Class Name");
                        Class<?> klass = null;
                        try {
                            klass = Class.forName(prim.getAsString());
                        } catch (ClassNotFoundException ce) {
                            ce.printStackTrace();
                        }
                        others.add((Item)gson.fromJson(e.getAsString(),klass));
                    }
                    transporter.setOthers(others);
                    break;
                default:
                    break;

            }
        }
        in.endObject();
        return transporter;
    }

    public String toJString(ArrayList<? extends Item> list) {
        JsonArray array = new JsonArray();
        for (Item item : list) {
            array.add(gson.toJson(item, item.getClass()));
        }
        return array.toString();
    }


}
