package com.ticketmaster.events.domain.enumeration;

public enum SortBy {
    ALPHABETICAL,  // Sorting the list in alphabetical order
    DATE,           // Sorting by event date

    DEFAULT;        // Sorting the list in alphabetical order then by event date

    public static SortBy fromString(String sortBy) {
        try {
            return SortBy.valueOf(sortBy.toUpperCase());
        } catch (IllegalArgumentException e) {
            return SortBy.DEFAULT;
        }
    }
}
