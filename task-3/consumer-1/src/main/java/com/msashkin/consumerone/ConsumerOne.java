package com.msashkin.consumerone;

import com.msashkin.Message;
import com.msashkin.eventbus.Event;
import com.msashkin.eventbus.EventConsumer;
import com.msashkin.eventbus.EventProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class ConsumerOne implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(ConsumerOne.class);

    private final EventConsumer<Message<String>> helloEventConsumer;

    private final EventProducer<Message<String>> helloFwdEventProducer;

    public ConsumerOne(EventConsumer<Message<String>> helloEventConsumer,
                       EventProducer<Message<String>> helloFwdEventProducer) {
        this.helloEventConsumer = helloEventConsumer;
        this.helloFwdEventProducer = helloFwdEventProducer;
    }

    public static void main(String[] args) {
        SpringApplication.run(ConsumerOne.class, args);
    }

    @Override
    public void run(String... args) {
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        helloEventConsumer.addEventListener(event -> {
            LOG.info("Event arrived to " + event.getTopic() + " : " + event.getData());
            if (event.getData().getForwardToTopic() != null) {
                LOG.info("Forwarding event to " + event.getData().getForwardToTopic());
                helloFwdEventProducer.sendAsync(new Event<>(event.getData()));
            }
        });

        executorService.submit(helloEventConsumer);
    }
}
