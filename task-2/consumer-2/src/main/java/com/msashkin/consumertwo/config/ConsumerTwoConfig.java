package com.msashkin.consumertwo.config;

import com.msashkin.pubsub.mapper.MessageMapperFactory;
import com.msashkin.pubsub.model.MessageWrapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConsumerTwoConfig {

    @Bean
    MessageMapperFactory<MessageWrapper<String>> messageMapperFactory() {
        return new MessageMapperFactory<>();
    }
}
