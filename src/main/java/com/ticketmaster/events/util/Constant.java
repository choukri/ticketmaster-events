package com.ticketmaster.events.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constant {

  public static final String API_BASE_PATH = "/api/ticketmaster/events/v1/";
  public static final String ENDPOINT_SEARCH_AVIALABLE_EVENTS = "events/available-events";
  public static final String ENDPOINT_SELECTION_BEST_SEATS = "/seats/{eventId}/best-selection";
  public static final String SCOPE_OAUTH_READ_EVENTS = "ticketmaster.event.read";
  public static final String SCOPE_OAUTH_READ_SEATS = "ticketmaster.seat.read";

}
