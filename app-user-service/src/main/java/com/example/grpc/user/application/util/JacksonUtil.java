package com.example.grpc.user.application.util;


import com.example.grpc.user.application.util.serializer.DateTimeJsonSerializer;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.extern.slf4j.Slf4j;

import java.time.*;
import java.util.Date;

@Slf4j
public class JacksonUtil {
    private static ObjectMapper objectMapper;

    public static ObjectMapper objectMapper() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            objectMapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
            objectMapper.configure(SerializationFeature.FAIL_ON_UNWRAPPED_TYPE_IDENTIFIERS, false);

            JavaTimeModule module = new JavaTimeModule();
            module.addSerializer(Date.class, new DateTimeJsonSerializer<>());
            module.addSerializer(Instant.class, new DateTimeJsonSerializer<>());
            module.addSerializer(LocalDate.class, new DateTimeJsonSerializer<>());
            module.addSerializer(LocalDateTime.class, new DateTimeJsonSerializer<>());
            module.addSerializer(ZonedDateTime.class, new DateTimeJsonSerializer<>());
            module.addSerializer(OffsetDateTime.class, new DateTimeJsonSerializer<>());

            module.addDeserializer(Instant.class, InstantDeserializer.INSTANT);
            module.addDeserializer(LocalDate.class, LocalDateDeserializer.INSTANCE);
            module.addDeserializer(LocalDateTime.class, LocalDateTimeDeserializer.INSTANCE);
            module.addDeserializer(ZonedDateTime.class, InstantDeserializer.ZONED_DATE_TIME);
            module.addDeserializer(OffsetDateTime.class, InstantDeserializer.OFFSET_DATE_TIME);

            objectMapper.registerModule(module);
        }
        return objectMapper;
    }

    public static <T> T jsonToInstance(String data, Class<T> clazz) {
        try {
            return objectMapper().readValue(data, clazz);
        } catch (Exception ex) {
            log.warn("JacksonUtil.jsonToInstance: {}", ex.getMessage());
        }
        return null;
    }

    public static String objectToJson(Object object) {
        try {
            return objectMapper().writeValueAsString(object);
        } catch (Exception ex) {
            log.warn("JacksonUtil.objectToJson: {}", ex.getMessage());
        }
        return null;
    }

    public static byte[] objectToByte(Object object) {
        try {
            return objectMapper().writeValueAsBytes(object);
        } catch (Exception ex) {
            log.warn("JacksonUtil.objectToJson: {}", ex.getMessage());
        }
        return null;
    }
}

