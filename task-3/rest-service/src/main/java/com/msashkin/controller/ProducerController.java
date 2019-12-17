package com.msashkin.controller;

import com.msashkin.Message;
import com.msashkin.eventbus.Event;
import com.msashkin.eventbus.EventProducer;
import com.msashkin.model.MessageRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class ProducerController {

    private final EventProducer<Message<String>> helloEventProducer;

    private final EventProducer<Message<String>> helloFwdEventProducer;

    public ProducerController(EventProducer<Message<String>> helloEventProducer,
                              EventProducer<Message<String>> helloFwdEventProducer) {
        this.helloEventProducer = helloEventProducer;
        this.helloFwdEventProducer = helloFwdEventProducer;
    }

    @PostMapping("/publish")
    void publish(@RequestBody @Valid MessageRequest messageRequest) {
        Message<String> message = new Message<>();
        message.setMessage(messageRequest.getMessage());
        message.setForwardToTopic(messageRequest.getForwardToTopic());

        String topic = messageRequest.getTopic();
        if (topic.equals("hello")) {
            helloEventProducer.sendAsync(new Event<>(message));
        }
        if (topic.equals("hello_fwd")) {
            helloFwdEventProducer.sendAsync(new Event<>(message));
        }
    }
}
