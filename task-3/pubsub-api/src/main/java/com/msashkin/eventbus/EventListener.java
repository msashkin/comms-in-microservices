package com.msashkin.eventbus;

public interface EventListener<T> extends java.util.EventListener {

    void acceptEvent(Event<T> event);
}
