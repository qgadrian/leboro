package com.leboro.util.json;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leboro.util.exception.JSONFormatException;

public class JSONUtils {

    private static final String JSON_CONTENT_TYPE = "application/json";

    private static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static String objectToJson(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new JSONFormatException(e);
        }
    }

    public static <T> T readValue(String content, Class<T> valueType) {
        try {
            return mapper.readValue(content, valueType);
        } catch (Exception e) {
            throw new JSONFormatException(e);
        }
    }

    public static <T> T readValue(String content, @SuppressWarnings("rawtypes") TypeReference valueTypeRef) {
        try {
            return mapper.readValue(content, valueTypeRef);
        } catch (Exception e) {
            throw new JSONFormatException(e);
        }
    }

    public static JsonNode getJsonNodeFromJsonString(String content) {
        JsonNode jsonNode;
        try {
            jsonNode = mapper.readTree(content);
            return jsonNode;
        } catch (IOException e) {
            throw new JSONFormatException(e);
        }
    }

}

