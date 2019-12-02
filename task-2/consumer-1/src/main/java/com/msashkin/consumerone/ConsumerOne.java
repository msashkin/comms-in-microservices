package com.msashkin.consumerone;

import com.msashkin.pubsub.MessagePublisher;
import com.msashkin.pubsub.mapper.MessageMapperFactory;
import com.msashkin.pubsub.model.MessageWrapper;
import com.msashkin.pubsub.rabbitmq.RabbitMessageSubscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConsumerOne extends RabbitMessageSubscriber<String> implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(ConsumerOne.class);

    private static final String RABBITMQ_HOST = "localhost";
    private static final String RABBITMQ_EXCHANGE_NAME = "hello_exchange";

    private final MessagePublisher<String> messagePublisher;

    public ConsumerOne(MessageMapperFactory<MessageWrapper<String>> messageMapperFactory,
                       MessagePublisher<String> messagePublisher) {
        super(messageMapperFactory, RABBITMQ_HOST, RABBITMQ_EXCHANGE_NAME);
        this.messagePublisher = messagePublisher;
    }

    public static void main(String[] args) {
        SpringApplication.run(ConsumerOne.class, args);
    }

    @Override
    public void onMessage(String topic, String message) {
        LOG.info("Received from " + topic + " : " + message);
    }

    @Override
    public void onMessageWrapper(String topic, MessageWrapper<String> messageWrapper) {
        LOG.info("Received from " + topic + " : " + messageWrapper);

    }

    @Override
    public void run(String... args) throws Exception {
        subscribe("hello");
        subscribe("hello_fwd");
    }
}
