package com.sing.singabsence.service.mapper;

import com.sing.singabsence.domain.Workflow;
import com.sing.singabsence.service.dto.WorkflowDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Workflow} and its DTO {@link WorkflowDTO}.
 */
@Mapper(componentModel = "spring")
public interface WorkflowMapper extends EntityMapper<WorkflowDTO, Workflow> {}
