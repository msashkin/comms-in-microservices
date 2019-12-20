package com.msashkin.eventbus;

public interface EventDeserializer<T> {

    T deserialize(byte[] data);
}
