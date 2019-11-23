package com.msashkin.controller;

import com.msashkin.pubsub.Message;
import com.msashkin.pubsub.MessagePublisher;
import com.msashkin.model.MessageRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProducerController {

    private final MessagePublisher messagePublisher;

    public ProducerController(MessagePublisher messagePublisher) {
        this.messagePublisher = messagePublisher;
    }

    @PostMapping("/publish")
    void publish(@RequestBody MessageRequest messageRequest) {
        messagePublisher.publish(messageRequest.getTopic(), new Message(messageRequest.getMessage()));
    }
}
