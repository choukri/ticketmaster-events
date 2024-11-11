package com.ticketmaster.events.api.mapping;

import com.ticketmaster.events.domain.enumeration.Status;
import com.ticketmaster.events.domain.model.Seat;
import com.ticketmaster.events.model.BestSeatsResource;
import com.ticketmaster.events.model.SeatResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SeatMapperTest {

    private SeatMapper seatMapper;

    @BeforeEach
    void setUp() {
        // Initialize the mapper
        seatMapper = Mappers.getMapper(SeatMapper.class);
    }

    @Test
    void testToResourceWithNullSeatsList() {
        Long eventId = 123L;
        BestSeatsResource bestSeatsResource = seatMapper.toResource(eventId, null);
        assertNotNull(bestSeatsResource);
        assertEquals(123L, bestSeatsResource.getEventId());
        assertNull(bestSeatsResource.getSelectedSeats());
    }

    @Test
    void testToResourceWithEmptySeatsList() {
        Long eventId = 123L;
        List<Seat> seats = new ArrayList<>();
        BestSeatsResource bestSeatsResource = seatMapper.toResource(eventId, seats);
        assertNotNull(bestSeatsResource);
        assertEquals(123L, bestSeatsResource.getEventId());
        assertNotNull(bestSeatsResource.getSelectedSeats());
        assertTrue(bestSeatsResource.getSelectedSeats().isEmpty());  // Ensure the list is empty
    }

    @Test
    void testToResourceWithValidSeatsList() {
        Long eventId = 123L;
        Seat seat1 = new Seat(1L,5L,"E","A", Status.OPEN,95L,false);
        Seat seat2 = new Seat(2L,5L,"E","A",Status.OPEN,95L,false);
        List<Seat> seats = List.of(seat1, seat2);
        BestSeatsResource bestSeatsResource = seatMapper.toResource(eventId, seats);
        assertNotNull(bestSeatsResource);
        assertEquals(123L, bestSeatsResource.getEventId());
        assertNotNull(bestSeatsResource.getSelectedSeats());
        assertEquals(2, bestSeatsResource.getSelectedSeats().size());  // Ensure there are 2 seats in the result
        SeatResource seatResource1 = bestSeatsResource.getSelectedSeats().get(0);
        assertEquals(1L, seatResource1.getSeatNumber());
        assertEquals(5L, seatResource1.getRow());
        assertEquals("E", seatResource1.getLevel());
        assertEquals("A", seatResource1.getSection());
        SeatResource seatResource2 = bestSeatsResource.getSelectedSeats().get(1);
        assertEquals(2L, seatResource2.getSeatNumber());
        assertEquals(5L, seatResource2.getRow());
        assertEquals("E", seatResource2.getLevel());
        assertEquals("A", seatResource2.getSection());
    }

    @Test
    void testToResourceWithNullSeat() {
        Long eventId = 123L;
        List<Seat> seats = new ArrayList<>();
        seats.add(null);
        BestSeatsResource bestSeatsResource = seatMapper.toResource(eventId, seats);
        assertNotNull(bestSeatsResource);
        assertEquals(123L, bestSeatsResource.getEventId());
        assertNotNull(bestSeatsResource.getSelectedSeats());
        assertEquals(1, bestSeatsResource.getSelectedSeats().size());
        assertNull(bestSeatsResource.getSelectedSeats().get(0));
    }

    @Test
    void testToResourceWithNullEventId() {
        List<Seat> seats = new ArrayList<>();
        BestSeatsResource bestSeatsResource = seatMapper.toResource(null, seats);
        assertNotNull(bestSeatsResource);
        assertNull(bestSeatsResource.getEventId()); // EventId should be null
        assertNotNull(bestSeatsResource.getSelectedSeats());
        assertTrue(bestSeatsResource.getSelectedSeats().isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = {"HOLD", "SOLD", "OPEN","null"})
    void testToResourceWithSeatStatus(String status) {
        Long eventId = 123L;
        Seat seat = new Seat(1L,5L,"E","A", Status.OPEN,95L,false);

        if ("HOLD".equals(status)) {
            seat.setStatus(Status.HOLD);
        } else if ("SOLD".equals(status)) {
            seat.setStatus(Status.SOLD);
        } else if ("OPEN".equals(status)) {
            seat.setStatus(Status.OPEN);
        }else {
            seat.setStatus(null);
        }
        List<Seat> seats = List.of(seat);
        BestSeatsResource bestSeatsResource = seatMapper.toResource(eventId, seats);
        assertNotNull(bestSeatsResource);
        assertEquals(123L, bestSeatsResource.getEventId());
        assertNotNull(bestSeatsResource.getSelectedSeats());
        assertEquals(1, bestSeatsResource.getSelectedSeats().size());
        SeatResource seatResource = bestSeatsResource.getSelectedSeats().get(0);
        assertNotNull(seatResource);
        assertEquals(1L, seatResource.getSeatNumber());
        assertEquals(5L, seatResource.getRow());
        assertEquals("E", seatResource.getLevel());
        assertEquals("A", seatResource.getSection());

        if ("HOLD".equals(status)) {
            assertEquals("HOLD", seatResource.getStatus().toString());
        } else if ("SOLD".equals(status)) {
            assertEquals("SOLD", seatResource.getStatus().toString());
        } else if ("OPEN".equals(status)) {
            assertEquals("OPEN", seatResource.getStatus().toString());
        }  else {
            assertNull(seatResource.getStatus());
        }
    }
}
