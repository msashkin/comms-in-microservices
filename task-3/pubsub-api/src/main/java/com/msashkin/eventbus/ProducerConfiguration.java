package com.msashkin.eventbus;

import java.util.Map;

public interface ProducerConfiguration {

    String getConnection();

    Map<String, Object> getProperties();

    String getTopic();
}
