package com.msashkin.consumerone.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.msashkin.Message;
import com.msashkin.eventbus.ConsumerConfiguration;
import com.msashkin.eventbus.EventConsumer;
import com.msashkin.eventbus.EventDeserializer;
import com.msashkin.eventbus.EventProducer;
import com.msashkin.eventbus.EventSerializer;
import com.msashkin.eventbus.ProducerConfiguration;
import com.msashkin.eventbus.kafka.KafkaEventConsumer;
import com.msashkin.eventbus.kafka.KafkaEventProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Configuration
public class ConsumerOneConfig {

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
    EventProducer<Message<String>> helloFwdEventProducer() {
        return new KafkaEventProducer<>(helloFwdProducerConfiguration(), eventSerializer());
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
                return "consumer_one";
            }

            @Override
            public Collection<String> getTopics() {
                return Collections.singleton("hello");
            }
        };
    }

    private ProducerConfiguration helloFwdProducerConfiguration() {
        return new ProducerConfiguration() {
            @Override
            public String getConnection() {
                return connectionString();
            }

            @Override
            public Map<String, Object> getProperties() {
                return null;
            }

            @Override
            public String getTopic() {
                return "hello_fwd";
            }
        };
    }

    private EventSerializer<Message<String>> eventSerializer() {
        return dataObj -> {
            try {
                return new ObjectMapper().writeValueAsBytes(dataObj);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
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
