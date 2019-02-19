package Item.TypeAdapters;

import Item.Envir_Items.Pillar;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class PillarTypeAdapter extends TypeAdapter<Pillar> {
    @Override
    public void write(JsonWriter out, Pillar pillar) throws IOException {
        out.beginObject();
        out.name("Class Name").value(Pillar.class.getName());
        out.name("X").value(pillar.getX());
        out.name("Y").value(pillar.getY());
        out.endObject();
    }

    @Override
    public Pillar read(JsonReader in) throws IOException {
        int X = 0, Y = 0;
        in.beginObject();
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
        return new Pillar(X, Y);
    }
}
