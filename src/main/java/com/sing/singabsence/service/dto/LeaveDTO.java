package com.sing.singabsence.service.dto;

import com.sing.singabsence.domain.enumeration.LeaveType;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.sing.singabsence.domain.Leave} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LeaveDTO implements Serializable {

    private Long id;

    @NotNull
    private LeaveType type;

    private EmployeeDTO owner;

    private WorkflowDTO workflow;

    private MessageDTO reason;

    private EventDTO period;

    private Set<EmployeeDTO> senttos = new HashSet<>();

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

    public EmployeeDTO getOwner() {
        return owner;
    }

    public void setOwner(EmployeeDTO owner) {
        this.owner = owner;
    }

    public WorkflowDTO getWorkflow() {
        return workflow;
    }

    public void setWorkflow(WorkflowDTO workflow) {
        this.workflow = workflow;
    }

    public MessageDTO getReason() {
        return reason;
    }

    public void setReason(MessageDTO reason) {
        this.reason = reason;
    }

    public EventDTO getPeriod() {
        return period;
    }

    public void setPeriod(EventDTO period) {
        this.period = period;
    }

    public Set<EmployeeDTO> getSenttos() {
        return senttos;
    }

    public void setSenttos(Set<EmployeeDTO> senttos) {
        this.senttos = senttos;
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
            ", owner=" + getOwner() +
            ", workflow=" + getWorkflow() +
            ", reason=" + getReason() +
            ", period=" + getPeriod() +
            ", senttos=" + getSenttos() +
            "}";
    }
}
