package com.ticketmaster.events.application.service;

import com.ticketmaster.events.api.exception.NotEnoughSeatsAvailableException;
import com.ticketmaster.events.domain.enumeration.Status;
import com.ticketmaster.events.domain.model.Seat;
import com.ticketmaster.events.domain.repository.SeatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SeatServiceTest {

    @Mock
    private SeatRepository seatRepository;

    @InjectMocks
    private SeatService seatService;

    private List<Seat> availableSeats;

    @BeforeEach
    void setUp() {
        availableSeats = Arrays.asList(
                new Seat(1L, 1L, "Level 1", "A", Status.OPEN, 10L, true),
                new Seat(2L, 1L, "Level 1", "B", Status.OPEN, 5L, true),
                new Seat(3L, 1L, "Level 1", "C", Status.SOLD, 7L, false),
                new Seat(4L, 2L, "Level 1", "D", Status.OPEN, 15L, true)
        );
    }

    @Test
    void testGetBestSeatsWithEnoughSeats() {
        when(seatRepository.findSeatsByEventId(1L)).thenReturn(availableSeats);
        long eventId = 1L;
        Long numberOfSeats = 2L;
        List<Seat> bestSeats = seatService.getBestSeats(eventId, numberOfSeats);
        assertNotNull(bestSeats);
        assertEquals(2, bestSeats.size());
        assertEquals(15L, bestSeats.get(0).getSellRank());
        assertEquals(10L, bestSeats.get(1).getSellRank());

        verify(seatRepository, times(1)).findSeatsByEventId(eventId);
    }

    @Test
    void testGetBestSeatsWithNotEnoughSeats() {
        when(seatRepository.findSeatsByEventId(1L)).thenReturn(availableSeats);

        long eventId = 1L;
        Long numberOfSeats = 5L;

        NotEnoughSeatsAvailableException exception = assertThrows(
                NotEnoughSeatsAvailableException.class,
                () -> seatService.getBestSeats(eventId, numberOfSeats)
        );
        assertEquals("Pas assez de sièges disponibles", exception.getMessage());
        verify(seatRepository, times(1)).findSeatsByEventId(eventId);
    }

    @Test
    void testGetBestSeatsWithOnlySoldSeats() {
        List<Seat> soldSeats = Arrays.asList(
                new Seat(1L, 1L, "Level 1", "A", Status.SOLD, 5L, false),
                new Seat(2L, 1L, "Level 1", "B", Status.SOLD, 7L, false)
        );
        when(seatRepository.findSeatsByEventId(1L)).thenReturn(soldSeats);

        long eventId = 1L;
        Long numberOfSeats = 2L;
        NotEnoughSeatsAvailableException exception = assertThrows(
                NotEnoughSeatsAvailableException.class,
                () -> seatService.getBestSeats(eventId, numberOfSeats)
        );
        assertEquals("Pas assez de sièges disponibles", exception.getMessage());
        verify(seatRepository, times(1)).findSeatsByEventId(eventId);
    }
}
