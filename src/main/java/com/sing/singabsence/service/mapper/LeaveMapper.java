package com.sing.singabsence.service.mapper;

import com.sing.singabsence.domain.Employee;
import com.sing.singabsence.domain.Event;
import com.sing.singabsence.domain.Leave;
import com.sing.singabsence.domain.Message;
import com.sing.singabsence.domain.Workflow;
import com.sing.singabsence.service.dto.EmployeeDTO;
import com.sing.singabsence.service.dto.EventDTO;
import com.sing.singabsence.service.dto.LeaveDTO;
import com.sing.singabsence.service.dto.MessageDTO;
import com.sing.singabsence.service.dto.WorkflowDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Leave} and its DTO {@link LeaveDTO}.
 */
@Mapper(componentModel = "spring")
public interface LeaveMapper extends EntityMapper<LeaveDTO, Leave> {
    @Mapping(target = "owner", source = "owner", qualifiedByName = "employeeId")
    @Mapping(target = "workflow", source = "workflow", qualifiedByName = "workflowId")
    @Mapping(target = "reason", source = "reason", qualifiedByName = "messageId")
    @Mapping(target = "period", source = "period", qualifiedByName = "eventId")
    @Mapping(target = "senttos", source = "senttos", qualifiedByName = "employeeIdSet")
    LeaveDTO toDto(Leave s);

    @Mapping(target = "removeSentto", ignore = true)
    Leave toEntity(LeaveDTO leaveDTO);

    @Named("employeeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmployeeDTO toDtoEmployeeId(Employee employee);

    @Named("employeeIdSet")
    default Set<EmployeeDTO> toDtoEmployeeIdSet(Set<Employee> employee) {
        return employee.stream().map(this::toDtoEmployeeId).collect(Collectors.toSet());
    }

    @Named("workflowId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    WorkflowDTO toDtoWorkflowId(Workflow workflow);

    @Named("messageId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MessageDTO toDtoMessageId(Message message);

    @Named("eventId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EventDTO toDtoEventId(Event event);
}
