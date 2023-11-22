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

    private String validation;

    @NotNull
    private String description;

    private Integer state;

    private String label;

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

    public String getValidation() {
        return validation;
    }

    public void setValidation(String validation) {
        this.validation = validation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
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
            ", validation='" + getValidation() + "'" +
            ", description='" + getDescription() + "'" +
            ", state=" + getState() +
            ", label='" + getLabel() + "'" +
            "}";
    }
}
