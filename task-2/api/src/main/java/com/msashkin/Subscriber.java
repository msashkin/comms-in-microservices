package com.msashkin;

public interface Subscriber {

    void subscribe(String topic);

    void unsubscribe(String topic);

    void onMessage(String topic);
}
