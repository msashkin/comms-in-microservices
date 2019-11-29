package com.msashkin.consumertwo.config;

import com.msashkin.pubsub.mapper.MessageMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConsumerTwoConfig {

    @Bean
    MessageMapperFactory messageMapperFactory() {
        return new MessageMapperFactory();
    }
}
