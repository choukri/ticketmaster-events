package com.ticketmaster.events.domain.model;

import com.ticketmaster.events.domain.enumeration.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Seat {
    private Long seatNumber;
    private Long row;
    private String level;
    private String section;
    private Status status;
    private Long sellRank;
    private boolean hasUpsells;
}
