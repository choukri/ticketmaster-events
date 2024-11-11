package com.ticketmaster.events.application.service;

import com.ticketmaster.events.api.exception.NotEnoughSeatsAvailableException;
import com.ticketmaster.events.domain.model.Seat;
import com.ticketmaster.events.domain.predicate.SeatPredicates;
import com.ticketmaster.events.domain.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatService {
    private final SeatRepository seatRepository;

    public List<Seat> getBestSeats(Long eventId, Long numberOfSeats) {
        List<Seat> availableSeats = seatRepository.findSeatsByEventId(eventId);

        // Filter to include only "OPEN" seats and sort by sellRank (descending)
        List<Seat> bestSeats = availableSeats.stream()
                .filter(SeatPredicates.isOpen)
                .sorted(Comparator.comparingLong(Seat::getSellRank)
                        .reversed())
                .limit(numberOfSeats)
                .toList();

        // Check if the available seats are enough
        if (bestSeats.size() < numberOfSeats) {
            throw new NotEnoughSeatsAvailableException();
        }

        return bestSeats;
    }
}
