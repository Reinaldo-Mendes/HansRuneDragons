package utilities;

import com.google.gson.*;
import org.dreambot.api.methods.prayer.Prayer;

import java.lang.reflect.Type;

import static org.dreambot.api.utilities.Logger.log;

public class PrayerDeserializer implements JsonDeserializer<Prayer> {
    @Override
    public Prayer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String prayerName = json.getAsString();
        try {
            return Prayer.valueOf(prayerName);
        } catch (IllegalArgumentException e) {
            log("Error parsing prayer: " + prayerName + ". Returning null.");
            return null;
        }
    }
}

