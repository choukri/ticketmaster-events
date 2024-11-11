package com.ticketmaster.events.domain.model;

import com.ticketmaster.events.domain.enumeration.Status;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;


class SeatTest {


    @Test
    void testGettersAndSetters() {
        Seat seat = new Seat();
        seat.setSeatNumber(101L);
        seat.setRow(5L);
        seat.setLevel("E");
        seat.setSection("A");
        seat.setStatus(Status.OPEN);
        seat.setSellRank(1L);
        seat.setHasUpsells(true);
        assertEquals(101L, seat.getSeatNumber());
        assertEquals(5L, seat.getRow());
        assertEquals("E", seat.getLevel());
        assertEquals("A", seat.getSection());
        assertEquals(Status.OPEN, seat.getStatus());
        assertEquals(1L, seat.getSellRank());
        assertTrue(seat.isHasUpsells());
    }
}
