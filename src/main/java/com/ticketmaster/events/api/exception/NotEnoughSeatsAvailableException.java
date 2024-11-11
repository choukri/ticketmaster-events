package com.ticketmaster.events.api.exception;

import lombok.Getter;

@Getter
public class NotEnoughSeatsAvailableException extends RuntimeException {

    public NotEnoughSeatsAvailableException() {
        super("Pas assez de sièges disponibles");
    }
}
