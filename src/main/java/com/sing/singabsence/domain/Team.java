package com.sing.singabsence.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Team.
 */
@Entity
@Table(name = "team")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Team implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

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
    private Employee lead;

    @JsonIgnoreProperties(value = { "events", "owner", "ownteam" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Calendar planing;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "service")
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
    private Set<Employee> ownmembers = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_team__members",
        joinColumns = @JoinColumn(name = "team_id"),
        inverseJoinColumns = @JoinColumn(name = "members_id")
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
    private Set<Employee> members = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "units")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "director", "units" }, allowSetters = true)
    private Set<Organization> organizations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Team id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Team name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Employee getLead() {
        return this.lead;
    }

    public void setLead(Employee employee) {
        this.lead = employee;
    }

    public Team lead(Employee employee) {
        this.setLead(employee);
        return this;
    }

    public Calendar getPlaning() {
        return this.planing;
    }

    public void setPlaning(Calendar calendar) {
        this.planing = calendar;
    }

    public Team planing(Calendar calendar) {
        this.setPlaning(calendar);
        return this;
    }

    public Set<Employee> getOwnmembers() {
        return this.ownmembers;
    }

    public void setOwnmembers(Set<Employee> employees) {
        if (this.ownmembers != null) {
            this.ownmembers.forEach(i -> i.setService(null));
        }
        if (employees != null) {
            employees.forEach(i -> i.setService(this));
        }
        this.ownmembers = employees;
    }

    public Team ownmembers(Set<Employee> employees) {
        this.setOwnmembers(employees);
        return this;
    }

    public Team addOwnmember(Employee employee) {
        this.ownmembers.add(employee);
        employee.setService(this);
        return this;
    }

    public Team removeOwnmember(Employee employee) {
        this.ownmembers.remove(employee);
        employee.setService(null);
        return this;
    }

    public Set<Employee> getMembers() {
        return this.members;
    }

    public void setMembers(Set<Employee> employees) {
        this.members = employees;
    }

    public Team members(Set<Employee> employees) {
        this.setMembers(employees);
        return this;
    }

    public Team addMembers(Employee employee) {
        this.members.add(employee);
        return this;
    }

    public Team removeMembers(Employee employee) {
        this.members.remove(employee);
        return this;
    }

    public Set<Organization> getOrganizations() {
        return this.organizations;
    }

    public void setOrganizations(Set<Organization> organizations) {
        if (this.organizations != null) {
            this.organizations.forEach(i -> i.removeUnits(this));
        }
        if (organizations != null) {
            organizations.forEach(i -> i.addUnits(this));
        }
        this.organizations = organizations;
    }

    public Team organizations(Set<Organization> organizations) {
        this.setOrganizations(organizations);
        return this;
    }

    public Team addOrganizations(Organization organization) {
        this.organizations.add(organization);
        organization.getUnits().add(this);
        return this;
    }

    public Team removeOrganizations(Organization organization) {
        this.organizations.remove(organization);
        organization.getUnits().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Team)) {
            return false;
        }
        return getId() != null && getId().equals(((Team) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Team{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
