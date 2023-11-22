package com.sing.singabsence.domain;

import static com.sing.singabsence.domain.CalendarTestSamples.*;
import static com.sing.singabsence.domain.EmployeeTestSamples.*;
import static com.sing.singabsence.domain.OrganizationTestSamples.*;
import static com.sing.singabsence.domain.TeamTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.sing.singabsence.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class TeamTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Team.class);
        Team team1 = getTeamSample1();
        Team team2 = new Team();
        assertThat(team1).isNotEqualTo(team2);

        team2.setId(team1.getId());
        assertThat(team1).isEqualTo(team2);

        team2 = getTeamSample2();
        assertThat(team1).isNotEqualTo(team2);
    }

    @Test
    void leadTest() throws Exception {
        Team team = getTeamRandomSampleGenerator();
        Employee employeeBack = getEmployeeRandomSampleGenerator();

        team.setLead(employeeBack);
        assertThat(team.getLead()).isEqualTo(employeeBack);

        team.lead(null);
        assertThat(team.getLead()).isNull();
    }

    @Test
    void planingTest() throws Exception {
        Team team = getTeamRandomSampleGenerator();
        Calendar calendarBack = getCalendarRandomSampleGenerator();

        team.setPlaning(calendarBack);
        assertThat(team.getPlaning()).isEqualTo(calendarBack);

        team.planing(null);
        assertThat(team.getPlaning()).isNull();
    }

    @Test
    void ownmemberTest() throws Exception {
        Team team = getTeamRandomSampleGenerator();
        Employee employeeBack = getEmployeeRandomSampleGenerator();

        team.addOwnmember(employeeBack);
        assertThat(team.getOwnmembers()).containsOnly(employeeBack);
        assertThat(employeeBack.getService()).isEqualTo(team);

        team.removeOwnmember(employeeBack);
        assertThat(team.getOwnmembers()).doesNotContain(employeeBack);
        assertThat(employeeBack.getService()).isNull();

        team.ownmembers(new HashSet<>(Set.of(employeeBack)));
        assertThat(team.getOwnmembers()).containsOnly(employeeBack);
        assertThat(employeeBack.getService()).isEqualTo(team);

        team.setOwnmembers(new HashSet<>());
        assertThat(team.getOwnmembers()).doesNotContain(employeeBack);
        assertThat(employeeBack.getService()).isNull();
    }

    @Test
    void membersTest() throws Exception {
        Team team = getTeamRandomSampleGenerator();
        Employee employeeBack = getEmployeeRandomSampleGenerator();

        team.addMembers(employeeBack);
        assertThat(team.getMembers()).containsOnly(employeeBack);

        team.removeMembers(employeeBack);
        assertThat(team.getMembers()).doesNotContain(employeeBack);

        team.members(new HashSet<>(Set.of(employeeBack)));
        assertThat(team.getMembers()).containsOnly(employeeBack);

        team.setMembers(new HashSet<>());
        assertThat(team.getMembers()).doesNotContain(employeeBack);
    }

    @Test
    void organizationsTest() throws Exception {
        Team team = getTeamRandomSampleGenerator();
        Organization organizationBack = getOrganizationRandomSampleGenerator();

        team.addOrganizations(organizationBack);
        assertThat(team.getOrganizations()).containsOnly(organizationBack);
        assertThat(organizationBack.getUnits()).containsOnly(team);

        team.removeOrganizations(organizationBack);
        assertThat(team.getOrganizations()).doesNotContain(organizationBack);
        assertThat(organizationBack.getUnits()).doesNotContain(team);

        team.organizations(new HashSet<>(Set.of(organizationBack)));
        assertThat(team.getOrganizations()).containsOnly(organizationBack);
        assertThat(organizationBack.getUnits()).containsOnly(team);

        team.setOrganizations(new HashSet<>());
        assertThat(team.getOrganizations()).doesNotContain(organizationBack);
        assertThat(organizationBack.getUnits()).doesNotContain(team);
    }
}
