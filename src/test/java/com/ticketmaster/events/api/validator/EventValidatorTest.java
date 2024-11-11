package com.ticketmaster.events.api.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class EventValidatorTest {

    private EventValidator eventValidator;

    @BeforeEach
    void setUp() {
        eventValidator = new EventValidator();
    }

    @ParameterizedTest
    @ValueSource(strings = {"alphabetical", "date", "alphabetical,date"})
    void testValidateEventRequest_validSortBy(String sortBy) {
        Long page = 0L;
        Long size = 10L;
        assertDoesNotThrow(() -> eventValidator.validateEventRequest(sortBy, page, size));
    }

    @ParameterizedTest
    @ValueSource(strings = {"invalid", "alphabetical,date, test1, test2", "alphabetical, test1, test2",
            "date, test1, test2"})
    void testValidateEventRequest_invalidSortBy(String sortBy) {
        Long page = 0L;
        Long size = 10L;
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                eventValidator.validateEventRequest(sortBy, page, size)
        );
        assertEquals("Valeur de sortBy invalide. Les valeurs possibles sont 'alphabetical', 'date' ou 'alphabetical,date'.", exception.getMessage());
    }

    @Test
    void testValidateEventRequest_validPage() {
        String sortBy = "alphabetical";
        Long size = 10L;
        Long page = 10L;
        assertDoesNotThrow(() -> eventValidator.validateEventRequest(sortBy, page, size));
    }

    @Test
    void testValidateEventRequest_invalidPage() {
        String sortBy = "alphabetical";
        Long size = 10L;
        Long page = -1L;
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                eventValidator.validateEventRequest(sortBy, page, size)
        );
        assertEquals("Le numéro de page est invalide", exception.getMessage());
    }

    @Test
    void testValidateEventRequest_validSize() {
        String sortBy = "alphabetical";
        Long page = 0L;
        Long size = 10L;
        assertDoesNotThrow(() -> eventValidator.validateEventRequest(sortBy, page, size));
    }

    @Test
    void testValidateEventRequest_invalidSize() {
        String sortBy = "alphabetical";
        Long page = 0L;
        Long size = -1L;
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                eventValidator.validateEventRequest(sortBy, page, size)
        );
        assertEquals("Le nombre d'éléments par page est invalide", exception.getMessage());
    }
}
