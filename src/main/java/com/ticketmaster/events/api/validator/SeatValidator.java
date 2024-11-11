package com.ticketmaster.events.api.validator;

import org.springframework.stereotype.Component;

@Component
public class SeatValidator {

    public void validateSeatRequest(Long eventId, Long numberOfSeats) {
        validateEventId(eventId);
        validateNumberOfSeats(numberOfSeats);
    }
    private void validateEventId(Long eventId) {
        if (eventId < 0) {
            throw new IllegalArgumentException("Le numéro de l'événement est invalide.");
        }
    }
    private void validateNumberOfSeats(Long numberOfSeats) {
        if (numberOfSeats < 0) {
            throw new IllegalArgumentException("Le nombre de siége est invalide.");
        }
    }
}
