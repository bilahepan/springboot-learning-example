package org.spring.springboot.grpc.client;

import com.demo.grpc.api.HelloReply;
import com.demo.grpc.api.HelloRequest;
import com.demo.grpc.api.RPCDataPointFilterServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.Executors.newScheduledThreadPool;

public class GRPCClient {
    private static final String host = "localhost";
    private static final int serverPort = 9998;

    public static void main(String[] args) throws Exception {
        //
        //            RPCDataPointFilterServiceGrpc.RPCDataPointFilterServiceBlockingStub rpcStubService = RPCDataPointFilterServiceGrpc.newBlockingStub(managedChannel);
        //            RPCFilterRequest request = RPCFilterRequest.newBuilder().setDevId("devId")
        //                    .setOwnerId("ownerId")
        //                    .setPid("pid")
        //                    .setOwnerId("ownerId")
        //                    .setUid("uid").build();
        //
        //            RPCFilterResponse response = rpcStubService.getFilter(request);
        //
        //            System.out.println(response.getRes());

        ArrayList<String> nsList = new ArrayList() {{
            add("devId4ProductId");
            add("uid4DevId");
        }};
        //
        for (String namespace : nsList) {
//            new Thread(() -> {
//                subscribe(namespace);
//            }).start();
            subscribe(namespace);
        }
        System.out.println("--end--");
    }


    public static void subscribe(String namespace) {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress(host, serverPort).usePlaintext().build();
        ScheduledExecutorService keepAliveExector = newScheduledThreadPool(1);
        //下面是双向流的操作，需要使用非阻塞的stub
        RPCDataPointFilterServiceGrpc.RPCDataPointFilterServiceStub rpcStubService = RPCDataPointFilterServiceGrpc.newStub(managedChannel);
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

        HelloRequest streamRequest = HelloRequest.newBuilder().setX(namespace+" : hello world,t=" + System.currentTimeMillis()).build();
        StreamObserver<HelloRequest> requestStreamObserver = rpcStubService.doubleStream(responseObserver);

        requestStreamObserver.onNext(streamRequest);
        System.out.println("---" + namespace + "---");
        keepAliveExector.scheduleAtFixedRate(() -> {
            requestStreamObserver.onNext(streamRequest);
        }, 10, 10, TimeUnit.SECONDS);
        //        requestStreamObserver.onCompleted();
    }
}
