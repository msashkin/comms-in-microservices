package com.msashkin.webservice.model;

import java.util.List;

public class MessagesResponse {

    private List<String> messages;

    public MessagesResponse(List<String> messages) {
        this.messages = messages;
    }

    public List<String> getMessages() {
        return messages;
    }
}
