package Item.TypeAdapters;

import Item.UnlockMagic;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class UnlockMagicTypeAdapter extends TypeAdapter<UnlockMagic> {
    @Override
    public void write(JsonWriter out, UnlockMagic unlockMagic) throws IOException {
        out.beginObject();
        out.name("Class Name").value(UnlockMagic.class.getName());
        out.endObject();
    }

    @Override
    public UnlockMagic read(JsonReader in) throws IOException {
        return new UnlockMagic();
    }
}
