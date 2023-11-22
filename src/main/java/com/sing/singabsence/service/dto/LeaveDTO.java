package com.sing.singabsence.service.dto;

import com.sing.singabsence.domain.enumeration.LeaveType;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.sing.singabsence.domain.Leave} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LeaveDTO implements Serializable {

    private Long id;

    @NotNull
    private LeaveType type;

    @NotNull
    private String reason;

    private WorkflowDTO workflow;

    private AttachmentDTO attachment;

    private EmployeeDTO sentto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LeaveType getType() {
        return type;
    }

    public void setType(LeaveType type) {
        this.type = type;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public WorkflowDTO getWorkflow() {
        return workflow;
    }

    public void setWorkflow(WorkflowDTO workflow) {
        this.workflow = workflow;
    }

    public AttachmentDTO getAttachment() {
        return attachment;
    }

    public void setAttachment(AttachmentDTO attachment) {
        this.attachment = attachment;
    }

    public EmployeeDTO getSentto() {
        return sentto;
    }

    public void setSentto(EmployeeDTO sentto) {
        this.sentto = sentto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LeaveDTO)) {
            return false;
        }

        LeaveDTO leaveDTO = (LeaveDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, leaveDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeaveDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", reason='" + getReason() + "'" +
            ", workflow=" + getWorkflow() +
            ", attachment=" + getAttachment() +
            ", sentto=" + getSentto() +
            "}";
    }
}
