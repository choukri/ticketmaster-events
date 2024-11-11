package com.ticketmaster.events.api.controller;

import com.ticketmaster.events.api.validator.SeatValidator;
import com.ticketmaster.events.application.service.SeatService;
import com.ticketmaster.events.api.mapping.SeatMapper;
import com.ticketmaster.events.model.BestSeatsResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SeatControllerTest {

    @Mock
    private SeatService seatService;

    @Mock
    private SeatMapper seatMapper;

    @InjectMocks
    private SeatController seatController;
    @Mock
    private SeatValidator seatValidator;


    @Test
    void selectBestSeatsAvailableByEvent_ShouldReturnBestSeats() {
        Long eventId = 123L;
        Long numberOfSeats = 5L;
        BestSeatsResource bestSeatsResource = new BestSeatsResource();
        when(seatService.getBestSeats(eventId, numberOfSeats)).thenReturn(new ArrayList<>());
        when(seatMapper.toResource(eventId, new ArrayList<>())).thenReturn(bestSeatsResource);
        ResponseEntity<BestSeatsResource> responseEntity = seatController.selectBestSeatsAvailableByEvent(eventId, numberOfSeats);
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCode().value());
        assertNotNull(responseEntity.getBody());
        assertTrue(responseEntity.getBody().getSelectedSeats().isEmpty());
        verify(seatService, times(1)).getBestSeats(eventId, numberOfSeats);
        verify(seatMapper, times(1)).toResource(eventId, new ArrayList<>());
    }
}
