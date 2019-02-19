package Item.TypeAdapters;

import Item.MagicEffect;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class MagicEffectTypeAdapter extends TypeAdapter<MagicEffect> {
    @Override
    public void write(JsonWriter out, MagicEffect magicEffect) throws IOException {

    }

    @Override
    public MagicEffect read(JsonReader in) throws IOException {
        return null;
    }
}
