package com.msashkin.webservice.client;

import com.google.common.util.concurrent.ListenableFuture;
import com.msashkin.rpchelloservice.service.HelloRequest;
import com.msashkin.rpchelloservice.service.HelloResponse;
import com.msashkin.rpchelloservice.service.RpcHelloServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RpcHelloServiceClient {

    private static final Logger logger = Logger.getLogger(RpcHelloServiceClient.class.getName());

    private final ManagedChannel channel;
    private final RpcHelloServiceGrpc.RpcHelloServiceBlockingStub blockingStub;
    private final RpcHelloServiceGrpc.RpcHelloServiceFutureStub futureStub;

    /**
     * Construct client connecting to HelloWorld server at {@code host:port}.
     */
    public RpcHelloServiceClient(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port)
                                  // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
                                  // needing certificates.
                                  .usePlaintext()
                                  .build());
    }

    /**
     * Construct client for accessing HelloWorld server using the existing channel.
     */
    RpcHelloServiceClient(ManagedChannel channel) {
        this.channel = channel;
        blockingStub = RpcHelloServiceGrpc.newBlockingStub(channel);
        futureStub = RpcHelloServiceGrpc.newFutureStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    /**
     * Say hello to server.
     *
     * @return message from the response
     */
    public String sayHello() {
        logger.info("Will try to send a request ...");
        HelloRequest request = HelloRequest.newBuilder().build();
        HelloResponse response;
        try {
            response = blockingStub.sayHello(request);
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            return null;
        }
        logger.info("Greeting: " + response.getMessage());
        return response.getMessage();
    }

    /**
     * Say hello to server.
     *
     * @return future response
     */
    public ListenableFuture<HelloResponse> sayHelloAsync() {
        logger.info("Will try to send a request ...");
        HelloRequest request = HelloRequest.newBuilder().build();
        ListenableFuture<HelloResponse> response;
        try {
            response = futureStub.sayHello(request);
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            return null;
        }
        return response;
    }

    /**
     * Greet server. If provided, the first element of {@code args} is the name to use in the
     * greeting.
     */
    public static void main(String[] args) throws Exception {
        // Access a service running on the local machine on port 50051
        RpcHelloServiceClient client = new RpcHelloServiceClient("localhost", 50051);
        try {
            String user = "world";
            // Use the arg as the name to greet if provided
            if (args.length > 0) {
                user = args[0];
            }
            client.sayHello();
        } finally {
            client.shutdown();
        }
    }
}
