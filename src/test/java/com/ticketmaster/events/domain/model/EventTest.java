package com.ticketmaster.events.domain.model;

import com.ticketmaster.events.domain.enumeration.Status;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EventTest {

    @Test
    void testEventGettersAndConstructor() {
        LocalDateTime eventDate = LocalDateTime.of(2024, 5, 15, 20, 0);
        List<Seat> seats = List.of(new Seat(1L, 1L, "E", "A", Status.OPEN, 1L, true));
        Event event = new Event(101L, "Concert", eventDate, seats);
        assertEquals(101L, event.getEventId());
        assertEquals("Concert", event.getEventName());
        assertEquals(eventDate, event.getEventDate());
        assertEquals(seats, event.getSeats());
    }
}
