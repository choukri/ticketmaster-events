package com.ticketmaster.events.api.exception;

import com.ticketmaster.events.model.ErrorRestResource;
import com.ticketmaster.events.model.ProblemRestResource;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<ProblemRestResource> handleEventNotFound(EventNotFoundException ex) {
        ErrorRestResource errorRestResource = new ErrorRestResource().code("error.event.not.found").detail(ex.getMessage()).field("eventId");
        return new ResponseEntity<>(new ProblemRestResource().addErrorsItem(errorRestResource), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotEnoughSeatsAvailableException.class)
    public ResponseEntity<ProblemRestResource> handleNotEnoughSeats(NotEnoughSeatsAvailableException ex) {
        ErrorRestResource errorRestResource = new ErrorRestResource().code("error.seats.available.not.enough").detail(ex.getMessage()).field("numberOfSeats");

        return new ResponseEntity<>(new ProblemRestResource().addErrorsItem(errorRestResource), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ProblemRestResource> handleMethodArgumentNotValidException(MethodArgumentTypeMismatchException ex) {
        ErrorRestResource errorRestResource = new ErrorRestResource().code("error.field.not.valid").detail(ex.getMessage()).field(ex.getParameter().getParameterName());
        return new ResponseEntity<>(new ProblemRestResource().addErrorsItem(errorRestResource), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ProblemRestResource> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        ErrorRestResource errorRestResource = new ErrorRestResource().code("error.field.required").detail(ex.getMessage()).field(ex.getParameterName());
        return new ResponseEntity<>(new ProblemRestResource().addErrorsItem(errorRestResource), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ProblemRestResource> handleConstraintViolationException(ConstraintViolationException ex) {
        ErrorRestResource errorRestResource = new ErrorRestResource().code("error.field.not.valid").detail(ex.getMessage());
        return new ResponseEntity<>(new ProblemRestResource().addErrorsItem(errorRestResource), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ProblemRestResource> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorRestResource errorRestResource = new ErrorRestResource().code("error.field.not.valid").detail(ex.getMessage());
        return new ResponseEntity<>(new ProblemRestResource().addErrorsItem(errorRestResource), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ProblemRestResource> handleNoResourceFoundException(NoResourceFoundException ex) {
        ErrorRestResource errorRestResource = new ErrorRestResource().code("error.resource.not.found").detail(ex.getMessage());
        return new ResponseEntity<>(new ProblemRestResource().addErrorsItem(errorRestResource), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemRestResource> handleGenericException(Exception ex) {
        ErrorRestResource errorRestResource = new ErrorRestResource().code("error.generic").detail(ex.getMessage());
        return new ResponseEntity<>(new ProblemRestResource().addErrorsItem(errorRestResource), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
