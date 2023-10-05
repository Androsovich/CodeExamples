package org.androsovich.cars.services.grpc;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.androsovich.CarDriverServiceGrpc;
import org.androsovich.Driver;
import org.androsovich.cars.services.CarServerServiceGrpc;

@GrpcService
@Slf4j
public class CarDriverServerServiceImpl extends CarDriverServiceGrpc.CarDriverServiceImplBase implements CarServerServiceGrpc {

    @Override
    public void getDriver(Driver request, StreamObserver<Driver> responseObserver) {
        responseObserver.onNext(request.toBuilder().setDriverId(100).build());
        responseObserver.onCompleted();
    }
}