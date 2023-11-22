package com.sing.singabsence.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sing.singabsence.domain.enumeration.TypeOfAttachment;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Attachment.
 */
@Entity
@Table(name = "attachment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Attachment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private TypeOfAttachment category;

    @Lob
    @Column(name = "picture")
    private byte[] picture;

    @Column(name = "picture_content_type")
    private String pictureContentType;

    @Lob
    @Column(name = "document")
    private byte[] document;

    @Column(name = "document_content_type")
    private String documentContentType;

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
            "service",
            "requests",
            "inboxes",
            "teams",
        },
        allowSetters = true
    )
    private Employee owner;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "attachment")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "workflow", "attachment", "sentto", "requestedbies" }, allowSetters = true)
    private Set<Leave> leaverequests = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "attachements")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "from", "attachements", "tos" }, allowSetters = true)
    private Set<Message> msgs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Attachment id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Attachment name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TypeOfAttachment getCategory() {
        return this.category;
    }

    public Attachment category(TypeOfAttachment category) {
        this.setCategory(category);
        return this;
    }

    public void setCategory(TypeOfAttachment category) {
        this.category = category;
    }

    public byte[] getPicture() {
        return this.picture;
    }

    public Attachment picture(byte[] picture) {
        this.setPicture(picture);
        return this;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getPictureContentType() {
        return this.pictureContentType;
    }

    public Attachment pictureContentType(String pictureContentType) {
        this.pictureContentType = pictureContentType;
        return this;
    }

    public void setPictureContentType(String pictureContentType) {
        this.pictureContentType = pictureContentType;
    }

    public byte[] getDocument() {
        return this.document;
    }

    public Attachment document(byte[] document) {
        this.setDocument(document);
        return this;
    }

    public void setDocument(byte[] document) {
        this.document = document;
    }

    public String getDocumentContentType() {
        return this.documentContentType;
    }

    public Attachment documentContentType(String documentContentType) {
        this.documentContentType = documentContentType;
        return this;
    }

    public void setDocumentContentType(String documentContentType) {
        this.documentContentType = documentContentType;
    }

    public Employee getOwner() {
        return this.owner;
    }

    public void setOwner(Employee employee) {
        this.owner = employee;
    }

    public Attachment owner(Employee employee) {
        this.setOwner(employee);
        return this;
    }

    public Set<Leave> getLeaverequests() {
        return this.leaverequests;
    }

    public void setLeaverequests(Set<Leave> leaves) {
        if (this.leaverequests != null) {
            this.leaverequests.forEach(i -> i.setAttachment(null));
        }
        if (leaves != null) {
            leaves.forEach(i -> i.setAttachment(this));
        }
        this.leaverequests = leaves;
    }

    public Attachment leaverequests(Set<Leave> leaves) {
        this.setLeaverequests(leaves);
        return this;
    }

    public Attachment addLeaverequest(Leave leave) {
        this.leaverequests.add(leave);
        leave.setAttachment(this);
        return this;
    }

    public Attachment removeLeaverequest(Leave leave) {
        this.leaverequests.remove(leave);
        leave.setAttachment(null);
        return this;
    }

    public Set<Message> getMsgs() {
        return this.msgs;
    }

    public void setMsgs(Set<Message> messages) {
        if (this.msgs != null) {
            this.msgs.forEach(i -> i.removeAttachements(this));
        }
        if (messages != null) {
            messages.forEach(i -> i.addAttachements(this));
        }
        this.msgs = messages;
    }

    public Attachment msgs(Set<Message> messages) {
        this.setMsgs(messages);
        return this;
    }

    public Attachment addMsg(Message message) {
        this.msgs.add(message);
        message.getAttachements().add(this);
        return this;
    }

    public Attachment removeMsg(Message message) {
        this.msgs.remove(message);
        message.getAttachements().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Attachment)) {
            return false;
        }
        return getId() != null && getId().equals(((Attachment) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Attachment{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", category='" + getCategory() + "'" +
            ", picture='" + getPicture() + "'" +
            ", pictureContentType='" + getPictureContentType() + "'" +
            ", document='" + getDocument() + "'" +
            ", documentContentType='" + getDocumentContentType() + "'" +
            "}";
    }
}
