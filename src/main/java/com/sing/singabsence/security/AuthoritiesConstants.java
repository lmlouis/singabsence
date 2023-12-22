package com.sing.singabsence.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    // Ajout des rôles employé, reponsable, directeur et administrateur
    public static final String EMPLOYEE = "ROLE_EMPLOYEE";
    public static final String MANAGER = "ROLE_MANAGER";
    public static final String DIRECTOR = "ROLE_DIRECTOR";
    public static final String ADMINISTRATOR = "ROLE_ADMINISTRATOR";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    private AuthoritiesConstants() {}
}
