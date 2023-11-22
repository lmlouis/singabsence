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
 * A Event.
 */
@Entity
@Table(name = "event")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Column(name = "start", nullable = false)
    private Instant start;

    @NotNull
    @Column(name = "jhi_end", nullable = false)
    private Instant end;

    @Column(name = "link")
    private String link;

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
            "request",
            "service",
            "inboxes",
            "teams",
            "requests",
        },
        allowSetters = true
    )
    private Employee creator;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "period")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "owner", "workflow", "reason", "period", "senttos", "ownworkflows", "requestedbies" },
        allowSetters = true
    )
    private Set<Leave> oneleaves = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "events")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "events", "owner", "ownteam" }, allowSetters = true)
    private Set<Calendar> owncalendars = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Event id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Event title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Instant getStart() {
        return this.start;
    }

    public Event start(Instant start) {
        this.setStart(start);
        return this;
    }

    public void setStart(Instant start) {
        this.start = start;
    }

    public Instant getEnd() {
        return this.end;
    }

    public Event end(Instant end) {
        this.setEnd(end);
        return this;
    }

    public void setEnd(Instant end) {
        this.end = end;
    }

    public String getLink() {
        return this.link;
    }

    public Event link(String link) {
        this.setLink(link);
        return this;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Employee getCreator() {
        return this.creator;
    }

    public void setCreator(Employee employee) {
        this.creator = employee;
    }

    public Event creator(Employee employee) {
        this.setCreator(employee);
        return this;
    }

    public Set<Leave> getOneleaves() {
        return this.oneleaves;
    }

    public void setOneleaves(Set<Leave> leaves) {
        if (this.oneleaves != null) {
            this.oneleaves.forEach(i -> i.setPeriod(null));
        }
        if (leaves != null) {
            leaves.forEach(i -> i.setPeriod(this));
        }
        this.oneleaves = leaves;
    }

    public Event oneleaves(Set<Leave> leaves) {
        this.setOneleaves(leaves);
        return this;
    }

    public Event addOneleave(Leave leave) {
        this.oneleaves.add(leave);
        leave.setPeriod(this);
        return this;
    }

    public Event removeOneleave(Leave leave) {
        this.oneleaves.remove(leave);
        leave.setPeriod(null);
        return this;
    }

    public Set<Calendar> getOwncalendars() {
        return this.owncalendars;
    }

    public void setOwncalendars(Set<Calendar> calendars) {
        if (this.owncalendars != null) {
            this.owncalendars.forEach(i -> i.removeEvents(this));
        }
        if (calendars != null) {
            calendars.forEach(i -> i.addEvents(this));
        }
        this.owncalendars = calendars;
    }

    public Event owncalendars(Set<Calendar> calendars) {
        this.setOwncalendars(calendars);
        return this;
    }

    public Event addOwncalendar(Calendar calendar) {
        this.owncalendars.add(calendar);
        calendar.getEvents().add(this);
        return this;
    }

    public Event removeOwncalendar(Calendar calendar) {
        this.owncalendars.remove(calendar);
        calendar.getEvents().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Event)) {
            return false;
        }
        return getId() != null && getId().equals(((Event) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Event{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", start='" + getStart() + "'" +
            ", end='" + getEnd() + "'" +
            ", link='" + getLink() + "'" +
            "}";
    }
}
