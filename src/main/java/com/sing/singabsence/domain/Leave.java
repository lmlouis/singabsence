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
            "request",
            "service",
            "inboxes",
            "teams",
            "requests",
        },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Employee owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "validation", "request", "ownrequests" }, allowSetters = true)
    private Workflow workflow;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "from", "attachements", "tos", "oneleaves", "ownworkflows" }, allowSetters = true)
    private Message reason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "creator", "oneleaves", "owncalendars" }, allowSetters = true)
    private Event period;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_leave__sentto",
        joinColumns = @JoinColumn(name = "leave_id"),
        inverseJoinColumns = @JoinColumn(name = "sentto_id")
    )
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
            "request",
            "service",
            "inboxes",
            "teams",
            "requests",
        },
        allowSetters = true
    )
    private Set<Employee> senttos = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "request")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "validation", "request", "ownrequests" }, allowSetters = true)
    private Set<Workflow> ownworkflows = new HashSet<>();

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
            "request",
            "service",
            "inboxes",
            "teams",
            "requests",
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

    public Employee getOwner() {
        return this.owner;
    }

    public void setOwner(Employee employee) {
        this.owner = employee;
    }

    public Leave owner(Employee employee) {
        this.setOwner(employee);
        return this;
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

    public Message getReason() {
        return this.reason;
    }

    public void setReason(Message message) {
        this.reason = message;
    }

    public Leave reason(Message message) {
        this.setReason(message);
        return this;
    }

    public Event getPeriod() {
        return this.period;
    }

    public void setPeriod(Event event) {
        this.period = event;
    }

    public Leave period(Event event) {
        this.setPeriod(event);
        return this;
    }

    public Set<Employee> getSenttos() {
        return this.senttos;
    }

    public void setSenttos(Set<Employee> employees) {
        this.senttos = employees;
    }

    public Leave senttos(Set<Employee> employees) {
        this.setSenttos(employees);
        return this;
    }

    public Leave addSentto(Employee employee) {
        this.senttos.add(employee);
        return this;
    }

    public Leave removeSentto(Employee employee) {
        this.senttos.remove(employee);
        return this;
    }

    public Set<Workflow> getOwnworkflows() {
        return this.ownworkflows;
    }

    public void setOwnworkflows(Set<Workflow> workflows) {
        if (this.ownworkflows != null) {
            this.ownworkflows.forEach(i -> i.setRequest(null));
        }
        if (workflows != null) {
            workflows.forEach(i -> i.setRequest(this));
        }
        this.ownworkflows = workflows;
    }

    public Leave ownworkflows(Set<Workflow> workflows) {
        this.setOwnworkflows(workflows);
        return this;
    }

    public Leave addOwnworkflow(Workflow workflow) {
        this.ownworkflows.add(workflow);
        workflow.setRequest(this);
        return this;
    }

    public Leave removeOwnworkflow(Workflow workflow) {
        this.ownworkflows.remove(workflow);
        workflow.setRequest(null);
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
            "}";
    }
}
