package com.msashkin.pubsub;

public interface MessagePublisher {

    void publish(String topic, Object message);
}
