package org.spring.springboot.grpc.client;

import com.demo.grpc.api.RPCDataPointFilterServiceGrpc;
import com.demo.grpc.api.RPCFilterRequest;
import com.demo.grpc.api.RPCFilterResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

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
        } finally {
            managedChannel.shutdown();
        }
    }
}
