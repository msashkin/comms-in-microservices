package com.msashkin.consumerone;

import com.msashkin.pubsub.rabbitmq.RabbitMessageSubscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConsumerOne extends RabbitMessageSubscriber implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(ConsumerOne.class);

    private static final String RABBITMQ_HOST = "localhost";
    private static final String RABBITMQ_EXCHANGE_NAME = "hello_exchange";

    public ConsumerOne() {
        super(RABBITMQ_HOST, RABBITMQ_EXCHANGE_NAME);
    }

    public static void main(String[] args) {
        SpringApplication.run(ConsumerOne.class, args);
    }

    @Override
    public void onMessage(String topic, String message) {
        LOG.info("Received from " + topic + " : " + message);
    }

    @Override
    public void run(String... args) throws Exception {
        subscribe("hello");
        subscribe("hello_fwd");
    }
}
