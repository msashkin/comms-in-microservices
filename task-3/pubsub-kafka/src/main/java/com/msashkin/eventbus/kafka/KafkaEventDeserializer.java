package com.msashkin.eventbus.kafka;

import com.msashkin.eventbus.Event;
import com.msashkin.eventbus.EventDeserializer;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

final class KafkaEventDeserializer<T> implements Deserializer<Event<T>> {

    private EventDeserializer<T> deserializer;

    KafkaEventDeserializer(EventDeserializer<T> deserializer) {
        this.deserializer = deserializer;
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public Event<T> deserialize(String topic, byte[] data) {
        Event<T> event = new Event<>(data);
        event.setDeserializer(deserializer);
        return event;
    }

    @Override
    public void close() {

    }
}
