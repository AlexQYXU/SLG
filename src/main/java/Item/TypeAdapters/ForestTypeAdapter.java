package Item.TypeAdapters;

import Item.Envir_Items.Castle;
import Item.Envir_Items.Forest;
import Item.Envir_Items.Fort;
import Item.Envir_Items.Throne;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class ForestTypeAdapter extends TypeAdapter<Forest> {
    @Override
    public void write(JsonWriter out, Forest forest) throws IOException {
        out.beginObject();
        out.name("Class Name").value(Forest.class.getName());
        out.name("X").value(forest.getX());
        out.name("Y").value(forest.getY());
        out.endObject();
    }

    @Override
    public Forest read(JsonReader in) throws IOException {
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
        return new Forest(X, Y);
    }
}
