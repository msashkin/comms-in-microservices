package com.msashkin.pubsub.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonMessageMapper implements MessageMapper {

    @Override
    public String getContentType() {
        return "application/json";
    }

    @Override
    public byte[] fromObject(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            throw new MessageMapperException(e.getMessage());
        }
    }

    @Override
    public Object fromMessage(byte[] message, Class clazz) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(message, clazz);
        } catch (IOException e) {
            throw new MessageMapperException(e.getMessage());
        }
    }
}
