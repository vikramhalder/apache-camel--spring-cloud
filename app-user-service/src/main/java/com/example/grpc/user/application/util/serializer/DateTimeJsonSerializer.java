package com.example.grpc.user.application.util.serializer;

import com.example.grpc.user.application.util.DateTimeMap;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class DateTimeJsonSerializer<T> extends JsonSerializer<T> {
    @Override
    public void serialize(T dateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(DateTimeMap.date(dateTime).toString());
    }
}
