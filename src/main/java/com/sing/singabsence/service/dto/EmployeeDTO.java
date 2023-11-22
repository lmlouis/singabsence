package com.sing.singabsence.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.sing.singabsence.domain.Employee} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmployeeDTO implements Serializable {

    private Long id;

    @NotNull
    private String fullname;

    @NotNull
    private String phone;

    @NotNull
    private String position;

    @NotNull
    private String location;

    @NotNull
    private LocalDate birthday;

    private UserDTO authenticateby;

    private CalendarDTO calendar;

    private Set<LeaveDTO> leaves = new HashSet<>();

    private TeamDTO service;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public UserDTO getAuthenticateby() {
        return authenticateby;
    }

    public void setAuthenticateby(UserDTO authenticateby) {
        this.authenticateby = authenticateby;
    }

    public CalendarDTO getCalendar() {
        return calendar;
    }

    public void setCalendar(CalendarDTO calendar) {
        this.calendar = calendar;
    }

    public Set<LeaveDTO> getLeaves() {
        return leaves;
    }

    public void setLeaves(Set<LeaveDTO> leaves) {
        this.leaves = leaves;
    }

    public TeamDTO getService() {
        return service;
    }

    public void setService(TeamDTO service) {
        this.service = service;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeeDTO)) {
            return false;
        }

        EmployeeDTO employeeDTO = (EmployeeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, employeeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeDTO{" +
            "id=" + getId() +
            ", fullname='" + getFullname() + "'" +
            ", phone='" + getPhone() + "'" +
            ", position='" + getPosition() + "'" +
            ", location='" + getLocation() + "'" +
            ", birthday='" + getBirthday() + "'" +
            ", authenticateby=" + getAuthenticateby() +
            ", calendar=" + getCalendar() +
            ", leaves=" + getLeaves() +
            ", service=" + getService() +
            "}";
    }
}
