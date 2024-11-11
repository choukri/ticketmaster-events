package com.ticketmaster.events.infrastructure.repository;

import com.ticketmaster.events.domain.model.Event;
import com.ticketmaster.events.infrastructure.datasource.CSVDataLoader;
import com.ticketmaster.events.infrastructure.repository.EventRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class EventRepositoryImplTest {

    @Mock
    private CSVDataLoader csvDataLoader;

    @InjectMocks
    private EventRepositoryImpl eventRepository;

    private Map<Long, Event> eventMap;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        eventMap = new HashMap<>();
        eventMap.put(1L, new Event(1L, "Event 1", null, null));
        eventMap.put(2L, new Event(2L, "Event 2", null, null));
    }

    @Test
    void testFindAllEvents_WhenEventsExist() {
        when(csvDataLoader.getEventMap()).thenReturn(eventMap);
        List<Event> events = eventRepository.findAllEvents();
        assertNotNull(events);
        assertEquals(2, events.size());
        assertEquals("Event 1", events.get(0).getEventName());
        assertEquals("Event 2", events.get(1).getEventName());
    }

    @Test
    void testFindAllEvents_WhenNoEventsExist() {
        when(csvDataLoader.getEventMap()).thenReturn(new HashMap<>());
        List<Event> events = eventRepository.findAllEvents();
        assertNotNull(events);
        assertTrue(events.isEmpty());
    }

    @Test
    void testFindAllEvents_WhenEventMapIsNull() {
        when(csvDataLoader.getEventMap()).thenReturn(null);
        List<Event> events = eventRepository.findAllEvents();
        assertNotNull(events);
        assertTrue(events.isEmpty());
    }
}
