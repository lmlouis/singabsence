package com.sing.singabsence.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Calendar.
 */
@Entity
@Table(name = "calendar")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Calendar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "summury")
    private String summury;

    @Column(name = "createdat")
    private Instant createdat;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_calendar__events",
        joinColumns = @JoinColumn(name = "calendar_id"),
        inverseJoinColumns = @JoinColumn(name = "events_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "creator", "oneleaves", "owncalendars" }, allowSetters = true)
    private Set<Event> events = new HashSet<>();

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
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "calendar")
    private Employee owner;

    @JsonIgnoreProperties(value = { "lead", "planing", "ownmembers", "members", "organizations" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "planing")
    private Team ownteam;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Calendar id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Calendar title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummury() {
        return this.summury;
    }

    public Calendar summury(String summury) {
        this.setSummury(summury);
        return this;
    }

    public void setSummury(String summury) {
        this.summury = summury;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public Calendar createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Set<Event> getEvents() {
        return this.events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    public Calendar events(Set<Event> events) {
        this.setEvents(events);
        return this;
    }

    public Calendar addEvents(Event event) {
        this.events.add(event);
        return this;
    }

    public Calendar removeEvents(Event event) {
        this.events.remove(event);
        return this;
    }

    public Employee getOwner() {
        return this.owner;
    }

    public void setOwner(Employee employee) {
        if (this.owner != null) {
            this.owner.setCalendar(null);
        }
        if (employee != null) {
            employee.setCalendar(this);
        }
        this.owner = employee;
    }

    public Calendar owner(Employee employee) {
        this.setOwner(employee);
        return this;
    }

    public Team getOwnteam() {
        return this.ownteam;
    }

    public void setOwnteam(Team team) {
        if (this.ownteam != null) {
            this.ownteam.setPlaning(null);
        }
        if (team != null) {
            team.setPlaning(this);
        }
        this.ownteam = team;
    }

    public Calendar ownteam(Team team) {
        this.setOwnteam(team);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Calendar)) {
            return false;
        }
        return getId() != null && getId().equals(((Calendar) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Calendar{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", summury='" + getSummury() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            "}";
    }
}
