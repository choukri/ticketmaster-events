package com.ticketmaster.events.domain.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Status {
    OPEN("OPEN"),   // available seat
    SOLD("SOLD"),   // Seat that has been sold
    HOLD("HOLD");   // A reserved seat

    private final String status;
    public static Status fromString(String status) {
        for (Status s : Status.values()) {
            if (s.getStatus().equalsIgnoreCase(status)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + status);
    }
}

