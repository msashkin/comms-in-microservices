package com.msashkin.pubsub.rabbitmq;

import com.msashkin.pubsub.MessageSubscriber;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public abstract class RabbitMessageSubscriber implements MessageSubscriber {

    private static final Logger LOG = LoggerFactory.getLogger(RabbitMessageSubscriber.class);

    private final String rabbitMqHost;

    private final String rabbitMqExchangeName;

    public RabbitMessageSubscriber(String rabbitMqHost, String rabbitMqExchangeName) {
        this.rabbitMqHost = rabbitMqHost;
        this.rabbitMqExchangeName = rabbitMqExchangeName;
    }

    @Override
    public void subscribe(String topic) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(rabbitMqHost);

        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();

            channel.exchangeDeclare(rabbitMqExchangeName, "topic");
            String queueName = channel.queueDeclare().getQueue();

            // queueDeclare is idempotent
            // channel.queueDeclare(RABBITMQ_QUEUE_NAME, true, false, false, null);

            channel.queueBind(queueName, rabbitMqExchangeName, topic);

            // we should not close the connection and channel on subscribing
            LOG.info("Waiting for messages from the topic: " + topic);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                onMessage(topic, message);
            };
            channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
            });
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void unsubscribe(String topic) {
        //TODO: implement
    }
}
