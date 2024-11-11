package com.ticketmaster.events.api.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SeatValidatorTest {

    private SeatValidator seatValidator;

    @BeforeEach
    void setUp() {
        seatValidator = new SeatValidator();
    }

    @Test
    void testValidateSeatRequest_validInputs() {
        assertDoesNotThrow(() -> seatValidator.validateSeatRequest(1L, 10L));
        assertDoesNotThrow(() -> seatValidator.validateSeatRequest(100L, 50L));
    }

    @Test
    void testValidateSeatRequest_invalidEventId() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> seatValidator.validateSeatRequest(-1L, 10L));
        assertEquals("Le numéro de l'événement est invalide.", exception.getMessage());
    }

    @Test
    void testValidateSeatRequest_invalidNumberOfSeats() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> seatValidator.validateSeatRequest(1L, -1L));
        assertEquals("Le nombre de siége est invalide.", exception.getMessage());
    }
}
