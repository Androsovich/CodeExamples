package org.androsovich.drivers.controllers.adviceImpl;

import org.androsovich.drivers.exceptions.AccountNotFoundException;
import org.androsovich.drivers.exceptions.DriverNotFoundException;
import org.androsovich.drivers.exceptions.DriverServiceException;
import org.androsovich.drivers.exceptions.NotEnoughMoneyException;
import org.springframework.hateoas.mediatype.vnderrors.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.Optional;

@ControllerAdvice
@RequestMapping(produces = "application/vnd.error+json")
public class ExceptionControllerAdvice {
    @ResponseBody
    @ExceptionHandler(DriverServiceException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<VndErrors> notFoundException(final DriverServiceException e) {
        return error(e, HttpStatus.NOT_FOUND, e.getLocalizedMessage());
    }

    @ResponseBody
    @ExceptionHandler(HttpStatusCodeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<VndErrors> httpStatusException(final HttpStatusCodeException e) {
        return error(e, HttpStatus.INTERNAL_SERVER_ERROR, e.getResponseBodyAsString());
    }

    private ResponseEntity<VndErrors> error(
            final Exception exception, final HttpStatus httpStatus, final String logRef) {
        final String message =
                Optional.of(exception.getMessage()).orElse(exception.getClass().getSimpleName());
        return new ResponseEntity<>(new VndErrors(logRef, message), httpStatus);
    }
}


