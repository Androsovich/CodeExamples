package org.androsovich.cars.services;

import io.grpc.stub.StreamObserver;
import org.androsovich.Driver;

public interface CarServerServiceGrpc {
    public void getDriver(Driver request, StreamObserver<Driver> responseObserver);
}
