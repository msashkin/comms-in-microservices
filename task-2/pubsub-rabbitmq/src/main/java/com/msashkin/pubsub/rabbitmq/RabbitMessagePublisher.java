package com.msashkin.pubsub.rabbitmq;

import com.msashkin.pubsub.MessagePublisher;
import com.msashkin.pubsub.mapper.MessageMapper;
import com.msashkin.pubsub.mapper.MessageMapperException;
import com.msashkin.pubsub.model.MessageWrapper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMessagePublisher<T> implements MessagePublisher<T> {

    private static final Logger LOG = LoggerFactory.getLogger(RabbitMessagePublisher.class);

    private final String rabbitMqHost;
    private final String rabbitMqExchangeName;
    private final MessageMapper<MessageWrapper<T>> messageMapper;

    public RabbitMessagePublisher(String rabbitMqHost,
                                  String rabbitMqExchangeName,
                                  MessageMapper<MessageWrapper<T>> messageMapper) {
        this.rabbitMqHost = rabbitMqHost;
        this.rabbitMqExchangeName = rabbitMqExchangeName;
        this.messageMapper = messageMapper;
    }

    @Override
    public void publish(String topic, T message) {
        publishWrapper(topic, MessageWrapper.of(message));
    }

    @Override
    public void publishWrapper(String topic, MessageWrapper<T> messageWrapper) {
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
                                 properties(),
                                 messageMapper.fromObject(messageWrapper));

            LOG.info("Sent to " + routingKey + " : " + messageWrapper.getMessage());
        } catch (TimeoutException | IOException | MessageMapperException e) {
            throw new RuntimeException(e);
        }
    }

    private AMQP.BasicProperties properties() {
        return new AMQP.BasicProperties.Builder().contentType(messageMapper.getContentType())
                                                 .type(MessageWrapper.class.getCanonicalName())
                                                 .build();
    }

    private String getRoutingKey(String topic) {
        return topic;
    }
}
