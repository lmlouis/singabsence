package com.sing.singabsence.domain.enumeration;

/**
 * The LeaveStatus enumeration.
 */
public enum LeaveStatus {
    PENDING("En attente"),
    APPROVED("Approuvé"),
    REJECTED("Rejeté");

    private final String value;

    LeaveStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
