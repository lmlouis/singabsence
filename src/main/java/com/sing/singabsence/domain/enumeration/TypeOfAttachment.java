package com.sing.singabsence.domain.enumeration;

/**
 * The TypeOfAttachment enumeration.
 */
public enum TypeOfAttachment {
    IMAGE("Image .jpeg .png .jpg"),
    DOCUMENT("Document .pdf .docx .pptx");

    private final String value;

    TypeOfAttachment(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
