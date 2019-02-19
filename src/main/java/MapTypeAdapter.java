import Characters.Character;
import Item.Envir_Item;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class MapTypeAdapter extends TypeAdapter<Map> {
    Gson gson = new GsonBuilder().create();
    JsonParser parser = new JsonParser();

    @Override
    public void write(JsonWriter out, Map map) throws IOException {
        out.beginObject();
        out.name("Map path").value(map.map_path);
        out.name("TileWidth").value(map.TileWidth);
        out.name("TileHeight").value(map.TileHeight);
        out.name("map_x").value(map.map_x);
        out.name("map_y").value(map.map_y);
        out.name("move_map").value(gson.toJson(map.move_map));
        JsonArray array = new JsonArray();
        for (Node n : map.original_position) {
            array.add(gson.toJson(n));
        }
        out.name("Original Position").value(array.toString());

        out.endObject();
    }

    @Override
    public Map read(JsonReader in) throws IOException {
        Map map = new Map();
        in.beginObject();
        while (in.hasNext()) {
            switch (in.nextName()) {
                case "Map path":
                    map.map_path = in.nextString();
                    System.out.println(map.map_path);
                    try {
                        map.map = ImageIO.read(new File(map.map_path));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "TileWidth":
                    map.TileWidth = in.nextInt();
                    break;
                case "TileHeight":
                    map.TileHeight = in.nextInt();
                    break;
                case "map_x":
                    map.map_x = in.nextInt();
                    break;
                case "map_y":
                    map.map_y = in.nextInt();
                    break;
                case "move_map":
                    map.move_map = gson.fromJson(in.nextString(), int[][].class);
                    break;
                case "Original Position":
                    map.original_position = new HashSet<>();
                    JsonArray array = parser.parse(in.nextString()).getAsJsonArray();
                    for (JsonElement e : array) {

                        map.original_position.add(gson.fromJson(e.getAsString(), Node.class));
                    }
                    break;
                default:
                    break;
            }

        }
        in.endObject();
        map.character_map = new Character[map.map_x][map.map_y];
        map.terrain_map = new Envir_Item[map.map_x][map.map_y];
        return map;
    }

    public static void main(String[] args) {
      ArrayList<Node> list =new ArrayList<>();
      System.out.println("[");
      for(Node n : list){
          System.out.println(n);
      }
      System.out.println("]");

    }
}
