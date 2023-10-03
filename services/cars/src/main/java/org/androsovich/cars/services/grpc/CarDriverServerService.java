package org.androsovich.cars.services.grpc;

import io.grpc.stub.StreamObserver;
import org.androsovich.CarDriverServiceGrpc;
import org.androsovich.Driver;

public class CarDriverServerService extends CarDriverServiceGrpc.CarDriverServiceImplBase {
    @Override
    public void getDriver(Driver request, StreamObserver<Driver> responseObserver) {
        super.getDriver(request, responseObserver);
    }
}