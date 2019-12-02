package com.msashkin.pubsub.model;

public class MessageWrapper<T> {

    // May be replaced with a map if multiple metadata fields are available
    private String forwardToTopic;

    private T message;

    public MessageWrapper() {
    }

    public static <T> MessageWrapper<T> of(T message) {
        MessageWrapper<T> result = new MessageWrapper<>();
        result.setMessage(message);
        return result;
    }

    public static <T> MessageWrapper<T> of(T message, String forwardToTopic) {
        MessageWrapper<T> result = new MessageWrapper<>();
        result.setMessage(message);
        result.setForwardToTopic(forwardToTopic);
        return result;
    }

    public void setForwardToTopic(String forwardTopic) {
        this.forwardToTopic = forwardTopic;
    }

    public String getForwardToTopic() {
        return forwardToTopic;
    }

    public T getMessage() {
        return message;
    }

    public void setMessage(T message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "MessageWrapper{" +
                "forwardToTopic='" + forwardToTopic + '\'' +
                ", message=" + message +
                '}';
    }
}
