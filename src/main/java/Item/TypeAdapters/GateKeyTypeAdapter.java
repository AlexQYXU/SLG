package Item.TypeAdapters;

import Item.Key.GateKey;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class GateKeyTypeAdapter extends TypeAdapter<GateKey> {
    @Override
    public void write(JsonWriter out, GateKey key) throws IOException {
        out.beginObject();
        out.name("Class Name").value(GateKey.class.getName());
        out.name("Dropable").value(key.isDropable());
        out.endObject();
    }

    @Override
    public GateKey read(JsonReader in) throws IOException {
        in.beginObject();
        GateKey key = null;
        while (in.hasNext()) {
            switch (in.nextName()) {
                case "Class Name":
                    key = new GateKey();
                    System.out.println("new gate key");
                    System.out.println(key.getName());
                    in.nextString();
                    break;
                case "Dropable":
                   key.setDropable(in.nextBoolean());
                   System.out.println("set drop");
                    break;
                default:
                    break;
            }
        }
        in.endObject();
        System.out.println(key.getName());
        return key;
    }
}
