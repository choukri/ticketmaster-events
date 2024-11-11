package com.ticketmaster.events.infrastructure.repository;

import com.ticketmaster.events.api.exception.EventNotFoundException;
import com.ticketmaster.events.domain.model.Event;
import com.ticketmaster.events.domain.model.Seat;
import com.ticketmaster.events.infrastructure.datasource.CSVDataLoader;
import com.ticketmaster.events.infrastructure.repository.SeatRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SeatRepositoryImplTest {

    @Mock
    private CSVDataLoader csvDataLoader;

    @InjectMocks
    private SeatRepositoryImpl seatRepository;

    @BeforeEach
    void setUp() {
        Seat seat1 = new Seat(1L, 1L, "A", "1", null, 1L, true);
        Seat seat2 = new Seat(2L, 1L, "A", "2", null, 2L, true);
        Event event = new Event(123L, "Concert", null, List.of(seat1, seat2));
        when(csvDataLoader.getEventMap()).thenReturn(Map.of(123L, event));
    }

    @Test
    void testFindSeatsByEventId_ValidEvent() {
        List<Seat> seats = seatRepository.findSeatsByEventId(123L);
        assertNotNull(seats);
        assertEquals(2, seats.size());
        assertEquals(1L, seats.get(0).getSeatNumber());
        assertEquals(2L, seats.get(1).getSeatNumber());
        verify(csvDataLoader, times(1)).getEventMap();
    }

    @Test
    void testFindSeatsByEventId_EventNotFound() {
        when(csvDataLoader.getEventMap()).thenReturn(Map.of());
        EventNotFoundException exception = assertThrows(EventNotFoundException.class, () -> seatRepository.findSeatsByEventId(999L));
        assertEquals("Événement non trouvé avec l'ID: 999", exception.getMessage());
        verify(csvDataLoader, times(1)).getEventMap();
    }
}
