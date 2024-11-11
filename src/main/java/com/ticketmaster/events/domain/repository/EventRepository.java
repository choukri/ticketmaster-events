package com.ticketmaster.events.domain.repository;

import com.ticketmaster.events.domain.model.Event;

import java.util.List;

public interface EventRepository {
    List<Event> findAllEvents();


}
