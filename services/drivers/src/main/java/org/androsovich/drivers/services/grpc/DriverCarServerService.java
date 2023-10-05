package org.androsovich.drivers.services.grpc;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.androsovich.Car;
import org.androsovich.Driver;
import org.androsovich.DriverCarServiceGrpc;

@GrpcService
@Slf4j
public class DriverCarServerService extends DriverCarServiceGrpc.DriverCarServiceImplBase {
    @Override
    public void getCarByDriver(Driver request, StreamObserver<Car> responseObserver) {
        log.info("inside method getCarByDriver :: DriverCarServerService");
        super.getCarByDriver(request, responseObserver);
        responseObserver.onCompleted();
    }
}
