package com.msashkin.webservice.controller;

import com.google.common.util.concurrent.ListenableFuture;
import com.msashkin.rpchelloservice.service.HelloResponse;
import com.msashkin.webservice.client.RpcHelloServiceClient;
import com.msashkin.webservice.config.WebServiceRpcOneConfigurationProperties;
import com.msashkin.webservice.config.WebServiceRpcTwoConfigurationProperties;
import com.msashkin.webservice.model.MessageResponse;
import com.msashkin.webservice.model.MessagesResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api")
public class WebServiceController {

    private final WebServiceRpcOneConfigurationProperties rpcOneConfigurationProperties;
    private final WebServiceRpcTwoConfigurationProperties rpcTwoConfigurationProperties;

    public WebServiceController(WebServiceRpcOneConfigurationProperties rpcOneConfigurationProperties,
                                WebServiceRpcTwoConfigurationProperties rpcTwoConfigurationProperties) {
        this.rpcOneConfigurationProperties = rpcOneConfigurationProperties;
        this.rpcTwoConfigurationProperties = rpcTwoConfigurationProperties;
    }

    @GetMapping("/hello")
    public MessageResponse hello() {
        return new MessageResponse("Hello World!");
    }

    @GetMapping("/rpchello")
    public MessagesResponse rpcHello() throws ExecutionException, InterruptedException {
        RpcHelloServiceClient rpcHelloServiceClient1 = new RpcHelloServiceClient(rpcOneConfigurationProperties.getHost(),
                                                                                 rpcOneConfigurationProperties.getPort());
        RpcHelloServiceClient rpcHelloServiceClient2 = new RpcHelloServiceClient(rpcTwoConfigurationProperties.getHost(),
                                                                                 rpcTwoConfigurationProperties.getPort());
        ListenableFuture<HelloResponse> response1 = rpcHelloServiceClient1.sayHelloAsync();
        ListenableFuture<HelloResponse> response2 = rpcHelloServiceClient2.sayHelloAsync("Max");

        return new MessagesResponse(Arrays.asList(response1.get().getMessage(),
                                                  response2.get().getMessage()));
    }
}
