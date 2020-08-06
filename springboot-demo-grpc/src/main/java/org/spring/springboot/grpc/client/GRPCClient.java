package org.spring.springboot.grpc.client;

import com.demo.grpc.api.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

public class GRPCClient {
    private static final String host = "localhost";
    private static final int serverPort = 9999;

    //
    public static void main(String[] args) throws Exception {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress(host, serverPort).usePlaintext().build();
        try {
            RPCDataPointFilterServiceGrpc.RPCDataPointFilterServiceBlockingStub rpcStubService = RPCDataPointFilterServiceGrpc.newBlockingStub(managedChannel);
            RPCFilterRequest request = RPCFilterRequest.newBuilder().setDevId("devId")
                    .setOwnerId("ownerId")
                    .setPid("pid")
                    .setOwnerId("ownerId")
                    .setUid("uid").build();
            //
            RPCFilterResponse response = rpcStubService.getFilter(request);

            System.out.println(response.getRes());
            /***/
            RPCDataPointFilterServiceGrpc.RPCDataPointFilterServiceStub rpcStubService2 = RPCDataPointFilterServiceGrpc.newStub(managedChannel);
            StreamObserver<HelloReply> responseObserver = new StreamObserver<HelloReply>() {
                @Override
                public void onNext(HelloReply helloReply) {
                    System.err.println("返回结果=" + helloReply.getY());
                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                }

                @Override
                public void onCompleted() {
                    System.err.println("--onCompleted--");
                }
            };

            HelloRequest streamRequest = HelloRequest.newBuilder().setX("hello world,t=" + System.currentTimeMillis()).build();
            StreamObserver<HelloRequest> streamResult = rpcStubService2.doubleStream(responseObserver);
            streamResult.onNext(streamRequest);
            streamResult.onNext(streamRequest);
            streamResult.onNext(streamRequest);
            streamResult.onCompleted();

            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally {
            managedChannel.shutdown();
        }
    }
}
