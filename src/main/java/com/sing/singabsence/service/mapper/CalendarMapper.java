package com.sing.singabsence.service.mapper;

import com.sing.singabsence.domain.Calendar;
import com.sing.singabsence.domain.Event;
import com.sing.singabsence.service.dto.CalendarDTO;
import com.sing.singabsence.service.dto.EventDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Calendar} and its DTO {@link CalendarDTO}.
 */
@Mapper(componentModel = "spring")
public interface CalendarMapper extends EntityMapper<CalendarDTO, Calendar> {
    @Mapping(target = "events", source = "events", qualifiedByName = "eventIdSet")
    CalendarDTO toDto(Calendar s);

    @Mapping(target = "removeEvents", ignore = true)
    Calendar toEntity(CalendarDTO calendarDTO);

    @Named("eventId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EventDTO toDtoEventId(Event event);

    @Named("eventIdSet")
    default Set<EventDTO> toDtoEventIdSet(Set<Event> event) {
        return event.stream().map(this::toDtoEventId).collect(Collectors.toSet());
    }
}
