import Characters.Character;
import Characters.CharacterTypeAdapter;
import Characters.Transporter;
import Characters.TransporterTypeAdapter;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class SaveTypeAdapter extends TypeAdapter<SAVE> {
    Gson gson = new GsonBuilder().registerTypeAdapter(Character.class, new CharacterTypeAdapter())
            .registerTypeAdapter(Transporter.class, new TransporterTypeAdapter()).registerTypeAdapter(Chapter.class, new ChapterTypeAdapter()).create();
    JsonParser parser = new JsonParser();

    @Override
    public void write(JsonWriter out, SAVE save) throws IOException {
        out.beginObject();
        out.name("PLAYER").value(toJString(save.PLAYER));
        out.name("Market").value(toJString(save.Market));
        out.name("Grave").value(toJString(save.Grave));
        out.name("Chapters").value(fromChapters(save.chapters));
        out.name("Asset").value(save.asset);
        out.name("Transporter").value(gson.toJson(save.transporter, Transporter.class));
        System.out.println(gson.toJson(save.transporter, Transporter.class));
        out.endObject();
    }

    @Override
    public SAVE read(JsonReader in) throws IOException {
        ArrayList<Character> player = null;
        ArrayList<Character> market = null;
        ArrayList<Character> grave = null;
        ArrayList<Chapter> chapters = null;
        int asset = 0;
        Transporter transporter = null;
        in.beginObject();
        while (in.hasNext()) {
            switch (in.nextName()) {
                case "PLAYER":
                    player = toCharacters(in.nextString());
                    break;
                case "Market":
                    market = toCharacters(in.nextString());
                    break;
                case "Grave":
                    grave = toCharacters(in.nextString());
                    break;
                case "Chapters":
                    chapters = toChapters(in.nextString());
                    break;
                case "Asset":
                    asset = in.nextInt();
                    break;
                case "Transporter":
                    transporter = gson.fromJson(in.nextString(), Transporter.class);
                    break;
                default:
                    break;
            }
        }
        in.endObject();
        return new SAVE(chapters, player, grave, market, asset, transporter);
    }

    private String toJString(ArrayList<Character> list) {
        JsonArray array = new JsonArray();
        for (Character pc : list) {
            array.add(gson.toJson(pc, Character.class));
            System.out.println(gson.toJson(pc, Character.class));
        }
        return array.toString();
    }

    private ArrayList<Character> toCharacters(String string) {
        ArrayList<Character> list = new ArrayList<>();
        JsonArray array = parser.parse(string).getAsJsonArray();
        for (JsonElement e : array) {
            list.add(gson.fromJson(e.getAsString(), Character.class));
        }
        return list;
    }

    private String fromChapters(ArrayList<Chapter> list) {
        JsonArray array = new JsonArray();
        for (Chapter chapter : list) {
            array.add(gson.toJson(chapter, Chapter.class));
        }
        return array.toString();
    }

    private ArrayList<Chapter> toChapters(String string) {
        ArrayList<Chapter> list = new ArrayList<>();
        JsonArray array = parser.parse(string).getAsJsonArray();
        for (JsonElement e : array) {
            list.add(gson.fromJson(e.getAsString(), Chapter.class));
        }
        return list;
    }
}
