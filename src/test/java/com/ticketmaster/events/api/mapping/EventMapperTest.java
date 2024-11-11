package com.ticketmaster.events.api.mapping;

import com.ticketmaster.events.domain.model.Event;
import com.ticketmaster.events.domain.model.PaginatedEvent;
import com.ticketmaster.events.model.AvailableEventsResource;
import com.ticketmaster.events.model.EventResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EventMapperTest {

    private EventMapper eventMapper;

    @BeforeEach
    void setUp() {
        eventMapper = Mappers.getMapper(EventMapper.class);
    }

    @Test
    void testToResource() {
        List<Event> eventList = new ArrayList<>();
        Event event = new Event(123L,"Concert",LocalDateTime.now(),new ArrayList<>());
        eventList.add(event);
        PaginatedEvent paginatedEvent = new PaginatedEvent(eventList,100L,5,1);
        AvailableEventsResource availableEventsResource = eventMapper.toResource(paginatedEvent);
        assertNotNull(availableEventsResource);
        assertEquals(5, availableEventsResource.getTotalPages());
        assertEquals(100L, availableEventsResource.getTotalElements());
        assertEquals(1L, availableEventsResource.getPage());
        assertNotNull(availableEventsResource.getEvents());
        assertEquals(1, availableEventsResource.getEvents().size());
        EventResource mappedEvent = availableEventsResource.getEvents().get(0);
        assertEquals(123L, mappedEvent.getEventId());
        assertEquals("Concert", mappedEvent.getEventName());
        assertNotNull(mappedEvent.getEventDate());
    }
    @Test
    void testToResourceWithNullPaginatedEvent() {
        AvailableEventsResource availableEventsResource = eventMapper.toResource(null);
        assertNull(availableEventsResource);
    }
    @Test
    void testToResourceWithNullEventList() {
        PaginatedEvent paginatedEvent = new PaginatedEvent(null,100L,5,1);
        AvailableEventsResource availableEventsResource = eventMapper.toResource(paginatedEvent);
        assertNotNull(availableEventsResource);
        assertEquals(5, availableEventsResource.getTotalPages());
        assertEquals(100L, availableEventsResource.getTotalElements());
        assertEquals(1L, availableEventsResource.getPage());
        assertNull(availableEventsResource.getEvents());
    }
    @Test
    void testToResourceWithNullEvent() {
        List<Event> eventList = new ArrayList<>();
        eventList.add(null);
        PaginatedEvent paginatedEvent = new PaginatedEvent(eventList,100L,5,1);
        AvailableEventsResource availableEventsResource = eventMapper.toResource(paginatedEvent);
        assertNotNull(availableEventsResource);
        assertEquals(5, availableEventsResource.getTotalPages());
        assertEquals(100L, availableEventsResource.getTotalElements());
        assertEquals(1L, availableEventsResource.getPage());
        assertNotNull(availableEventsResource.getEvents());
        assertEquals(1, availableEventsResource.getEvents().size());
        assertNull(availableEventsResource.getEvents().get(0));
    }
}

