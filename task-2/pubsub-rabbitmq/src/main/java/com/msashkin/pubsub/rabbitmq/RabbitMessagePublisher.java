package com.msashkin.pubsub.rabbitmq;

import com.msashkin.pubsub.Message;
import com.msashkin.pubsub.MessagePublisher;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMessagePublisher implements MessagePublisher {

    private static final Logger LOG = LoggerFactory.getLogger(RabbitMessagePublisher.class);

    private final String rabbitMqHost;
    private final String rabbitMqExchangeName;

    public RabbitMessagePublisher(String rabbitMqHost, String rabbitMqExchangeName) {
        this.rabbitMqHost = rabbitMqHost;
        this.rabbitMqExchangeName = rabbitMqExchangeName;
    }

    @Override
    public void publish(String topic, Message message) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(rabbitMqHost);

        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {

            // queueDeclare is idempotent
//            channel.queueDeclare("hello", true, false, false, null);
//            channel.queueDeclare("hello_fwd", true, false, false, null);

            channel.exchangeDeclare(rabbitMqExchangeName, "topic");

            String routingKey = getRoutingKey(topic);

            channel.basicPublish(rabbitMqExchangeName, routingKey, null, message.getPayload().getBytes());

            LOG.info("Sent to " + routingKey + " : " + message);
        } catch (TimeoutException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getRoutingKey(String topic) {
        return topic;
    }
}
