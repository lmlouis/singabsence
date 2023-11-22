package com.sing.singabsence.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sing.singabsence.domain.enumeration.LeaveStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Workflow.
 */
@Entity
@Table(name = "workflow")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Workflow implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private LeaveStatus status;

    @Column(name = "validation")
    private String validation;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "state")
    private Integer state;

    @Column(name = "label")
    private String label;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "workflow")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "workflow", "attachment", "sentto", "requestedbies" }, allowSetters = true)
    private Set<Leave> ownrequests = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Workflow id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LeaveStatus getStatus() {
        return this.status;
    }

    public Workflow status(LeaveStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(LeaveStatus status) {
        this.status = status;
    }

    public String getValidation() {
        return this.validation;
    }

    public Workflow validation(String validation) {
        this.setValidation(validation);
        return this;
    }

    public void setValidation(String validation) {
        this.validation = validation;
    }

    public String getDescription() {
        return this.description;
    }

    public Workflow description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getState() {
        return this.state;
    }

    public Workflow state(Integer state) {
        this.setState(state);
        return this;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getLabel() {
        return this.label;
    }

    public Workflow label(String label) {
        this.setLabel(label);
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Set<Leave> getOwnrequests() {
        return this.ownrequests;
    }

    public void setOwnrequests(Set<Leave> leaves) {
        if (this.ownrequests != null) {
            this.ownrequests.forEach(i -> i.setWorkflow(null));
        }
        if (leaves != null) {
            leaves.forEach(i -> i.setWorkflow(this));
        }
        this.ownrequests = leaves;
    }

    public Workflow ownrequests(Set<Leave> leaves) {
        this.setOwnrequests(leaves);
        return this;
    }

    public Workflow addOwnrequests(Leave leave) {
        this.ownrequests.add(leave);
        leave.setWorkflow(this);
        return this;
    }

    public Workflow removeOwnrequests(Leave leave) {
        this.ownrequests.remove(leave);
        leave.setWorkflow(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Workflow)) {
            return false;
        }
        return getId() != null && getId().equals(((Workflow) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Workflow{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", validation='" + getValidation() + "'" +
            ", description='" + getDescription() + "'" +
            ", state=" + getState() +
            ", label='" + getLabel() + "'" +
            "}";
    }
}
