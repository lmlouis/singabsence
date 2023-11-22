package com.sing.singabsence.service.dto;

import com.sing.singabsence.domain.enumeration.LeaveStatus;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.sing.singabsence.domain.Workflow} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class WorkflowDTO implements Serializable {

    private Long id;

    @NotNull
    private LeaveStatus status;

    @NotNull
    private String description;

    private MessageDTO validation;

    private LeaveDTO request;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LeaveStatus getStatus() {
        return status;
    }

    public void setStatus(LeaveStatus status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MessageDTO getValidation() {
        return validation;
    }

    public void setValidation(MessageDTO validation) {
        this.validation = validation;
    }

    public LeaveDTO getRequest() {
        return request;
    }

    public void setRequest(LeaveDTO request) {
        this.request = request;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WorkflowDTO)) {
            return false;
        }

        WorkflowDTO workflowDTO = (WorkflowDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, workflowDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WorkflowDTO{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", description='" + getDescription() + "'" +
            ", validation=" + getValidation() +
            ", request=" + getRequest() +
            "}";
    }
}
