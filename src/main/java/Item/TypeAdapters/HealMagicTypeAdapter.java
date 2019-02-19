package Item.TypeAdapters;

import Item.HealMagic;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class HealMagicTypeAdapter extends TypeAdapter<HealMagic> {
    int val;

    @Override
    public void write(JsonWriter out, HealMagic healMagic) throws IOException {
        out.beginObject();
        out.name("Class Name").value(HealMagic.class.getName());
        out.name("Value").value(healMagic.getVal());
        out.endObject();
    }

    @Override
    public HealMagic read(JsonReader in) throws IOException {
        in.beginObject();
        while (in.hasNext()) {
            switch (in.nextName()) {
                case "Class Name":
                    in.nextString();
                    break;
                case "Value":
                    val = in.nextInt();
                    break;
                default:
                    break;
            }
        }
        in.endObject();
        return  new HealMagic(val);
    }
}
