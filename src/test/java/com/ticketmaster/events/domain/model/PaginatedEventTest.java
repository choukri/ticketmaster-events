package com.ticketmaster.events.domain.model;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaginatedEventTest {

    @Test
    void testPaginatedEventConstructorAndGetters() {
        Event event = new Event(1L, "Concert", null, Collections.emptyList());
        List<Event> events = List.of(event);
        PaginatedEvent paginatedEvent = new PaginatedEvent(events, 1, 1, 1);
        assertEquals(1, paginatedEvent.getTotalElements());
        assertEquals(1, paginatedEvent.getTotalPages());
        assertEquals(1, paginatedEvent.getPage());
        assertEquals(events, paginatedEvent.getEvents());
    }
}
