package Item.TypeAdapters;

import Characters.Character;
import Characters.CharacterTypeAdapter;
import Item.Envir_Items.Fort;
import Item.Envir_Items.Throne;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class FortTypeAdapter extends TypeAdapter<Fort> {
    Gson gson = new GsonBuilder().registerTypeAdapter(Character.class, new CharacterTypeAdapter()).create();

    @Override
    public void write(JsonWriter out, Fort fort) throws IOException {
        out.beginObject();
        out.name("Class Name").value(Fort.class.getName());
        out.name("X").value(fort.getX());
        out.name("Y").value(fort.getY());
        out.name("Start_Turn").value(fort.getStart_turn());
        out.name("End_Turn").value(fort.getEnd_turn());
        out.name("Step").value(fort.getStep());
//        String inside = fort.getName() == null ? gson.toJson(fort.getInside()) : "Null";
        out.name("Inside").value(fort.getInside() != null ? gson.toJson(fort.getInside()) : "Null");
        out.endObject();
    }

    @Override
    public Fort read(JsonReader in) throws IOException {
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
                    System.out.println("Inside: " + str);
                    if (!str.equals("Null")) {
                        System.out.println(false);
                        inside = gson.fromJson(str, Character.class);
                    }
                    break;
                default:
                    break;
            }
        }
        in.endObject();
        return new Fort(X, Y, start_turn, end_turn, step, inside);
    }
}
