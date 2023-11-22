package com.sing.singabsence.service.mapper;

import com.sing.singabsence.domain.Employee;
import com.sing.singabsence.domain.Event;
import com.sing.singabsence.service.dto.EmployeeDTO;
import com.sing.singabsence.service.dto.EventDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Event} and its DTO {@link EventDTO}.
 */
@Mapper(componentModel = "spring")
public interface EventMapper extends EntityMapper<EventDTO, Event> {
    @Mapping(target = "creator", source = "creator", qualifiedByName = "employeeId")
    EventDTO toDto(Event s);

    @Named("employeeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmployeeDTO toDtoEmployeeId(Employee employee);
}
