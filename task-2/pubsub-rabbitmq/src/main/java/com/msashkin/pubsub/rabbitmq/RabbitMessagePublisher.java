package com.msashkin.pubsub.rabbitmq;

import com.msashkin.pubsub.MessagePublisher;
import com.msashkin.pubsub.mapper.MessageMapper;
import com.rabbitmq.client.AMQP;
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
    private final MessageMapper messageMapper;

    public RabbitMessagePublisher(String rabbitMqHost,
                                  String rabbitMqExchangeName,
                                  MessageMapper messageMapper) {
        this.rabbitMqHost = rabbitMqHost;
        this.rabbitMqExchangeName = rabbitMqExchangeName;
        this.messageMapper = messageMapper;
    }

    @Override
    public void publish(String topic, Object message) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(rabbitMqHost);
        connectionFactory.setAutomaticRecoveryEnabled(true);
        connectionFactory.setTopologyRecoveryEnabled(true);

        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.exchangeDeclare(rabbitMqExchangeName, "topic", true);

            String routingKey = getRoutingKey(topic);

            channel.basicPublish(rabbitMqExchangeName,
                                 routingKey,
                                 properties(message),
                                 messageMapper.fromObject(message));

            LOG.info("Sent to " + routingKey + " : " + message);
        } catch (TimeoutException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private AMQP.BasicProperties properties(Object message) {
        return new AMQP.BasicProperties.Builder().contentType(messageMapper.getContentType())
                                                 .type(message.getClass().getCanonicalName())
                                                 .build();
    }

    private String getRoutingKey(String topic) {
        return topic;
    }
}
