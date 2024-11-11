package com.ticketmaster.events.api.mapping;

import com.ticketmaster.events.domain.model.Seat;
import com.ticketmaster.events.model.BestSeatsResource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SeatMapper {
    @Mapping(target ="eventId", source = "eventId")
    @Mapping(target ="selectedSeats", source = "seats")
    BestSeatsResource toResource(Long eventId, List<Seat> seats);

}
