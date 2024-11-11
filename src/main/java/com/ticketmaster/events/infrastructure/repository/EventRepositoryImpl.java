package com.ticketmaster.events.infrastructure.repository;

import com.ticketmaster.events.domain.model.Event;
import com.ticketmaster.events.domain.repository.EventRepository;
import com.ticketmaster.events.infrastructure.datasource.CSVDataLoader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
@Slf4j
public class EventRepositoryImpl implements EventRepository {

    private final CSVDataLoader csvDataLoader;

    @Override
    public List<Event> findAllEvents() {
        Map<Long, Event> eventMap = csvDataLoader.getEventMap();
        if (eventMap == null) {
            return List.of();
        }
        return List.copyOf(eventMap.values());
    }

}
