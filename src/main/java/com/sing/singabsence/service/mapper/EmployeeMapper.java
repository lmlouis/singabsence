package com.sing.singabsence.service.mapper;

import com.sing.singabsence.domain.Calendar;
import com.sing.singabsence.domain.Employee;
import com.sing.singabsence.domain.Leave;
import com.sing.singabsence.domain.Team;
import com.sing.singabsence.domain.User;
import com.sing.singabsence.service.dto.CalendarDTO;
import com.sing.singabsence.service.dto.EmployeeDTO;
import com.sing.singabsence.service.dto.LeaveDTO;
import com.sing.singabsence.service.dto.TeamDTO;
import com.sing.singabsence.service.dto.UserDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Employee} and its DTO {@link EmployeeDTO}.
 */
@Mapper(componentModel = "spring")
public interface EmployeeMapper extends EntityMapper<EmployeeDTO, Employee> {
    @Mapping(target = "authenticateby", source = "authenticateby", qualifiedByName = "userLogin")
    @Mapping(target = "calendar", source = "calendar", qualifiedByName = "calendarId")
    @Mapping(target = "leaves", source = "leaves", qualifiedByName = "leaveIdSet")
    @Mapping(target = "service", source = "service", qualifiedByName = "teamId")
    EmployeeDTO toDto(Employee s);

    @Mapping(target = "removeLeaves", ignore = true)
    Employee toEntity(EmployeeDTO employeeDTO);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);

    @Named("calendarId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CalendarDTO toDtoCalendarId(Calendar calendar);

    @Named("leaveId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LeaveDTO toDtoLeaveId(Leave leave);

    @Named("leaveIdSet")
    default Set<LeaveDTO> toDtoLeaveIdSet(Set<Leave> leave) {
        return leave.stream().map(this::toDtoLeaveId).collect(Collectors.toSet());
    }

    @Named("teamId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TeamDTO toDtoTeamId(Team team);
}
