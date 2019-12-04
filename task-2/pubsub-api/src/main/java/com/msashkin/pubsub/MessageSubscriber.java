package com.msashkin.pubsub;

import com.msashkin.pubsub.model.MessageWrapper;

public interface MessageSubscriber<T> {

    void subscribe(String topic);

    void unsubscribe(String topic);

    void onMessage(String topic, T message);

    void onMessageWrapper(String topic, MessageWrapper<T> messageWrapper);
}
