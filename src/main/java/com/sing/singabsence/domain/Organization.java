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
 * A Organization.
 */
@Entity
@Table(name = "organization")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Organization implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Lob
    @Column(name = "logo")
    private byte[] logo;

    @Column(name = "logo_content_type")
    private String logoContentType;

    @Column(name = "website")
    private String website;

    @NotNull
    @Column(name = "domain", nullable = false)
    private String domain;

    @Column(name = "phone")
    private String phone;

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
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Employee director;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_organization__units",
        joinColumns = @JoinColumn(name = "organization_id"),
        inverseJoinColumns = @JoinColumn(name = "units_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "lead", "planing", "ownmembers", "members", "organizations" }, allowSetters = true)
    private Set<Team> units = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Organization id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Organization name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getLogo() {
        return this.logo;
    }

    public Organization logo(byte[] logo) {
        this.setLogo(logo);
        return this;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getLogoContentType() {
        return this.logoContentType;
    }

    public Organization logoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
        return this;
    }

    public void setLogoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
    }

    public String getWebsite() {
        return this.website;
    }

    public Organization website(String website) {
        this.setWebsite(website);
        return this;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getDomain() {
        return this.domain;
    }

    public Organization domain(String domain) {
        this.setDomain(domain);
        return this;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getPhone() {
        return this.phone;
    }

    public Organization phone(String phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Employee getDirector() {
        return this.director;
    }

    public void setDirector(Employee employee) {
        this.director = employee;
    }

    public Organization director(Employee employee) {
        this.setDirector(employee);
        return this;
    }

    public Set<Team> getUnits() {
        return this.units;
    }

    public void setUnits(Set<Team> teams) {
        this.units = teams;
    }

    public Organization units(Set<Team> teams) {
        this.setUnits(teams);
        return this;
    }

    public Organization addUnits(Team team) {
        this.units.add(team);
        return this;
    }

    public Organization removeUnits(Team team) {
        this.units.remove(team);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Organization)) {
            return false;
        }
        return getId() != null && getId().equals(((Organization) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Organization{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", logo='" + getLogo() + "'" +
            ", logoContentType='" + getLogoContentType() + "'" +
            ", website='" + getWebsite() + "'" +
            ", domain='" + getDomain() + "'" +
            ", phone='" + getPhone() + "'" +
            "}";
    }
}
