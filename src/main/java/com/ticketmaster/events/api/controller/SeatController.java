package com.ticketmaster.events.api.controller;

import com.ticketmaster.events.api.SeatsApi;
import com.ticketmaster.events.api.mapping.SeatMapper;
import com.ticketmaster.events.api.validator.SeatValidator;
import com.ticketmaster.events.application.service.SeatService;
import com.ticketmaster.events.model.BestSeatsResource;
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
public class SeatController implements SeatsApi {

    private final SeatService seatService;

    private final SeatMapper seatMapper;

    private final SeatValidator seatValidator;

    @Override
    public ResponseEntity<BestSeatsResource> selectBestSeatsAvailableByEvent(Long eventId, Long numberOfSeats) {
        log.info("show best seats by event");
        seatValidator.validateSeatRequest(eventId,numberOfSeats);
        return ResponseEntity.ok(seatMapper.toResource(eventId, seatService.getBestSeats(eventId, numberOfSeats)));
    }
}
