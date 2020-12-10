package game.risk.controller;

import com.google.gson.Gson;

/**
 * Json Processor
 */
public class JsonHandler {

    private final Gson gson = new Gson();

    /**
     * Transform object i json
     *
     * @param payload object instance
     * @param <T>     class of object
     * @return Json as string
     */
    public <T> String toJson(T payload) {
        return gson.toJson(payload);
    }

    /**
     * Transform string in object
     *
     * @param jsonString Json stirng
     * @param clazz      Class of desired object
     * @param <T>        Object Type
     * @return Instance of object
     */
    public <T> T fromJson(String jsonString, Class<T> clazz) {
        return gson.fromJson(jsonString, clazz);
    }
}
