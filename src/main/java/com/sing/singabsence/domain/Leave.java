package com.sing.singabsence.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sing.singabsence.domain.enumeration.LeaveType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Leave.
 */
@Entity
@Table(name = "leave")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Leave implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private LeaveType type;

    @NotNull
    @Column(name = "reason", nullable = false)
    private String reason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "ownrequests" }, allowSetters = true)
    private Workflow workflow;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "owner", "leaverequests", "msgs" }, allowSetters = true)
    private Attachment attachment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "authenticateby",
            "calendar",
            "attachments",
            "createdevents",
            "leaves",
            "ownenterprise",
            "ownteam",
            "ownmessage",
            "service",
            "requests",
            "inboxes",
            "teams",
        },
        allowSetters = true
    )
    private Employee sentto;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "leaves")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "authenticateby",
            "calendar",
            "attachments",
            "createdevents",
            "leaves",
            "ownenterprise",
            "ownteam",
            "ownmessage",
            "service",
            "requests",
            "inboxes",
            "teams",
        },
        allowSetters = true
    )
    private Set<Employee> requestedbies = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Leave id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LeaveType getType() {
        return this.type;
    }

    public Leave type(LeaveType type) {
        this.setType(type);
        return this;
    }

    public void setType(LeaveType type) {
        this.type = type;
    }

    public String getReason() {
        return this.reason;
    }

    public Leave reason(String reason) {
        this.setReason(reason);
        return this;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Workflow getWorkflow() {
        return this.workflow;
    }

    public void setWorkflow(Workflow workflow) {
        this.workflow = workflow;
    }

    public Leave workflow(Workflow workflow) {
        this.setWorkflow(workflow);
        return this;
    }

    public Attachment getAttachment() {
        return this.attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

    public Leave attachment(Attachment attachment) {
        this.setAttachment(attachment);
        return this;
    }

    public Employee getSentto() {
        return this.sentto;
    }

    public void setSentto(Employee employee) {
        this.sentto = employee;
    }

    public Leave sentto(Employee employee) {
        this.setSentto(employee);
        return this;
    }

    public Set<Employee> getRequestedbies() {
        return this.requestedbies;
    }

    public void setRequestedbies(Set<Employee> employees) {
        if (this.requestedbies != null) {
            this.requestedbies.forEach(i -> i.removeLeaves(this));
        }
        if (employees != null) {
            employees.forEach(i -> i.addLeaves(this));
        }
        this.requestedbies = employees;
    }

    public Leave requestedbies(Set<Employee> employees) {
        this.setRequestedbies(employees);
        return this;
    }

    public Leave addRequestedby(Employee employee) {
        this.requestedbies.add(employee);
        employee.getLeaves().add(this);
        return this;
    }

    public Leave removeRequestedby(Employee employee) {
        this.requestedbies.remove(employee);
        employee.getLeaves().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Leave)) {
            return false;
        }
        return getId() != null && getId().equals(((Leave) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Leave{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", reason='" + getReason() + "'" +
            "}";
    }
}
