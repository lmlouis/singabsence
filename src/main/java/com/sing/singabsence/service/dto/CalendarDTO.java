package com.sing.singabsence.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.sing.singabsence.domain.Calendar} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CalendarDTO implements Serializable {

    private Long id;

    private String title;

    private String summury;

    private Instant createdat;

    private Set<EventDTO> events = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummury() {
        return summury;
    }

    public void setSummury(String summury) {
        this.summury = summury;
    }

    public Instant getCreatedat() {
        return createdat;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Set<EventDTO> getEvents() {
        return events;
    }

    public void setEvents(Set<EventDTO> events) {
        this.events = events;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CalendarDTO)) {
            return false;
        }

        CalendarDTO calendarDTO = (CalendarDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, calendarDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CalendarDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", summury='" + getSummury() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", events=" + getEvents() +
            "}";
    }
}
