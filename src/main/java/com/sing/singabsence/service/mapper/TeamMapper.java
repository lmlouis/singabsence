package com.sing.singabsence.service.mapper;

import com.sing.singabsence.domain.Calendar;
import com.sing.singabsence.domain.Employee;
import com.sing.singabsence.domain.Team;
import com.sing.singabsence.service.dto.CalendarDTO;
import com.sing.singabsence.service.dto.EmployeeDTO;
import com.sing.singabsence.service.dto.TeamDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Team} and its DTO {@link TeamDTO}.
 */
@Mapper(componentModel = "spring")
public interface TeamMapper extends EntityMapper<TeamDTO, Team> {
    @Mapping(target = "lead", source = "lead", qualifiedByName = "employeeId")
    @Mapping(target = "planing", source = "planing", qualifiedByName = "calendarId")
    @Mapping(target = "members", source = "members", qualifiedByName = "employeeIdSet")
    TeamDTO toDto(Team s);

    @Mapping(target = "removeMembers", ignore = true)
    Team toEntity(TeamDTO teamDTO);

    @Named("employeeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmployeeDTO toDtoEmployeeId(Employee employee);

    @Named("employeeIdSet")
    default Set<EmployeeDTO> toDtoEmployeeIdSet(Set<Employee> employee) {
        return employee.stream().map(this::toDtoEmployeeId).collect(Collectors.toSet());
    }

    @Named("calendarId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CalendarDTO toDtoCalendarId(Calendar calendar);
}
