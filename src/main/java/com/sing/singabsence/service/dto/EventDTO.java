package com.sing.singabsence.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.sing.singabsence.domain.Event} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EventDTO implements Serializable {

    private Long id;

    @NotNull
    private String title;

    @NotNull
    private Instant start;

    @NotNull
    private Instant end;

    private String link;

    private EmployeeDTO creator;

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

    public Instant getStart() {
        return start;
    }

    public void setStart(Instant start) {
        this.start = start;
    }

    public Instant getEnd() {
        return end;
    }

    public void setEnd(Instant end) {
        this.end = end;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public EmployeeDTO getCreator() {
        return creator;
    }

    public void setCreator(EmployeeDTO creator) {
        this.creator = creator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EventDTO)) {
            return false;
        }

        EventDTO eventDTO = (EventDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, eventDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EventDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", start='" + getStart() + "'" +
            ", end='" + getEnd() + "'" +
            ", link='" + getLink() + "'" +
            ", creator=" + getCreator() +
            "}";
    }
}
