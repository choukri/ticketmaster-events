package com.ticketmaster.events.api.exception;

import com.ticketmaster.events.model.ErrorRestResource;
import com.ticketmaster.events.model.ProblemRestResource;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Mock
    private MethodParameter methodParameter;

    @Mock
    private ConstraintViolationException constraintViolationException;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHandleEventNotFoundException() {
        EventNotFoundException ex = new EventNotFoundException("Événement non trouvé avec l'ID: 1");

        ResponseEntity<ProblemRestResource> response = globalExceptionHandler.handleEventNotFound(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("error.event.not.found", response.getBody().getErrors().get(0).getCode());
    }

    @Test
    void testHandleNotEnoughSeatsAvailableException() {
        NotEnoughSeatsAvailableException ex = new NotEnoughSeatsAvailableException();

        ResponseEntity<ProblemRestResource> response = globalExceptionHandler.handleNotEnoughSeats(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("error.seats.available.not.enough", response.getBody().getErrors().get(0).getCode());
    }

    @Test
    void testHandleMethodArgumentNotValidException() {
        MethodArgumentTypeMismatchException ex = mock(MethodArgumentTypeMismatchException.class);
        when(ex.getMessage()).thenReturn("Invalid argument");
        when(ex.getParameter()).thenReturn(methodParameter);
        when(ex.getParameter().getParameterName()).thenReturn("numberOfSeats");

        ResponseEntity<ProblemRestResource> response = globalExceptionHandler.handleMethodArgumentNotValidException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("error.field.not.valid", response.getBody().getErrors().get(0).getCode());
        assertEquals("numberOfSeats", response.getBody().getErrors().get(0).getField());
    }

    @Test
    void testHandleMissingServletRequestParameterException() {
        MissingServletRequestParameterException ex = new MissingServletRequestParameterException("numberOfSeats", "String");

        ResponseEntity<ProblemRestResource> response = globalExceptionHandler.handleMissingServletRequestParameterException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("error.field.required", response.getBody().getErrors().get(0).getCode());
    }

    @Test
    void testHandleIllegalArgumentException() {
        IllegalArgumentException ex = new IllegalArgumentException("Illegal argument");

        ResponseEntity<ProblemRestResource> response = globalExceptionHandler.handleIllegalArgumentException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("error.field.not.valid", response.getBody().getErrors().get(0).getCode());
    }

    @Test
    void testHandleNoResourceFoundException() {
        NoResourceFoundException ex = new NoResourceFoundException(HttpMethod.GET, "Resource not found");

        ResponseEntity<ProblemRestResource> response = globalExceptionHandler.handleNoResourceFoundException(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("error.resource.not.found", response.getBody().getErrors().get(0).getCode());
    }

    @Test
    void testHandleGenericException() {
        Exception ex = new Exception("Generic error");

        ResponseEntity<ProblemRestResource> response = globalExceptionHandler.handleGenericException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("error.generic", response.getBody().getErrors().get(0).getCode());
    }

    @Test
    void handleConstraintViolationException_ShouldReturnBadRequest() {
        String errorMessage = "Field value is invalid";
        when(constraintViolationException.getMessage()).thenReturn(errorMessage);
        ResponseEntity<ProblemRestResource> responseEntity = globalExceptionHandler.handleConstraintViolationException(constraintViolationException);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        ProblemRestResource body = responseEntity.getBody();
        assertNotNull(body);
        assertEquals(1, body.getErrors().size());
        ErrorRestResource error = body.getErrors().get(0);
        assertEquals("error.field.not.valid", error.getCode());
        assertEquals(errorMessage, error.getDetail());
    }
}
