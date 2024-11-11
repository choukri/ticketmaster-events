package com.ticketmaster.events.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
public class Event {
    private long eventId;
    private String eventName;
    private LocalDateTime eventDate;
    private List<Seat> seats;
}
