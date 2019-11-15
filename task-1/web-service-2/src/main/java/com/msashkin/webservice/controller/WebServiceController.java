package com.msashkin.webservice.controller;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.msashkin.rpchelloservice.service.HelloResponse;
import com.msashkin.webservice.client.RpcHelloServiceClient;
import com.msashkin.webservice.config.WebServiceRpcOneConfigurationProperties;
import com.msashkin.webservice.config.WebServiceRpcTwoConfigurationProperties;
import com.msashkin.webservice.model.MessageResponse;
import com.msashkin.webservice.model.MessagesResponse;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

@RestController
@RequestMapping("/api")
public class WebServiceController {

    private final WebServiceRpcOneConfigurationProperties rpcOneConfigurationProperties;
    private final WebServiceRpcTwoConfigurationProperties rpcTwoConfigurationProperties;
    private final Executor threadPoolExecutor;

    public WebServiceController(WebServiceRpcOneConfigurationProperties rpcOneConfigurationProperties,
                                WebServiceRpcTwoConfigurationProperties rpcTwoConfigurationProperties,
                                Executor threadPoolExecutor) {
        this.rpcOneConfigurationProperties = rpcOneConfigurationProperties;
        this.rpcTwoConfigurationProperties = rpcTwoConfigurationProperties;
        this.threadPoolExecutor = threadPoolExecutor;
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

        CompletableFuture<MessageResponse> result1 = new CompletableFuture<>();
        Futures.addCallback(response1, new FutureCallback<HelloResponse>() {
            @Override
            public void onSuccess(@NullableDecl HelloResponse helloResponse) {
                result1.complete(new MessageResponse(helloResponse != null ? helloResponse.getMessage() : null));
            }

            @Override
            public void onFailure(Throwable throwable) {
                result1.completeExceptionally(throwable);
            }
        }, threadPoolExecutor);

        CompletableFuture<MessageResponse> result2 = new CompletableFuture<>();
        Futures.addCallback(response2, new FutureCallback<HelloResponse>() {
            @Override
            public void onSuccess(@NullableDecl HelloResponse helloResponse) {
                result2.complete(new MessageResponse(helloResponse != null ? helloResponse.getMessage() : null));
            }

            @Override
            public void onFailure(Throwable throwable) {
                result2.completeExceptionally(throwable);
            }
        }, threadPoolExecutor);

        CompletableFuture.allOf(result1, result2).join();

        return new MessagesResponse(Arrays.asList(result1.get().getMessage(), result2.get().getMessage()));
    }
}
