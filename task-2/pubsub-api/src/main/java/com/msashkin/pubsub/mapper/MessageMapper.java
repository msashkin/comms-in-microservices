package com.msashkin.pubsub.mapper;

public interface MessageMapper {

    String getContentType();

    byte[] fromObject(Object object);

    Object fromMessage(byte[] message, Class clazz);
}
