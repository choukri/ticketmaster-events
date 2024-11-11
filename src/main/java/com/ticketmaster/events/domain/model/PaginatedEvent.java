package com.ticketmaster.events.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PaginatedEvent {
    private List<Event> events;
    private long totalElements;
    private int totalPages;
    private long page;
}
