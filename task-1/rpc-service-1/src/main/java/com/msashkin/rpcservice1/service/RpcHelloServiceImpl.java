package com.msashkin.rpcservice1.service;

import com.msashkin.rpchelloservice.service.HelloRequest;
import com.msashkin.rpchelloservice.service.HelloResponse;
import com.msashkin.rpchelloservice.service.RpcHelloServiceGrpc;
import io.grpc.stub.StreamObserver;

public class RpcHelloServiceImpl extends RpcHelloServiceGrpc.RpcHelloServiceImplBase {

    @Override
    public void sayHello(HelloRequest req, StreamObserver<HelloResponse> responseObserver) {
        HelloResponse response = HelloResponse.newBuilder().setMessage("Hello from gRPC service #1").build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
