package com.ticketmaster.events.api.controller;

import com.ticketmaster.events.api.mapping.EventMapper;
import com.ticketmaster.events.api.validator.EventValidator;
import com.ticketmaster.events.application.service.EventService;
import com.ticketmaster.events.domain.model.PaginatedEvent;
import com.ticketmaster.events.model.AvailableEventsResource;
import com.ticketmaster.events.model.EventResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.ticketmaster.events.util.Constant.API_BASE_PATH;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EventControllerTest {

    @Mock
    private EventService eventService;

    @Mock
    private EventMapper eventMapper;

    @Mock
    private EventValidator eventValidator;

    @InjectMocks
    private EventController eventController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(eventController).build();
    }

    @Test
    public void testGetAvailableEvents() throws Exception {
        EventResource eventResource = new EventResource();
        eventResource.setEventId(1L);
        eventResource.setEventName("Test Event");
        AvailableEventsResource availableEventsResource = new AvailableEventsResource();
        availableEventsResource.setEvents(List.of(eventResource));
        availableEventsResource.setTotalElements(1L);
        availableEventsResource.setTotalPages(1L);
        availableEventsResource.setPage(1L);
        when(eventService.getPaginatedAvailableEventsSortedBy("alphabetical", 1L, 10L))
                .thenReturn(new PaginatedEvent(List.of(), 1L, 1, 1L));  // Mocking the service's return
        when(eventMapper.toResource(new PaginatedEvent(List.of(), 1L, 1, 1L)))
                .thenReturn(availableEventsResource);
        mockMvc.perform(get(API_BASE_PATH+"/events/available-events")
                        .param("sortBy", "alphabetical")
                        .param("page", "1")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.page").value(1))
                .andExpect(jsonPath("$.events[0].eventId").value(1))
                .andExpect(jsonPath("$.events[0].eventName").value("Test Event"));
    }
}
