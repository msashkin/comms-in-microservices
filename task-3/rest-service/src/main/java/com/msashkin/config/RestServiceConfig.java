package com.msashkin.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.msashkin.Message;
import com.msashkin.eventbus.EventProducer;
import com.msashkin.eventbus.EventSerializer;
import com.msashkin.eventbus.ProducerConfiguration;
import com.msashkin.eventbus.kafka.KafkaEventProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class RestServiceConfig {

    @Value("${kafka.host}")
    private String kafkaHost;

    @Value("${kafka.port}")
    private String kafkaPort;

    private String connectionString() {
        return kafkaHost + ":" + kafkaPort;
    }

    @Bean
    EventProducer<Message<String>> helloEventProducer() {
        return new KafkaEventProducer<>(helloProducerConfiguration(), eventSerializer());
    }

    @Bean
    EventProducer<Message<String>> helloFwdEventProducer() {
        return new KafkaEventProducer<>(helloFwdProducerConfiguration(), eventSerializer());
    }

    private ProducerConfiguration helloProducerConfiguration() {
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
                return "hello";
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
}
