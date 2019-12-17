package com.msashkin.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class MessageRequest {

    @NotNull
    @NotBlank
    private String message;

    @NotNull
    @NotBlank
    private String topic;

    private String forwardToTopic;

    public MessageRequest() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getForwardToTopic() {
        return forwardToTopic;
    }

    public void setForwardToTopic(String forwardToTopic) {
        this.forwardToTopic = forwardToTopic;
    }
}
