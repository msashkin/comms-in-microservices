package com.msashkin.config;

import com.msashkin.pubsub.MessagePublisher;
import com.msashkin.pubsub.mapper.JsonMessageMapper;
import com.msashkin.pubsub.mapper.MessageMapper;
import com.msashkin.pubsub.model.MessageWrapper;
import com.msashkin.pubsub.rabbitmq.RabbitMessagePublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestServiceConfig {

    // TODO: extract this to properties
    private static final String RABBITMQ_HOST = "localhost";
    private static final String RABBITMQ_EXCHANGE_NAME = "hello_exchange";

    @Bean
    MessagePublisher<String> messagePublisher() {
        return new RabbitMessagePublisher<>(RABBITMQ_HOST, RABBITMQ_EXCHANGE_NAME, messageMapper());
    }

    @Bean
    MessageMapper<MessageWrapper<String>> messageMapper() {
        return new JsonMessageMapper<>();
    }
}
