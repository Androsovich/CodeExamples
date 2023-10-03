package org.androsovich.drivers.services.grpc;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.androsovich.Car;
import org.androsovich.Driver;
import org.androsovich.DriverCarServiceGrpc;

@GrpcService
public class DriverCarServerService extends DriverCarServiceGrpc.DriverCarServiceImplBase {
    @Override
    public void getCarByDriver(Driver request, StreamObserver<Car> responseObserver) {
        super.getCarByDriver(request, responseObserver);
    }
}
