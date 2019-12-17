package com.msashkin;

public class Message<T> {

    private T message;

    private String forwardToTopic;

    public Message() {
    }

    public T getMessage() {
        return message;
    }

    public void setMessage(T message) {
        this.message = message;
    }

    public String getForwardToTopic() {
        return forwardToTopic;
    }

    public void setForwardToTopic(String forwardToTopic) {
        this.forwardToTopic = forwardToTopic;
    }

    @Override
    public String toString() {
        return "Message{" +
                "message=" + message +
                ", forwardToTopic='" + forwardToTopic + '\'' +
                '}';
    }
}
