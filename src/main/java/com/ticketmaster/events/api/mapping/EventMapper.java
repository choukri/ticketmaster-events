package com.ticketmaster.events.api.mapping;

import com.ticketmaster.events.domain.model.PaginatedEvent;
import com.ticketmaster.events.model.AvailableEventsResource;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {

    AvailableEventsResource toResource(PaginatedEvent event);

}
