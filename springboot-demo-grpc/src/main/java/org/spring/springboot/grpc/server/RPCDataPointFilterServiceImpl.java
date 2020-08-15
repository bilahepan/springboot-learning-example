package org.spring.springboot.grpc.server;

import com.demo.grpc.api.*;
import io.grpc.stub.StreamObserver;

public class RPCDataPointFilterServiceImpl extends RPCDataPointFilterServiceGrpc.RPCDataPointFilterServiceImplBase {


    @Override
    public void getFilter(RPCFilterRequest request, StreamObserver<RPCFilterResponse> responseObserver) {
        RPCFilterResponse response = null;
        try {
            if ("devId".equals(request.getDevId())) {
                response = RPCFilterResponse.newBuilder().setRes(true).build();
            } else {
                response = RPCFilterResponse.newBuilder().setRes(false).build();
            }
        } catch (Exception e) {
            responseObserver.onError(e);
        } finally {
            responseObserver.onNext(response);
        }
        responseObserver.onCompleted();
    }


    @Override
    public StreamObserver<HelloRequest> doubleStream(StreamObserver<HelloReply> responseObserver) {
        return new StreamObserver<HelloRequest>() {
            @Override
            public void onNext(HelloRequest request) {
                //System.err.println("收到请求:x=" + request.getX());
                //
                HelloReply result = HelloReply.newBuilder().setY("response reply.input={" + request.getX() + "} t=" + System.currentTimeMillis()).build();
                responseObserver.onNext(result);
            }

            @Override
            public void onError(Throwable throwable) {
                System.err.println(throwable.getStackTrace().toString());
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }

}
