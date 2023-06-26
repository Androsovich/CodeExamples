package org.androsovich.cars.controllers.adviceImpl;

import org.androsovich.cars.exceptions.CarNotFoundException;
import org.androsovich.cars.exceptions.DetailNotFoundException;
import org.springframework.hateoas.mediatype.vnderrors.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Optional;

@ControllerAdvice
@RequestMapping(produces = "application/vnd.error+json")
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {
    @ResponseBody
    @ExceptionHandler({CarNotFoundException.class, DetailNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<VndErrors> notFoundException(final Exception e) {
        return error(e, HttpStatus.NOT_FOUND, e.getLocalizedMessage());
    }

    @ResponseBody
    @ExceptionHandler(HttpStatusCodeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<VndErrors> httpStatusException(final HttpStatusCodeException e) {
        return error(e, HttpStatus.NOT_FOUND, e.getResponseBodyAsString());
    }

    private ResponseEntity<VndErrors> error(
            final Exception exception, final HttpStatus httpStatus, final String logRef) {
        final String message =
                Optional.of(exception.getMessage()).orElse(exception.getClass().getSimpleName());
        return new ResponseEntity<>(new VndErrors(logRef, message), httpStatus);
    }
}