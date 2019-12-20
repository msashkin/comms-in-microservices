package com.msashkin.eventbus;

public interface EventSerializer<T> {

    byte[] serialize(T dataObj);
}
