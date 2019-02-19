package Characters;

import Item.Item;
import Item.*;
import Item.Key.ChestKey;
import Item.Key.GateKey;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import Item.TypeAdapters.*;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class CharacterTypeAdapter extends TypeAdapter<Character> {
    String path = System.getProperty("user.dir") + "/src/main/resources/Characters/";
    private JsonParser parser = new JsonParser();
    private static final AxeTypeAdapter axe = new AxeTypeAdapter();
    private static final BowTypeAdapter bow = new BowTypeAdapter();
    private static final FireTypeAdapter fire = new FireTypeAdapter();
    private static final LanceTypeAdapter lance = new LanceTypeAdapter();
    private static final SwordTypeAdapter sword = new SwordTypeAdapter();
    private static final ThunderTypeAdapter thunder = new ThunderTypeAdapter();
    private static final WindTypeAdapter wind = new WindTypeAdapter();
    private static final HealingItemTypeAdapter healitem = new HealingItemTypeAdapter();
    private static final StaffTypeAdapter staff = new StaffTypeAdapter();
    private static final Gson gson = new GsonBuilder().registerTypeAdapter(Axe.class, axe).registerTypeAdapter(Sword.class, sword).registerTypeAdapter(Lance.class, lance)
            .registerTypeAdapter(Bow.class, bow).registerTypeAdapter(Wind.class, wind).registerTypeAdapter(Fire.class, fire).registerTypeAdapter(Thunder.class, thunder)
            .registerTypeAdapter(Staff.class, staff).registerTypeAdapter(HealingItem.class, healitem).registerTypeAdapter(GateKey.class, new GateKeyTypeAdapter())
            .registerTypeAdapter(ChestKey.class, new ChestKeyTypeAdapter()).create();


    @Override
    public void write(JsonWriter writer, Character c) throws IOException {
        writer.beginObject();
        writer.name("Name").value(c.getName());
        writer.name("Gender").value(c.getGender().name());
        writer.name("Camp").value(c.getCamp().name());
        writer.name("Job").value(c.getJob().name());
        writer.name("Level").value(c.getLv());
        writer.name("EXP").value(c.getExp());
        writer.name("x").value(c.x);
        writer.name("y").value(c.y);

        JsonArray weapon_ability = new JsonArray();
        for (WeaponLevel lv : c.weapon_ability) {
            weapon_ability.add(lv.name());
        }
        writer.name("Weapon Ability").value(weapon_ability.toString());
        writer.name("Weapon Exp").value(gson.toJson(c.weapon_exp, int[].class));

        writer.name("Profile").value(c.profile_path);
        writer.name("Character_Image").value(c.image_path);
        writer.name("HP").value(c.getHP());
        writer.name("Str").value(c.getStr());
        writer.name("Mag").value(c.getMag());
        writer.name("Skl").value(c.getSkl());
        writer.name("Spd").value(c.getSpd());
        writer.name("Luk").value(c.getLuk());
        writer.name("Def").value(c.getDef());
        writer.name("Res").value(c.getRes());
        writer.name("Build").value(c.getBuild());
        writer.name("Move").value(c.getMove());
        JsonArray types = new JsonArray();
        for (Type type : c.types) {
            types.add(type.name());
        }
        writer.name("Types").value(types.toString());
        writer.name("Growth").value(gson.toJson(c.getGrowth(), int[].class));
        writer.name("AI").value(c.getAi().name());
        JsonArray bag = new JsonArray();
        for (Item item : c.getBag()) {
            System.out.println(item.getName() + "  " + item.getClass().getName());
            bag.add(gson.toJson(item, item.getClass()));
        }
        writer.name("Bag").value(bag.toString());
        System.out.println(bag.toString());
        Item item = c.getWant();
        String want = item != null ? gson.toJson(item, item.getClass()) : "Null";
        writer.name("Want").value(want);
        writer.endObject();
    }

    @Override
    public Character read(JsonReader in) throws IOException {
        Character c = new Character();
        in.beginObject();
        while (in.hasNext()) {
            switch (in.nextName()) {
                case "Name":
                    c.setName(in.nextString());
                    break;
                case "Gender":
                    c.setGender(Gender.valueOf(in.nextString()));
                    break;
                case "Camp":
                    c.setCamp(Camp.valueOf(in.nextString()));
                    break;
                case "Job":
                    c.setJob(Job.valueOf(in.nextString()));
                    break;
                case "Level":
                    c.setLv(Integer.parseInt(in.nextString()));
                    break;
                case "EXP":
                    c.setExp(Integer.parseInt(in.nextString()));
                    break;
                case "x":
                    c.x = Integer.parseInt(in.nextString());
                    break;
                case "y":
                    c.y = Integer.parseInt(in.nextString());
                    break;
                case "Weapon Ability":
                    JsonElement element = parser.parse(in.nextString());
                    JsonArray array = element.getAsJsonArray();
                    for (int i = 0; i < 8; i++) {
                        String lv = array.get(i).getAsString();
                        c.weapon_ability[i] = WeaponLevel.valueOf(lv);
                    }
                    break;
                case "Weapon Exp":
                    c.setWeapon_exp(gson.fromJson(in.nextString(), int[].class));
                    break;
                case "Profile":
                    c.profile_path = in.nextString();
                    System.out.println(path + c.getJob().toString() + "_" + c.profile_path + ".png");
                    c.profile = ImageIO.read(new File(c.profile_path));
                    break;
                case "Character_Image":
                    c.image_path = in.nextString();
                    c.character_Image = ImageIO.read(new File(c.image_path));
                    break;
                case "HP":
                    c.setHP(Integer.parseInt(in.nextString()));
                    c.sethp(c.getHP());
                    break;
                case "Str":
                    c.setStr(Integer.parseInt(in.nextString()));
                    break;
                case "Mag":
                    c.setMag(Integer.parseInt(in.nextString()));
                    break;
                case "Skl":
                    c.setSkl(Integer.parseInt(in.nextString()));
                    break;
                case "Spd":
                    c.setSpd(Integer.parseInt(in.nextString()));
                    break;
                case "Luk":
                    c.setLuk(Integer.parseInt(in.nextString()));
                    break;
                case "Def":
                    c.setDef(Integer.parseInt(in.nextString()));
                    break;
                case "Res":
                    c.setRes(Integer.parseInt(in.nextString()));
                    break;
                case "Build":
                    c.setBuild(Integer.parseInt(in.nextString()));
                    break;
                case "Move":
                    c.setMove(Integer.parseInt(in.nextString()));
                    break;
                case "Types":
                    JsonArray jarray = parser.parse(in.nextString()).getAsJsonArray();
                    System.out.println("Types: ");
                    for (JsonElement e : jarray) {
                        String str = e.getAsString();
                        c.types.add(Type.valueOf(str));
                        System.out.println(str);
                    }
                    break;
                case "Growth":
                    c.setGrowth(gson.fromJson(in.nextString(), int[].class));
                    break;
                case "AI":
                    c.setAi(AI.valueOf(in.nextString()));
                    break;
                case "Bag":
                    JsonArray jbag = parser.parse(in.nextString()).getAsJsonArray();
                    for (JsonElement e : jbag) {
                        JsonObject o = parser.parse(e.getAsString()).getAsJsonObject();
                        JsonPrimitive prim = (JsonPrimitive) o.get("Class Name");
                        Class<?> klass = null;
                        try {
                            klass = Class.forName(prim.getAsString());
                        } catch (ClassNotFoundException ce) {
                            ce.printStackTrace();
                        }
                        System.out.println("this item is " + e.getAsString());
                        System.out.println(klass.getName());
                        Object item = gson.fromJson(e.getAsString(), klass);
                        if (item instanceof Item) {
                            c.gainItem((Item) item);
                            System.out.println("succeed to store " + ((Item) item).getName());
                        }
                    }
                    break;
                case "Want":
                    String item = in.nextString();
                    if (!item.equals("Null")) {
                        JsonPrimitive prim = (JsonPrimitive) parser.parse(item).getAsJsonObject().get("Class Name");
                        Class<?> klass = null;
                        try {
                            klass = Class.forName(prim.getAsString());
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        c.setWant((Item) gson.fromJson(item, klass));
                    }
                    break;
                default:
                    break;
            }
        }
        in.endObject();
        return c;
    }

}
