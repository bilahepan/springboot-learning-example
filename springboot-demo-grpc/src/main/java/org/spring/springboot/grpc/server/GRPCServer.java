package org.spring.springboot.grpc.server;


import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.util.concurrent.TimeUnit;

public class GRPCServer {
    private static final int port = 9999;

    public static void main(String[] args) throws Exception {
        Server server = ServerBuilder.forPort(port)
                .addService(new RPCDataPointFilterServiceImpl())
                .build()
                .start();
        System.out.println("---grpc server started success! port=" + port + "---");
        server.awaitTermination(12,TimeUnit.HOURS);
        //---
    }


}
