package Item.TypeAdapters;

import Item.Envir_Items.Gate;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class GateTypeAdapter extends TypeAdapter<Gate> {
    @Override
    public void write(JsonWriter out, Gate gate) throws IOException {
        out.beginObject();
        out.name("Class Name").value(Gate.class.getName());
        out.name("X").value(gate.getX());
        out.name("Y").value(gate.getY());
        out.endObject();
    }

    @Override
    public Gate read(JsonReader in) throws IOException {
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
        return new Gate(X,Y);
    }
}
