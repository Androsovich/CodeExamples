package org.androsovich.drivers.exceptions;

public class DriverServiceException extends RuntimeException{
    public DriverServiceException() {
    }

    public DriverServiceException(String message) {
        super(message);
    }

    public DriverServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public DriverServiceException(Throwable cause) {
        super(cause);
    }

    public DriverServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
