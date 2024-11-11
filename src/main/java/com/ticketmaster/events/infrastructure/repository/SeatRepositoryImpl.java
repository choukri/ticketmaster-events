package com.ticketmaster.events.infrastructure.repository;

import com.ticketmaster.events.api.exception.EventNotFoundException;
import com.ticketmaster.events.domain.model.Event;
import com.ticketmaster.events.domain.model.Seat;
import com.ticketmaster.events.domain.repository.SeatRepository;
import com.ticketmaster.events.infrastructure.datasource.CSVDataLoader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SeatRepositoryImpl implements SeatRepository {

    private final CSVDataLoader csvDataLoader;

    @Override
    public List<Seat> findSeatsByEventId(long eventId) {
        Event event = csvDataLoader.getEventMap().get(eventId);
        if (event == null) {
            throw new EventNotFoundException("Événement non trouvé avec l'ID: " + eventId);
        }

        return event.getSeats();
    }
}
