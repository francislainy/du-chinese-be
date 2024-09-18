package com.francislainy.duchinesebe.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class TestUtils {

    public static String toJson(Object object) {
        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        try {
            return om.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Updated to support different JsonViews
    public static String toJson(Object object, Class<?> jsonView) {
        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        try {
            return om.writerWithView(jsonView).writeValueAsString(object);  // Use the specified view for serialization
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
