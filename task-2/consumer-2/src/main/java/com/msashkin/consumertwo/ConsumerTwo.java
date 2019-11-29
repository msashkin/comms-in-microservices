package com.msashkin.consumertwo;

import com.msashkin.pubsub.mapper.MessageMapperFactory;
import com.msashkin.pubsub.rabbitmq.RabbitMessageSubscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConsumerTwo extends RabbitMessageSubscriber implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(ConsumerTwo.class);

    private static final String RABBITMQ_HOST = "localhost";
    private static final String RABBITMQ_EXCHANGE_NAME = "hello_exchange";

    public ConsumerTwo(MessageMapperFactory messageMapperFactory) {
        super(messageMapperFactory, RABBITMQ_HOST, RABBITMQ_EXCHANGE_NAME);
    }

    public static void main(String[] args) {
        SpringApplication.run(ConsumerTwo.class, args);
    }

    @Override
    public void onMessage(String topic, Object message) {
        LOG.info("Received from " + topic + " : " + message);
    }

    @Override
    public void run(String... args) throws Exception {
        subscribe("hello");
        subscribe("hello_fwd");
    }
}
