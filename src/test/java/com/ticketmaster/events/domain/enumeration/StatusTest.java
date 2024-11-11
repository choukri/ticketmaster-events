package com.ticketmaster.events.domain.enumeration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StatusTest {

    @Test
    void testFromStringValidValues() {
        assertEquals(Status.OPEN, Status.fromString("OPEN"));
        assertEquals(Status.SOLD, Status.fromString("SOLD"));
        assertEquals(Status.HOLD, Status.fromString("HOLD"));
    }
    @Test
    void testFromStringValidValuesCaseInsensitive() {
        assertEquals(Status.OPEN, Status.fromString("open"));
        assertEquals(Status.SOLD, Status.fromString("sold"));
        assertEquals(Status.HOLD, Status.fromString("hold"));
    }
    @Test
    void testFromStringInvalidValue() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Status.fromString("invalid");
        });

        assertEquals("Unknown status: invalid", exception.getMessage());
    }
    @Test
    void testEnumValues() {
        assertEquals(Status.OPEN, Status.OPEN);
        assertEquals(Status.SOLD, Status.SOLD);
        assertEquals(Status.HOLD, Status.HOLD);
    }
}

