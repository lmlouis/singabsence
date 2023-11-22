package com.sing.singabsence.service.mapper;

import com.sing.singabsence.domain.Attachment;
import com.sing.singabsence.domain.Employee;
import com.sing.singabsence.service.dto.AttachmentDTO;
import com.sing.singabsence.service.dto.EmployeeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Attachment} and its DTO {@link AttachmentDTO}.
 */
@Mapper(componentModel = "spring")
public interface AttachmentMapper extends EntityMapper<AttachmentDTO, Attachment> {
    @Mapping(target = "owner", source = "owner", qualifiedByName = "employeeId")
    AttachmentDTO toDto(Attachment s);

    @Named("employeeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmployeeDTO toDtoEmployeeId(Employee employee);
}
