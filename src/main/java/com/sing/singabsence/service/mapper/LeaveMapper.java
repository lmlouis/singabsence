package com.sing.singabsence.service.mapper;

import com.sing.singabsence.domain.Attachment;
import com.sing.singabsence.domain.Employee;
import com.sing.singabsence.domain.Leave;
import com.sing.singabsence.domain.Workflow;
import com.sing.singabsence.service.dto.AttachmentDTO;
import com.sing.singabsence.service.dto.EmployeeDTO;
import com.sing.singabsence.service.dto.LeaveDTO;
import com.sing.singabsence.service.dto.WorkflowDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Leave} and its DTO {@link LeaveDTO}.
 */
@Mapper(componentModel = "spring")
public interface LeaveMapper extends EntityMapper<LeaveDTO, Leave> {
    @Mapping(target = "workflow", source = "workflow", qualifiedByName = "workflowId")
    @Mapping(target = "attachment", source = "attachment", qualifiedByName = "attachmentId")
    @Mapping(target = "sentto", source = "sentto", qualifiedByName = "employeeId")
    LeaveDTO toDto(Leave s);

    @Named("workflowId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    WorkflowDTO toDtoWorkflowId(Workflow workflow);

    @Named("attachmentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AttachmentDTO toDtoAttachmentId(Attachment attachment);

    @Named("employeeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmployeeDTO toDtoEmployeeId(Employee employee);
}
