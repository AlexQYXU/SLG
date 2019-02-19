package Item.TypeAdapters;

import Item.Key.ChestKey;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class ChestKeyTypeAdapter extends TypeAdapter<ChestKey> {
    @Override
    public void write(JsonWriter out, ChestKey key) throws IOException {
        out.beginObject();
        out.name("Class Name").value(ChestKey.class.getName());
        out.name("uses").value(key.getUses());
        out.name("Dropable").value(key.isDropable());
        out.endObject();
    }

    @Override
    public ChestKey read(JsonReader in) throws IOException {
        in.beginObject();
        ChestKey key = null;
        while (in.hasNext()) {
            switch (in.nextName()) {
                case "Class Name":
                    in.nextString();
                    break;
                case "uses":
                    key = new ChestKey(in.nextInt());
                    break;
                case "Dropable":
                    key.setDropable(in.nextBoolean());
                    break;
                default:
                    break;
            }
        }
        in.endObject();
        return key;
    }
}
