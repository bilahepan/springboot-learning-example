package org.spring.springboot.grpc.server;

import com.demo.grpc.api.RPCDataPointFilterServiceGrpc;
import com.demo.grpc.api.RPCFilterRequest;
import com.demo.grpc.api.RPCFilterResponse;
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
}
