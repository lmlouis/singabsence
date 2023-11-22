package com.sing.singabsence.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.sing.singabsence.domain.Message} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MessageDTO implements Serializable {

    private Long id;

    @NotNull
    private String purpose;

    @NotNull
    private String content;

    private Instant sentat;

    private Boolean isread;

    private EmployeeDTO from;

    private Set<AttachmentDTO> attachements = new HashSet<>();

    private Set<EmployeeDTO> tos = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getSentat() {
        return sentat;
    }

    public void setSentat(Instant sentat) {
        this.sentat = sentat;
    }

    public Boolean getIsread() {
        return isread;
    }

    public void setIsread(Boolean isread) {
        this.isread = isread;
    }

    public EmployeeDTO getFrom() {
        return from;
    }

    public void setFrom(EmployeeDTO from) {
        this.from = from;
    }

    public Set<AttachmentDTO> getAttachements() {
        return attachements;
    }

    public void setAttachements(Set<AttachmentDTO> attachements) {
        this.attachements = attachements;
    }

    public Set<EmployeeDTO> getTos() {
        return tos;
    }

    public void setTos(Set<EmployeeDTO> tos) {
        this.tos = tos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MessageDTO)) {
            return false;
        }

        MessageDTO messageDTO = (MessageDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, messageDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MessageDTO{" +
            "id=" + getId() +
            ", purpose='" + getPurpose() + "'" +
            ", content='" + getContent() + "'" +
            ", sentat='" + getSentat() + "'" +
            ", isread='" + getIsread() + "'" +
            ", from=" + getFrom() +
            ", attachements=" + getAttachements() +
            ", tos=" + getTos() +
            "}";
    }
}
