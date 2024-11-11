package com.ticketmaster.events.application.service;

import com.ticketmaster.events.domain.enumeration.SortBy;
import com.ticketmaster.events.domain.model.Event;
import com.ticketmaster.events.domain.model.PaginatedEvent;
import com.ticketmaster.events.domain.predicate.SeatPredicates;
import com.ticketmaster.events.domain.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;


@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;

    public PaginatedEvent getPaginatedAvailableEventsSortedBy(String sortBy, Long page, Long size) {

        List<Event> events = eventRepository.findAllEvents();

        List<Event> sortedAvailableEvents = filterAndSortEvents(events, sortBy);

        List<Event> paginatedEvents = paginateEvents(sortedAvailableEvents, page, size);

        long totalElements = sortedAvailableEvents.size();
        int totalPages = (int) Math.ceil((double) totalElements / size);

        return new PaginatedEvent(paginatedEvents, totalElements, totalPages, page);
    }

    private List<Event> paginateEvents(List<Event> sortedEvents, Long page, Long size) {
        long totalElements = sortedEvents.size();
        int fromIndex = Math.toIntExact(page * size);
        int toIndex = Math.toIntExact(Math.min(fromIndex + size, totalElements));
        return sortedEvents.subList(fromIndex, toIndex);
    }

    private List<Event> filterAndSortEvents(List<Event> events, String sortBy) {
        // Filter events to include only those with at least one "OPEN" seat
        List<Event> availableEvents = events.stream()
                .filter(event -> event.getSeats().stream().anyMatch(SeatPredicates.isOpen))
                .filter(event -> event.getEventDate().isAfter(LocalDateTime.now())).toList();

        // Sorting based on the sortBy parameter
        Comparator<Event> comparator = switch (SortBy.fromString(sortBy)) {
            case ALPHABETICAL -> Comparator.comparing(Event::getEventName, String.CASE_INSENSITIVE_ORDER);
            case DATE -> Comparator.comparing(Event::getEventDate);
            case DEFAULT -> Comparator.comparing(Event::getEventName, String.CASE_INSENSITIVE_ORDER)
                    .thenComparing(Event::getEventDate);
        };

        return availableEvents.stream()
                .sorted(comparator).toList();
    }

}

