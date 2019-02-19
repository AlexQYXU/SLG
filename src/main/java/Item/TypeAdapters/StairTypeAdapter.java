package Item.TypeAdapters;

import Characters.Character;
import Characters.CharacterTypeAdapter;
import Item.Envir_Items.Stair;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class StairTypeAdapter extends TypeAdapter<Stair> {
    Gson gson = new GsonBuilder().registerTypeAdapter(Character.class, new CharacterTypeAdapter()).create();

    @Override
    public void write(JsonWriter out, Stair stair) throws IOException {
        out.beginObject();
        out.name("Class Name").value(Stair.class.getName());
        out.name("X").value(stair.getX());
        out.name("Y").value(stair.getY());
        out.name("Start_Turn").value(stair.getStart_turn());
        out.name("End_Turn").value(stair.getEnd_turn());
        out.name("Step").value(stair.getStep());
        out.name("Inside").value(stair.getInside() != null ? gson.toJson(stair.getInside()) : "Null");
        out.endObject();
    }

    @Override
    public Stair read(JsonReader in) throws IOException {
        int X = 0;
        int Y = 0;
        int start_turn = 0, end_turn = 0, step = 0;
        Character inside = null;
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
                case "Start_Turn":
                    start_turn = in.nextInt();
                    break;
                case "End_Turn":
                    end_turn = in.nextInt();
                    break;
                case "Step":
                    step = in.nextInt();
                    break;
                case "Inside":
                    String str = in.nextString();
                    if (!str.equals("Null")) {
                        inside = gson.fromJson(str, Character.class);
                    }
                    break;
                default:
                    break;
            }
        }
        in.endObject();
        return new Stair(X, Y, start_turn, end_turn, step, inside);
    }
}
