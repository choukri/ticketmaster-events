package com.ticketmaster.events.application.service;

import com.ticketmaster.events.domain.enumeration.Status;
import com.ticketmaster.events.domain.model.Event;
import com.ticketmaster.events.domain.model.PaginatedEvent;
import com.ticketmaster.events.domain.model.Seat;
import com.ticketmaster.events.domain.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    private List<Event> mockEvents;

    @BeforeEach
    void setUp() {
        mockEvents = new ArrayList<>();
        Event event1 = new Event(1L, "Event 1", LocalDateTime.of(2025, 5, 1, 10, 0), Arrays.asList(
                new Seat(1L, 1L, "E", "A", Status.OPEN, 1L, true),
                new Seat(2L, 1L, "E", "A", Status.SOLD, 2L, false)
        ));
        Event event2 = new Event(2L, "Event 2", LocalDateTime.of(2025, 6, 1, 10, 0), Arrays.asList(
                new Seat(1L, 1L, "E", "B", Status.OPEN, 1L, true),
                new Seat(2L, 1L, "E", "B", Status.SOLD, 2L, false)
        ));
        Event event3 = new Event(3L, "Event 3", LocalDateTime.of(2025, 7, 1, 10, 0), Arrays.asList(
                new Seat(1L, 1L, "E", "C", Status.OPEN, 1L, true),
                new Seat(2L, 1L, "E", "C", Status.SOLD, 2L, false)
        ));

        mockEvents.add(event1);
        mockEvents.add(event2);
        mockEvents.add(event3);
    }

    @Test
    void testGetPaginatedAvailableEventsSortedBy_WithValidParams_ShouldReturnPaginatedAndSortedEvents() {
        when(eventRepository.findAllEvents()).thenReturn(mockEvents);
        Long page = 0L;
        Long size = 2L;
        String sortBy = "alphabetical";
        PaginatedEvent result = eventService.getPaginatedAvailableEventsSortedBy(sortBy, page, size);
        verify(eventRepository, times(1)).findAllEvents();
        assertNotNull(result);
        assertEquals(3, result.getTotalElements());
        assertEquals(2, result.getTotalPages());
        assertEquals(page, result.getPage());
        assertEquals("Event 1", result.getEvents().get(0).getEventName());
        assertEquals("Event 2", result.getEvents().get(1).getEventName());
    }

    @Test
    void testGetPaginatedAvailableEventsSortedBy_WithInvalidPage_ShouldThrowException() {
        when(eventRepository.findAllEvents()).thenReturn(mockEvents);
        Long page = 999L;
        Long size = 2L;
        String sortBy = "alphabetical";
        assertThrows(IllegalArgumentException.class, () -> eventService.getPaginatedAvailableEventsSortedBy(sortBy, page, size));
    }

    @Test
    void testGetPaginatedAvailableEventsSortedBy_WithInvalidSize_ShouldThrowException() {
        when(eventRepository.findAllEvents()).thenReturn(mockEvents);
        Long page = 0L;
        Long size = -1L;
        String sortBy = "alphabetical";
        assertThrows(IllegalArgumentException.class, () -> eventService.getPaginatedAvailableEventsSortedBy(sortBy, page, size));
    }

    @Test
    void testGetPaginatedAvailableEventsSortedBy_WithEmptyEventList_ShouldReturnEmptyResult() {
        when(eventRepository.findAllEvents()).thenReturn(Collections.emptyList());

        Long page = 0L;
        Long size = 2L;
        String sortBy = "alphabetical";
        PaginatedEvent result = eventService.getPaginatedAvailableEventsSortedBy(sortBy, page, size);
        assertNotNull(result);
        assertEquals(0, result.getTotalElements());
        assertEquals(0, result.getTotalPages());
        assertEquals(page, result.getPage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"alphabetical", "date", "alphabetical,date"})
    void testGetPaginatedAvailableEventsSortedBy_WithDifferentSortByValues(String sortBy) {
        when(eventRepository.findAllEvents()).thenReturn(mockEvents);
        Long page = 0L;
        Long size = 2L;
        PaginatedEvent result = eventService.getPaginatedAvailableEventsSortedBy(sortBy, page, size);
        verify(eventRepository, times(1)).findAllEvents();
        assertNotNull(result);
        assertEquals(3, result.getTotalElements());
        assertEquals(2, result.getTotalPages());
        assertEquals(page, result.getPage());
        assertEquals("Event 1", result.getEvents().get(0).getEventName());
        assertEquals("Event 2", result.getEvents().get(1).getEventName());
    }

}
