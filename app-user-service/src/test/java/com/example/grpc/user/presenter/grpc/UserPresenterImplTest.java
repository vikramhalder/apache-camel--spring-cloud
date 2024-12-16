package com.example.grpc.user.presenter.grpc;

import com.example.user.gen.grpc.UserServiceProto;
import com.example.user.gen.grpc.UserServiceProtoRouteGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;

@Slf4j
class UserPresenterImplTest {
    private final UserServiceProtoRouteGrpc.UserServiceProtoRouteStub asyncStub;

    public UserPresenterImplTest(String host, int port) {
        final ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
        asyncStub = UserServiceProtoRouteGrpc.newStub(channel);
    }

    public void getAll() {
        UserServiceProto.UserFilterRequest request = UserServiceProto.UserFilterRequest.newBuilder().setId(10L).setName("Test").build();
        CountDownLatch latch = new CountDownLatch(1);

        final StreamObserver<UserServiceProto.ItemUserResponse> responseObserver = new StreamObserver<>() {
            @Override
            public void onNext(UserServiceProto.ItemUserResponse value) {
                log.info("Received Users: " + value.getUsersList());
            }

            @Override
            public void onCompleted() {
                latch.countDown();
            }


            @Override
            public void onError(Throwable t) {
                log.error("Throwable:", t);
            }
        };
        asyncStub.userGetAll(request, responseObserver);

        try {
            latch.await();
        } catch (InterruptedException e) {
            log.error("InterruptedException: ", e);
        }
    }

    public static void main(String[] args) {
        UserPresenterImplTest client = new UserPresenterImplTest("localhost", 8080);
        client.getAll();
    }
}