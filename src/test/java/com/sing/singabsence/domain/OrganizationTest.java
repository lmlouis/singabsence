package com.sing.singabsence.domain;

import static com.sing.singabsence.domain.EmployeeTestSamples.*;
import static com.sing.singabsence.domain.OrganizationTestSamples.*;
import static com.sing.singabsence.domain.TeamTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.sing.singabsence.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class OrganizationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Organization.class);
        Organization organization1 = getOrganizationSample1();
        Organization organization2 = new Organization();
        assertThat(organization1).isNotEqualTo(organization2);

        organization2.setId(organization1.getId());
        assertThat(organization1).isEqualTo(organization2);

        organization2 = getOrganizationSample2();
        assertThat(organization1).isNotEqualTo(organization2);
    }

    @Test
    void directorTest() throws Exception {
        Organization organization = getOrganizationRandomSampleGenerator();
        Employee employeeBack = getEmployeeRandomSampleGenerator();

        organization.setDirector(employeeBack);
        assertThat(organization.getDirector()).isEqualTo(employeeBack);

        organization.director(null);
        assertThat(organization.getDirector()).isNull();
    }

    @Test
    void unitsTest() throws Exception {
        Organization organization = getOrganizationRandomSampleGenerator();
        Team teamBack = getTeamRandomSampleGenerator();

        organization.addUnits(teamBack);
        assertThat(organization.getUnits()).containsOnly(teamBack);

        organization.removeUnits(teamBack);
        assertThat(organization.getUnits()).doesNotContain(teamBack);

        organization.units(new HashSet<>(Set.of(teamBack)));
        assertThat(organization.getUnits()).containsOnly(teamBack);

        organization.setUnits(new HashSet<>());
        assertThat(organization.getUnits()).doesNotContain(teamBack);
    }
}
