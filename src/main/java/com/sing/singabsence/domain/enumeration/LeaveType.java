package com.sing.singabsence.domain.enumeration;

/**
 * The LeaveType enumeration.
 */
public enum LeaveType {
    PAID_LEAVE("Congé payé"),
    UNPAID_LEAVE("Congé sans solde"),
    ANNUAL_LEAVE("Congé annuel"),
    SICK_LEAVE("Congé de maladie"),
    MATERNITY_PATERNITY_LEAVE("Congé de maternité/paternité"),
    PARENTAL_LEAVE("Congé parental"),
    SABBATICAL_LEAVE("Congé sabbatique"),
    PROFESSIONAL_DEVELOPMENT_LEAVE("Congé de formation professionnelle"),
    BEREAVEMENT_LEAVE("Congé de deuil"),
    MARRIAGE_LEAVE("Congé de mariage"),
    MILITARY_SERVICE_LEAVE("Congé pour obligations militaires"),
    RELOCATION_LEAVE("Congé pour déménagement"),
    FAMILY_EVENTS_LEAVE("Congé pour événements familiaux"),
    BLOOD_DONATION_LEAVE("Congé pour don de sang"),
    GRIEF_LEAVE("Congé de deuil"),
    COMPENSATORY_LEAVE("Congé de récupération"),
    CIVIC_SERVICE_LEAVE("Congé pour service civique"),
    OTHER("Autre");

    private final String value;

    LeaveType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
