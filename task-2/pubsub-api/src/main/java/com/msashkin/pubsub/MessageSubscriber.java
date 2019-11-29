package com.msashkin.pubsub;

public interface MessageSubscriber {

    void subscribe(String topic);

    void unsubscribe(String topic);

    void onMessage(String topic, Object message);
}
