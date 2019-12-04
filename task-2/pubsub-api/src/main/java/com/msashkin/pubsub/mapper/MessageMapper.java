package com.msashkin.pubsub.mapper;

public interface MessageMapper<T> {

    String getContentType();

    byte[] fromObject(T object) throws MessageMapperException;

    T fromMessage(byte[] message, Class<T> clazz) throws MessageMapperException;
}
