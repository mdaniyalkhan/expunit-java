package com.mushdap.methodtest.utility;

import com.google.gson.Gson;

/**
 * Utility for object serialization and deserialization
 */
public class SerializeUtility {

    /**
     * Serialize object
     *
     * @param objectToBeSerialized Object to be serialized
     * @param <T>                  Generic type
     * @return String serialized output
     */
    public static <T> String serialize(T objectToBeSerialized) {
        try {
            Gson gson = new Gson();
            return gson.toJson(objectToBeSerialized);
        } catch (Exception ignored) {
            return "'" + objectToBeSerialized.toString() + "'";
        }
    }
}
