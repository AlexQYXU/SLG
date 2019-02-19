package Item.TypeAdapters;

import Characters.Type;
import Item.Envir_Items.Castle;
import Item.Envir_Items.Hill;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class HillTypeAdapter extends TypeAdapter<Hill> {
    @Override
    public void write(JsonWriter out, Hill hill) throws IOException {
        out.beginObject();
        out.name("Class Name").value(Hill.class.getName());
        out.name("X").value(hill.getX());
        out.name("Y").value(hill.getY());
        out.endObject();
    }

    @Override
    public Hill read(JsonReader in) throws IOException {
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
        return new Hill(X, Y);
    }
}
