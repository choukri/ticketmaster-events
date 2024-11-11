package com.ticketmaster.events.api.validator;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class EventValidator {
    public void validateEventRequest(String sortBy, Long page , Long size) {
        validateSortBy(sortBy);
        validatePage(page);
        validateSize(size);
    }

    private void validatePage(Long page) {
        if (page < 0) {
            throw new IllegalArgumentException("Le numéro de page est invalide");
        }
    }

    private void validateSize(Long size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Le nombre d'éléments par page est invalide");
        }
    }

    private void validateSortBy(String sortBy) {
        Set<String> validSortByValues = new HashSet<>();
        validSortByValues.add("alphabetical");
        validSortByValues.add("date");
        validSortByValues.add("alphabetical,date");

        if (!validSortByValues.contains(sortBy) ) {
            throw new IllegalArgumentException("Valeur de sortBy invalide. Les valeurs possibles sont 'alphabetical', 'date' ou 'alphabetical,date'.");
        }
    }


}
