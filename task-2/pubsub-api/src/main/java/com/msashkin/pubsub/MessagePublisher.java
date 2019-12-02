package com.msashkin.pubsub;

import com.msashkin.pubsub.model.MessageWrapper;

public interface MessagePublisher<T> {

    void publish(String topic, T message);

    void publishWrapper(String topic, MessageWrapper<T> messageWrapper);
}
