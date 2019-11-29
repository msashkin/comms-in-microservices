package com.msashkin.consumerone.config;

import com.msashkin.pubsub.mapper.MessageMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConsumerOneConfig {

    @Bean
    MessageMapperFactory messageMapperFactory() {
        return new MessageMapperFactory();
    }
}
