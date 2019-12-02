package com.msashkin.pubsub.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonMessageMapper<T> implements MessageMapper<T> {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public String getContentType() {
        return "application/json";
    }

    @Override
    public byte[] fromObject(T object) throws MessageMapperException {
        try {
            return OBJECT_MAPPER.writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            throw new MessageMapperException(e.getMessage());
        }
    }

    @Override
    public T fromMessage(byte[] message, Class<T> clazz) throws MessageMapperException {
        try {
            return OBJECT_MAPPER.readValue(message, clazz);
        } catch (IOException e) {
            throw new MessageMapperException(e.getMessage());
        }
    }
}
