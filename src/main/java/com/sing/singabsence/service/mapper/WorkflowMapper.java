package com.sing.singabsence.service.mapper;

import com.sing.singabsence.domain.Leave;
import com.sing.singabsence.domain.Message;
import com.sing.singabsence.domain.Workflow;
import com.sing.singabsence.service.dto.LeaveDTO;
import com.sing.singabsence.service.dto.MessageDTO;
import com.sing.singabsence.service.dto.WorkflowDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Workflow} and its DTO {@link WorkflowDTO}.
 */
@Mapper(componentModel = "spring")
public interface WorkflowMapper extends EntityMapper<WorkflowDTO, Workflow> {
    @Mapping(target = "validation", source = "validation", qualifiedByName = "messageId")
    @Mapping(target = "request", source = "request", qualifiedByName = "leaveId")
    WorkflowDTO toDto(Workflow s);

    @Named("messageId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MessageDTO toDtoMessageId(Message message);

    @Named("leaveId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LeaveDTO toDtoLeaveId(Leave leave);
}
