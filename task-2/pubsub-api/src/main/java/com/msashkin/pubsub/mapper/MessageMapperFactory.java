package com.msashkin.pubsub.mapper;

public class MessageMapperFactory<T> {

    public MessageMapper<T> create(String contentType) {
        switch (contentType) {
            case "application/json":
                return new JsonMessageMapper<>();
            default:
                throw new IllegalArgumentException("Unknown content type");
        }
    }
}
