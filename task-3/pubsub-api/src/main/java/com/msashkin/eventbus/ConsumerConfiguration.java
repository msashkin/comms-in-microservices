package com.msashkin.eventbus;

import java.util.Collection;
import java.util.Map;

public interface ConsumerConfiguration {

    String getConnection();

    Map<String, Object> getProperties();

    String getConsumerGroup();

    Collection<String> getTopics();
}
