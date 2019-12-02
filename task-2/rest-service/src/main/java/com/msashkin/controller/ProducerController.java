package com.msashkin.controller;

import com.msashkin.model.MessageRequest;
import com.msashkin.pubsub.MessagePublisher;
import com.msashkin.pubsub.model.MessageWrapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class ProducerController {

    private final MessagePublisher<String> messagePublisher;

    public ProducerController(MessagePublisher<String> messagePublisher) {
        this.messagePublisher = messagePublisher;
    }

    @PostMapping("/publish")
    void publish(@RequestBody @Valid MessageRequest messageRequest) {
        if (messageRequest.getForwardToTopic() == null) {
            messagePublisher.publish(messageRequest.getTopic(), messageRequest.getMessage());
        } else {
            messagePublisher.publishWrapper(messageRequest.getTopic(),
                                            MessageWrapper.of(messageRequest.getMessage(),
                                                              messageRequest.getForwardToTopic()));
        }
    }
}
