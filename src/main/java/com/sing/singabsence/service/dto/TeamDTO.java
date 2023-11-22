package com.sing.singabsence.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.sing.singabsence.domain.Team} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TeamDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private EmployeeDTO lead;

    private CalendarDTO planing;

    private Set<EmployeeDTO> members = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EmployeeDTO getLead() {
        return lead;
    }

    public void setLead(EmployeeDTO lead) {
        this.lead = lead;
    }

    public CalendarDTO getPlaning() {
        return planing;
    }

    public void setPlaning(CalendarDTO planing) {
        this.planing = planing;
    }

    public Set<EmployeeDTO> getMembers() {
        return members;
    }

    public void setMembers(Set<EmployeeDTO> members) {
        this.members = members;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TeamDTO)) {
            return false;
        }

        TeamDTO teamDTO = (TeamDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, teamDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TeamDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", lead=" + getLead() +
            ", planing=" + getPlaning() +
            ", members=" + getMembers() +
            "}";
    }
}
