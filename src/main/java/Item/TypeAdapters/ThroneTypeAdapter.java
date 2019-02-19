package Item.TypeAdapters;


import Item.Envir_Items.Throne;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class ThroneTypeAdapter extends TypeAdapter<Throne> {
    @Override
    public void write(JsonWriter out, Throne throne) throws IOException {
        out.beginObject();
        out.name("Class Name").value(Throne.class.getName());
        out.name("X").value(throne.getX());
        out.name("Y").value(throne.getY());
        out.endObject();
    }

    @Override
    public Throne read(JsonReader in) throws IOException {
        in.beginObject();
        int X = 0;
        int Y = 0;
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
                default:
                    break;
            }
        }
        in.endObject();
        return new Throne(X, Y);
    }

}
