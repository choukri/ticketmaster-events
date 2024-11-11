package com.ticketmaster.events.api.controller;

import com.ticketmaster.events.api.EventsApi;
import com.ticketmaster.events.api.mapping.EventMapper;
import com.ticketmaster.events.api.validator.EventValidator;
import com.ticketmaster.events.application.service.EventService;
import com.ticketmaster.events.model.AvailableEventsResource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.ticketmaster.events.util.Constant.API_BASE_PATH;


@Slf4j
@RestController
@RequestMapping(API_BASE_PATH)
@RequiredArgsConstructor
public class EventController implements EventsApi {

    private final EventService eventService;

    private final EventMapper eventMapper;

    private final EventValidator eventValidator;

    public ResponseEntity<AvailableEventsResource> getAvailableEvents(String sortBy, Long page, Long size) {
        log.info("show available events");
        eventValidator.validateEventRequest(sortBy, page, size);
        return ResponseEntity.ok(eventMapper.toResource(eventService.getPaginatedAvailableEventsSortedBy(sortBy, page, size)));
    }
}
