package com.msashkin.consumerone.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msashkin.Message;
import com.msashkin.eventbus.ConsumerConfiguration;
import com.msashkin.eventbus.EventConsumer;
import com.msashkin.eventbus.EventDeserializer;
import com.msashkin.eventbus.kafka.KafkaEventConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Configuration
public class ConsumerTwoConfig {

    @Value("${kafka.host}")
    private String kafkaHost;

    @Value("${kafka.port}")
    private String kafkaPort;

    private String connectionString() {
        return kafkaHost + ":" + kafkaPort;
    }

    @Bean
    EventConsumer<Message<String>> helloEventConsumer() {
        return new KafkaEventConsumer<>(helloConsumerConfiguration(), eventDeserializer());
    }

    @Bean
    EventConsumer<Message<String>> helloFwdEventConsumer() {
        return new KafkaEventConsumer<>(helloFwdConsumerConfiguration(), eventDeserializer());
    }

    private ConsumerConfiguration helloConsumerConfiguration() {
        return new ConsumerConfiguration() {
            @Override
            public String getConnection() {
                return connectionString();
            }

            @Override
            public Map<String, Object> getProperties() {
                return null;
            }

            @Override
            public String getConsumerGroup() {
                return "consumer_two";
            }

            @Override
            public Collection<String> getTopics() {
                return Collections.singleton("hello");
            }
        };
    }

    private ConsumerConfiguration helloFwdConsumerConfiguration() {
        return new ConsumerConfiguration() {
            @Override
            public String getConnection() {
                return connectionString();
            }

            @Override
            public Map<String, Object> getProperties() {
                return null;
            }

            @Override
            public String getConsumerGroup() {
                return "consumer_two";
            }

            @Override
            public Collection<String> getTopics() {
                return Collections.singleton("hello_fwd");
            }
        };
    }

    private EventDeserializer<Message<String>> eventDeserializer() {
        return data -> {
            try {
                return new ObjectMapper().readValue(data, Message.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }
}
