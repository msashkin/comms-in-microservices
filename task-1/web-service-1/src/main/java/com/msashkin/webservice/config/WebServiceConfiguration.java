package com.msashkin.webservice.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({ WebServiceRpcOneConfigurationProperties.class,
                                 WebServiceRpcTwoConfigurationProperties.class })
public class WebServiceConfiguration {
}
