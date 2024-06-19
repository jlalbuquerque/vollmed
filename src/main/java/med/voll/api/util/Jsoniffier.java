package med.voll.api.util;

import com.google.gson.Gson;

public class Jsoniffier {
    private static final Gson gson = new Gson();

    public static String toJson(Object object) {
        return gson.toJson(object);
    }
}
