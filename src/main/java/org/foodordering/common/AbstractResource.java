package org.foodordering.common;

import com.google.gson.*;

import javax.ws.rs.core.Response;
import java.lang.reflect.Type;
import java.sql.Time;
import java.sql.Date;

public abstract class AbstractResource {
    private Gson gson = null;

    public Gson gson() {
        if (gson == null) {
            gson = new GsonBuilder()
                    .registerTypeAdapter(Time.class, new JsonDeserializer<Time>() {
                        @Override
                        public Time deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                                throws JsonParseException {
                            try {
                                return Time.valueOf(json.getAsString());
                            } catch (IllegalArgumentException e) {
                                throw new JsonParseException("Invalid time format: " + json.getAsString());
                            }
                        }
                    }) .registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                        @Override
                        public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                                throws JsonParseException {
                            String value = json.getAsString();
                            try {
                                if (value.matches("\\d+")) {
                                    return new Date(Long.parseLong(value));
                                }
                                return Date.valueOf(value);
                            } catch (Exception e) {
                                throw new JsonParseException("Invalid SQL Date: " + value, e);
                            }
                        }
                    })
                    .registerTypeAdapter(Date.class, new JsonSerializer<Date>() {
                        @Override
                        public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
                            return new JsonPrimitive(src.toString());
                        }
                    })
                    .create();
        }
        return gson;
    }
}
