package com.msashkin.consumerone;

import com.msashkin.Message;
import com.msashkin.eventbus.EventConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class ConsumerTwo implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(ConsumerTwo.class);

    private final EventConsumer<Message<String>> helloEventConsumer;

    private final EventConsumer<Message<String>> helloFwdEventConsumer;

    public ConsumerTwo(EventConsumer<Message<String>> helloEventConsumer,
                       EventConsumer<Message<String>> helloFwdEventConsumer) {
        this.helloEventConsumer = helloEventConsumer;
        this.helloFwdEventConsumer = helloFwdEventConsumer;
    }

    public static void main(String[] args) {
        SpringApplication.run(ConsumerTwo.class, args);
    }

    @Override
    public void run(String... args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        helloEventConsumer.addEventListener(event -> LOG.info("Event arrived to " + event.getTopic() + " : " + event.getData()));
        helloFwdEventConsumer.addEventListener(event -> LOG.info("Event arrived to " + event.getTopic() + " : " + event.getData()));

        executorService.submit(helloEventConsumer);
        executorService.submit(helloFwdEventConsumer);
    }
}
