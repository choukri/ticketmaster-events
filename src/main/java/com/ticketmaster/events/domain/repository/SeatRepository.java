package com.ticketmaster.events.domain.repository;

import com.ticketmaster.events.domain.model.Seat;

import java.util.List;

public interface SeatRepository {
    List<Seat> findSeatsByEventId(long eventId);

}
