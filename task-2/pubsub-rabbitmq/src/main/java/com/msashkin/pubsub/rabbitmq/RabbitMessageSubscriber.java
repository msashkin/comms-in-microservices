package com.msashkin.pubsub.rabbitmq;

import com.msashkin.pubsub.MessageSubscriber;
import com.msashkin.pubsub.mapper.MessageMapper;
import com.msashkin.pubsub.mapper.MessageMapperFactory;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public abstract class RabbitMessageSubscriber implements MessageSubscriber {

    private static final Logger LOG = LoggerFactory.getLogger(RabbitMessageSubscriber.class);

    private final MessageMapperFactory messageMapperFactory;
    private final String rabbitMqHost;
    private final String rabbitMqExchangeName;

    public RabbitMessageSubscriber(MessageMapperFactory messageMapperFactory,
                                   String rabbitMqHost,
                                   String rabbitMqExchangeName) {
        this.messageMapperFactory = messageMapperFactory;
        this.rabbitMqHost = rabbitMqHost;
        this.rabbitMqExchangeName = rabbitMqExchangeName;
    }

    @Override
    public void subscribe(String topic) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(rabbitMqHost);
        connectionFactory.setAutomaticRecoveryEnabled(true);
        connectionFactory.setTopologyRecoveryEnabled(true);

        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();

            channel.exchangeDeclare(rabbitMqExchangeName, "topic", true);
            String queueName = channel.queueDeclare().getQueue();
            channel.queueBind(queueName, rabbitMqExchangeName, topic);

            // we should not close the connection and channel on subscribing
            LOG.info("Waiting for messages from the topic: " + topic);

            channel.basicConsume(queueName, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag,
                                           Envelope envelope,
                                           AMQP.BasicProperties properties,
                                           byte[] body) throws IOException {
                    String routingKey = envelope.getRoutingKey();
                    String contentType = properties.getContentType();
                    long deliveryTag = envelope.getDeliveryTag();
                    try {
                        MessageMapper messageMapper = messageMapperFactory.create(contentType);
                        Object message = messageMapper.fromMessage(body, Class.forName(properties.getType()));
                        onMessage(routingKey, message);
                        channel.basicAck(deliveryTag, false);
                    } catch (Exception e) {
                        channel.basicNack(deliveryTag, false, true);
                    }
                }
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
