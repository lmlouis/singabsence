package com.sing.singabsence.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Message.
 */
@Entity
@Table(name = "message")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "purpose", nullable = false)
    private String purpose;

    @NotNull
    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "sentat")
    private Instant sentat;

    @Column(name = "isread")
    private Boolean isread;

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
    private Employee from;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_message__attachements",
        joinColumns = @JoinColumn(name = "message_id"),
        inverseJoinColumns = @JoinColumn(name = "attachements_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "owner", "msgs" }, allowSetters = true)
    private Set<Attachment> attachements = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "rel_message__to", joinColumns = @JoinColumn(name = "message_id"), inverseJoinColumns = @JoinColumn(name = "to_id"))
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
    private Set<Employee> tos = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "reason")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "owner", "workflow", "reason", "period", "senttos", "ownworkflows", "requestedbies" },
        allowSetters = true
    )
    private Set<Leave> oneleaves = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "validation")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "validation", "request", "ownrequests" }, allowSetters = true)
    private Set<Workflow> ownworkflows = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Message id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPurpose() {
        return this.purpose;
    }

    public Message purpose(String purpose) {
        this.setPurpose(purpose);
        return this;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getContent() {
        return this.content;
    }

    public Message content(String content) {
        this.setContent(content);
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getSentat() {
        return this.sentat;
    }

    public Message sentat(Instant sentat) {
        this.setSentat(sentat);
        return this;
    }

    public void setSentat(Instant sentat) {
        this.sentat = sentat;
    }

    public Boolean getIsread() {
        return this.isread;
    }

    public Message isread(Boolean isread) {
        this.setIsread(isread);
        return this;
    }

    public void setIsread(Boolean isread) {
        this.isread = isread;
    }

    public Employee getFrom() {
        return this.from;
    }

    public void setFrom(Employee employee) {
        this.from = employee;
    }

    public Message from(Employee employee) {
        this.setFrom(employee);
        return this;
    }

    public Set<Attachment> getAttachements() {
        return this.attachements;
    }

    public void setAttachements(Set<Attachment> attachments) {
        this.attachements = attachments;
    }

    public Message attachements(Set<Attachment> attachments) {
        this.setAttachements(attachments);
        return this;
    }

    public Message addAttachements(Attachment attachment) {
        this.attachements.add(attachment);
        return this;
    }

    public Message removeAttachements(Attachment attachment) {
        this.attachements.remove(attachment);
        return this;
    }

    public Set<Employee> getTos() {
        return this.tos;
    }

    public void setTos(Set<Employee> employees) {
        this.tos = employees;
    }

    public Message tos(Set<Employee> employees) {
        this.setTos(employees);
        return this;
    }

    public Message addTo(Employee employee) {
        this.tos.add(employee);
        return this;
    }

    public Message removeTo(Employee employee) {
        this.tos.remove(employee);
        return this;
    }

    public Set<Leave> getOneleaves() {
        return this.oneleaves;
    }

    public void setOneleaves(Set<Leave> leaves) {
        if (this.oneleaves != null) {
            this.oneleaves.forEach(i -> i.setReason(null));
        }
        if (leaves != null) {
            leaves.forEach(i -> i.setReason(this));
        }
        this.oneleaves = leaves;
    }

    public Message oneleaves(Set<Leave> leaves) {
        this.setOneleaves(leaves);
        return this;
    }

    public Message addOneleave(Leave leave) {
        this.oneleaves.add(leave);
        leave.setReason(this);
        return this;
    }

    public Message removeOneleave(Leave leave) {
        this.oneleaves.remove(leave);
        leave.setReason(null);
        return this;
    }

    public Set<Workflow> getOwnworkflows() {
        return this.ownworkflows;
    }

    public void setOwnworkflows(Set<Workflow> workflows) {
        if (this.ownworkflows != null) {
            this.ownworkflows.forEach(i -> i.setValidation(null));
        }
        if (workflows != null) {
            workflows.forEach(i -> i.setValidation(this));
        }
        this.ownworkflows = workflows;
    }

    public Message ownworkflows(Set<Workflow> workflows) {
        this.setOwnworkflows(workflows);
        return this;
    }

    public Message addOwnworkflow(Workflow workflow) {
        this.ownworkflows.add(workflow);
        workflow.setValidation(this);
        return this;
    }

    public Message removeOwnworkflow(Workflow workflow) {
        this.ownworkflows.remove(workflow);
        workflow.setValidation(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Message)) {
            return false;
        }
        return getId() != null && getId().equals(((Message) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Message{" +
            "id=" + getId() +
            ", purpose='" + getPurpose() + "'" +
            ", content='" + getContent() + "'" +
            ", sentat='" + getSentat() + "'" +
            ", isread='" + getIsread() + "'" +
            "}";
    }
}
