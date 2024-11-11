package com.ticketmaster.events.domain.predicate;

import com.ticketmaster.events.domain.enumeration.Status;
import com.ticketmaster.events.domain.model.Seat;

import java.util.function.Predicate;

public class SeatPredicates {

    public static Predicate<Seat> isOpen = seat -> Status.OPEN.equals(seat.getStatus());

}
