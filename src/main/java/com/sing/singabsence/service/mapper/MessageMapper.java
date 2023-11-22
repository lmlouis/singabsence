package com.sing.singabsence.service.mapper;

import com.sing.singabsence.domain.Attachment;
import com.sing.singabsence.domain.Employee;
import com.sing.singabsence.domain.Message;
import com.sing.singabsence.service.dto.AttachmentDTO;
import com.sing.singabsence.service.dto.EmployeeDTO;
import com.sing.singabsence.service.dto.MessageDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Message} and its DTO {@link MessageDTO}.
 */
@Mapper(componentModel = "spring")
public interface MessageMapper extends EntityMapper<MessageDTO, Message> {
    @Mapping(target = "from", source = "from", qualifiedByName = "employeeId")
    @Mapping(target = "attachements", source = "attachements", qualifiedByName = "attachmentIdSet")
    @Mapping(target = "tos", source = "tos", qualifiedByName = "employeeIdSet")
    MessageDTO toDto(Message s);

    @Mapping(target = "removeAttachements", ignore = true)
    @Mapping(target = "removeTo", ignore = true)
    Message toEntity(MessageDTO messageDTO);

    @Named("employeeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmployeeDTO toDtoEmployeeId(Employee employee);

    @Named("employeeIdSet")
    default Set<EmployeeDTO> toDtoEmployeeIdSet(Set<Employee> employee) {
        return employee.stream().map(this::toDtoEmployeeId).collect(Collectors.toSet());
    }

    @Named("attachmentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AttachmentDTO toDtoAttachmentId(Attachment attachment);

    @Named("attachmentIdSet")
    default Set<AttachmentDTO> toDtoAttachmentIdSet(Set<Attachment> attachment) {
        return attachment.stream().map(this::toDtoAttachmentId).collect(Collectors.toSet());
    }
}
