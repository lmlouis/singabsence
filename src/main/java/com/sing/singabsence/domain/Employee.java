package com.sing.singabsence.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Employee.
 */
@Entity
@Table(name = "employee")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "fullname", nullable = false)
    private String fullname;

    @NotNull
    @Column(name = "phone", nullable = false)
    private String phone;

    @NotNull
    @Column(name = "position", nullable = false)
    private String position;

    @NotNull
    @Column(name = "location", nullable = false)
    private String location;

    @NotNull
    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private User authenticateby;

    @JsonIgnoreProperties(value = { "events", "owner", "ownteam" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Calendar calendar;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "owner", "leaverequests", "msgs" }, allowSetters = true)
    private Set<Attachment> attachments = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "creator")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "creator", "owncalendars" }, allowSetters = true)
    private Set<Event> createdevents = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_employee__leaves",
        joinColumns = @JoinColumn(name = "employee_id"),
        inverseJoinColumns = @JoinColumn(name = "leaves_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "workflow", "attachment", "sentto", "requestedbies" }, allowSetters = true)
    private Set<Leave> leaves = new HashSet<>();

    @JsonIgnoreProperties(value = { "director", "units" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "director")
    private Organization ownenterprise;

    @JsonIgnoreProperties(value = { "lead", "planing", "ownmembers", "members", "organizations" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "lead")
    private Team ownteam;

    @JsonIgnoreProperties(value = { "from", "attachements", "tos" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "from")
    private Message ownmessage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "lead", "planing", "ownmembers", "members", "organizations" }, allowSetters = true)
    private Team service;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sentto")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "workflow", "attachment", "sentto", "requestedbies" }, allowSetters = true)
    private Set<Leave> requests = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "tos")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "from", "attachements", "tos" }, allowSetters = true)
    private Set<Message> inboxes = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "members")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "lead", "planing", "ownmembers", "members", "organizations" }, allowSetters = true)
    private Set<Team> teams = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Employee id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullname() {
        return this.fullname;
    }

    public Employee fullname(String fullname) {
        this.setFullname(fullname);
        return this;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return this.phone;
    }

    public Employee phone(String phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPosition() {
        return this.position;
    }

    public Employee position(String position) {
        this.setPosition(position);
        return this;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getLocation() {
        return this.location;
    }

    public Employee location(String location) {
        this.setLocation(location);
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getBirthday() {
        return this.birthday;
    }

    public Employee birthday(LocalDate birthday) {
        this.setBirthday(birthday);
        return this;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public User getAuthenticateby() {
        return this.authenticateby;
    }

    public void setAuthenticateby(User user) {
        this.authenticateby = user;
    }

    public Employee authenticateby(User user) {
        this.setAuthenticateby(user);
        return this;
    }

    public Calendar getCalendar() {
        return this.calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public Employee calendar(Calendar calendar) {
        this.setCalendar(calendar);
        return this;
    }

    public Set<Attachment> getAttachments() {
        return this.attachments;
    }

    public void setAttachments(Set<Attachment> attachments) {
        if (this.attachments != null) {
            this.attachments.forEach(i -> i.setOwner(null));
        }
        if (attachments != null) {
            attachments.forEach(i -> i.setOwner(this));
        }
        this.attachments = attachments;
    }

    public Employee attachments(Set<Attachment> attachments) {
        this.setAttachments(attachments);
        return this;
    }

    public Employee addAttachments(Attachment attachment) {
        this.attachments.add(attachment);
        attachment.setOwner(this);
        return this;
    }

    public Employee removeAttachments(Attachment attachment) {
        this.attachments.remove(attachment);
        attachment.setOwner(null);
        return this;
    }

    public Set<Event> getCreatedevents() {
        return this.createdevents;
    }

    public void setCreatedevents(Set<Event> events) {
        if (this.createdevents != null) {
            this.createdevents.forEach(i -> i.setCreator(null));
        }
        if (events != null) {
            events.forEach(i -> i.setCreator(this));
        }
        this.createdevents = events;
    }

    public Employee createdevents(Set<Event> events) {
        this.setCreatedevents(events);
        return this;
    }

    public Employee addCreatedevents(Event event) {
        this.createdevents.add(event);
        event.setCreator(this);
        return this;
    }

    public Employee removeCreatedevents(Event event) {
        this.createdevents.remove(event);
        event.setCreator(null);
        return this;
    }

    public Set<Leave> getLeaves() {
        return this.leaves;
    }

    public void setLeaves(Set<Leave> leaves) {
        this.leaves = leaves;
    }

    public Employee leaves(Set<Leave> leaves) {
        this.setLeaves(leaves);
        return this;
    }

    public Employee addLeaves(Leave leave) {
        this.leaves.add(leave);
        return this;
    }

    public Employee removeLeaves(Leave leave) {
        this.leaves.remove(leave);
        return this;
    }

    public Organization getOwnenterprise() {
        return this.ownenterprise;
    }

    public void setOwnenterprise(Organization organization) {
        if (this.ownenterprise != null) {
            this.ownenterprise.setDirector(null);
        }
        if (organization != null) {
            organization.setDirector(this);
        }
        this.ownenterprise = organization;
    }

    public Employee ownenterprise(Organization organization) {
        this.setOwnenterprise(organization);
        return this;
    }

    public Team getOwnteam() {
        return this.ownteam;
    }

    public void setOwnteam(Team team) {
        if (this.ownteam != null) {
            this.ownteam.setLead(null);
        }
        if (team != null) {
            team.setLead(this);
        }
        this.ownteam = team;
    }

    public Employee ownteam(Team team) {
        this.setOwnteam(team);
        return this;
    }

    public Message getOwnmessage() {
        return this.ownmessage;
    }

    public void setOwnmessage(Message message) {
        if (this.ownmessage != null) {
            this.ownmessage.setFrom(null);
        }
        if (message != null) {
            message.setFrom(this);
        }
        this.ownmessage = message;
    }

    public Employee ownmessage(Message message) {
        this.setOwnmessage(message);
        return this;
    }

    public Team getService() {
        return this.service;
    }

    public void setService(Team team) {
        this.service = team;
    }

    public Employee service(Team team) {
        this.setService(team);
        return this;
    }

    public Set<Leave> getRequests() {
        return this.requests;
    }

    public void setRequests(Set<Leave> leaves) {
        if (this.requests != null) {
            this.requests.forEach(i -> i.setSentto(null));
        }
        if (leaves != null) {
            leaves.forEach(i -> i.setSentto(this));
        }
        this.requests = leaves;
    }

    public Employee requests(Set<Leave> leaves) {
        this.setRequests(leaves);
        return this;
    }

    public Employee addRequest(Leave leave) {
        this.requests.add(leave);
        leave.setSentto(this);
        return this;
    }

    public Employee removeRequest(Leave leave) {
        this.requests.remove(leave);
        leave.setSentto(null);
        return this;
    }

    public Set<Message> getInboxes() {
        return this.inboxes;
    }

    public void setInboxes(Set<Message> messages) {
        if (this.inboxes != null) {
            this.inboxes.forEach(i -> i.removeTo(this));
        }
        if (messages != null) {
            messages.forEach(i -> i.addTo(this));
        }
        this.inboxes = messages;
    }

    public Employee inboxes(Set<Message> messages) {
        this.setInboxes(messages);
        return this;
    }

    public Employee addInbox(Message message) {
        this.inboxes.add(message);
        message.getTos().add(this);
        return this;
    }

    public Employee removeInbox(Message message) {
        this.inboxes.remove(message);
        message.getTos().remove(this);
        return this;
    }

    public Set<Team> getTeams() {
        return this.teams;
    }

    public void setTeams(Set<Team> teams) {
        if (this.teams != null) {
            this.teams.forEach(i -> i.removeMembers(this));
        }
        if (teams != null) {
            teams.forEach(i -> i.addMembers(this));
        }
        this.teams = teams;
    }

    public Employee teams(Set<Team> teams) {
        this.setTeams(teams);
        return this;
    }

    public Employee addTeams(Team team) {
        this.teams.add(team);
        team.getMembers().add(this);
        return this;
    }

    public Employee removeTeams(Team team) {
        this.teams.remove(team);
        team.getMembers().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Employee)) {
            return false;
        }
        return getId() != null && getId().equals(((Employee) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Employee{" +
            "id=" + getId() +
            ", fullname='" + getFullname() + "'" +
            ", phone='" + getPhone() + "'" +
            ", position='" + getPosition() + "'" +
            ", location='" + getLocation() + "'" +
            ", birthday='" + getBirthday() + "'" +
            "}";
    }
}
