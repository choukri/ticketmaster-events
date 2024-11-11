package com.ticketmaster.events.domain.enumeration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SortByTest {

    @Test
    void testFromStringValidValues() {
        assertEquals(SortBy.ALPHABETICAL, SortBy.fromString("alphabetical"));
        assertEquals(SortBy.DATE, SortBy.fromString("date"));
        assertEquals(SortBy.DEFAULT, SortBy.fromString("default"));
    }

    @Test
    void testFromStringValidValuesCaseInsensitive() {
        assertEquals(SortBy.ALPHABETICAL, SortBy.fromString("Alphabetical"));
        assertEquals(SortBy.DATE, SortBy.fromString("Date"));
        assertEquals(SortBy.DEFAULT, SortBy.fromString("Default"));
    }
    @Test
    void testFromStringInvalidValue() {
        assertEquals(SortBy.DEFAULT, SortBy.fromString("invalidValue"));
        assertEquals(SortBy.DEFAULT, SortBy.fromString(""));
    }

    @Test
    void testEnumValues() {
        assertEquals(SortBy.ALPHABETICAL, SortBy.ALPHABETICAL);
        assertEquals(SortBy.DATE, SortBy.DATE);
        assertEquals(SortBy.DEFAULT, SortBy.DEFAULT);
    }
}

