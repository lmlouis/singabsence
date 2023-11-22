package com.sing.singabsence.domain;

import static com.sing.singabsence.domain.AttachmentTestSamples.*;
import static com.sing.singabsence.domain.CalendarTestSamples.*;
import static com.sing.singabsence.domain.EmployeeTestSamples.*;
import static com.sing.singabsence.domain.EventTestSamples.*;
import static com.sing.singabsence.domain.LeaveTestSamples.*;
import static com.sing.singabsence.domain.MessageTestSamples.*;
import static com.sing.singabsence.domain.OrganizationTestSamples.*;
import static com.sing.singabsence.domain.TeamTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.sing.singabsence.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class EmployeeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Employee.class);
        Employee employee1 = getEmployeeSample1();
        Employee employee2 = new Employee();
        assertThat(employee1).isNotEqualTo(employee2);

        employee2.setId(employee1.getId());
        assertThat(employee1).isEqualTo(employee2);

        employee2 = getEmployeeSample2();
        assertThat(employee1).isNotEqualTo(employee2);
    }

    @Test
    void calendarTest() throws Exception {
        Employee employee = getEmployeeRandomSampleGenerator();
        Calendar calendarBack = getCalendarRandomSampleGenerator();

        employee.setCalendar(calendarBack);
        assertThat(employee.getCalendar()).isEqualTo(calendarBack);

        employee.calendar(null);
        assertThat(employee.getCalendar()).isNull();
    }

    @Test
    void attachmentsTest() throws Exception {
        Employee employee = getEmployeeRandomSampleGenerator();
        Attachment attachmentBack = getAttachmentRandomSampleGenerator();

        employee.addAttachments(attachmentBack);
        assertThat(employee.getAttachments()).containsOnly(attachmentBack);
        assertThat(attachmentBack.getOwner()).isEqualTo(employee);

        employee.removeAttachments(attachmentBack);
        assertThat(employee.getAttachments()).doesNotContain(attachmentBack);
        assertThat(attachmentBack.getOwner()).isNull();

        employee.attachments(new HashSet<>(Set.of(attachmentBack)));
        assertThat(employee.getAttachments()).containsOnly(attachmentBack);
        assertThat(attachmentBack.getOwner()).isEqualTo(employee);

        employee.setAttachments(new HashSet<>());
        assertThat(employee.getAttachments()).doesNotContain(attachmentBack);
        assertThat(attachmentBack.getOwner()).isNull();
    }

    @Test
    void createdeventsTest() throws Exception {
        Employee employee = getEmployeeRandomSampleGenerator();
        Event eventBack = getEventRandomSampleGenerator();

        employee.addCreatedevents(eventBack);
        assertThat(employee.getCreatedevents()).containsOnly(eventBack);
        assertThat(eventBack.getCreator()).isEqualTo(employee);

        employee.removeCreatedevents(eventBack);
        assertThat(employee.getCreatedevents()).doesNotContain(eventBack);
        assertThat(eventBack.getCreator()).isNull();

        employee.createdevents(new HashSet<>(Set.of(eventBack)));
        assertThat(employee.getCreatedevents()).containsOnly(eventBack);
        assertThat(eventBack.getCreator()).isEqualTo(employee);

        employee.setCreatedevents(new HashSet<>());
        assertThat(employee.getCreatedevents()).doesNotContain(eventBack);
        assertThat(eventBack.getCreator()).isNull();
    }

    @Test
    void leavesTest() throws Exception {
        Employee employee = getEmployeeRandomSampleGenerator();
        Leave leaveBack = getLeaveRandomSampleGenerator();

        employee.addLeaves(leaveBack);
        assertThat(employee.getLeaves()).containsOnly(leaveBack);

        employee.removeLeaves(leaveBack);
        assertThat(employee.getLeaves()).doesNotContain(leaveBack);

        employee.leaves(new HashSet<>(Set.of(leaveBack)));
        assertThat(employee.getLeaves()).containsOnly(leaveBack);

        employee.setLeaves(new HashSet<>());
        assertThat(employee.getLeaves()).doesNotContain(leaveBack);
    }

    @Test
    void ownenterpriseTest() throws Exception {
        Employee employee = getEmployeeRandomSampleGenerator();
        Organization organizationBack = getOrganizationRandomSampleGenerator();

        employee.setOwnenterprise(organizationBack);
        assertThat(employee.getOwnenterprise()).isEqualTo(organizationBack);
        assertThat(organizationBack.getDirector()).isEqualTo(employee);

        employee.ownenterprise(null);
        assertThat(employee.getOwnenterprise()).isNull();
        assertThat(organizationBack.getDirector()).isNull();
    }

    @Test
    void ownteamTest() throws Exception {
        Employee employee = getEmployeeRandomSampleGenerator();
        Team teamBack = getTeamRandomSampleGenerator();

        employee.setOwnteam(teamBack);
        assertThat(employee.getOwnteam()).isEqualTo(teamBack);
        assertThat(teamBack.getLead()).isEqualTo(employee);

        employee.ownteam(null);
        assertThat(employee.getOwnteam()).isNull();
        assertThat(teamBack.getLead()).isNull();
    }

    @Test
    void ownmessageTest() throws Exception {
        Employee employee = getEmployeeRandomSampleGenerator();
        Message messageBack = getMessageRandomSampleGenerator();

        employee.setOwnmessage(messageBack);
        assertThat(employee.getOwnmessage()).isEqualTo(messageBack);
        assertThat(messageBack.getFrom()).isEqualTo(employee);

        employee.ownmessage(null);
        assertThat(employee.getOwnmessage()).isNull();
        assertThat(messageBack.getFrom()).isNull();
    }

    @Test
    void serviceTest() throws Exception {
        Employee employee = getEmployeeRandomSampleGenerator();
        Team teamBack = getTeamRandomSampleGenerator();

        employee.setService(teamBack);
        assertThat(employee.getService()).isEqualTo(teamBack);

        employee.service(null);
        assertThat(employee.getService()).isNull();
    }

    @Test
    void requestTest() throws Exception {
        Employee employee = getEmployeeRandomSampleGenerator();
        Leave leaveBack = getLeaveRandomSampleGenerator();

        employee.addRequest(leaveBack);
        assertThat(employee.getRequests()).containsOnly(leaveBack);
        assertThat(leaveBack.getSentto()).isEqualTo(employee);

        employee.removeRequest(leaveBack);
        assertThat(employee.getRequests()).doesNotContain(leaveBack);
        assertThat(leaveBack.getSentto()).isNull();

        employee.requests(new HashSet<>(Set.of(leaveBack)));
        assertThat(employee.getRequests()).containsOnly(leaveBack);
        assertThat(leaveBack.getSentto()).isEqualTo(employee);

        employee.setRequests(new HashSet<>());
        assertThat(employee.getRequests()).doesNotContain(leaveBack);
        assertThat(leaveBack.getSentto()).isNull();
    }

    @Test
    void inboxTest() throws Exception {
        Employee employee = getEmployeeRandomSampleGenerator();
        Message messageBack = getMessageRandomSampleGenerator();

        employee.addInbox(messageBack);
        assertThat(employee.getInboxes()).containsOnly(messageBack);
        assertThat(messageBack.getTos()).containsOnly(employee);

        employee.removeInbox(messageBack);
        assertThat(employee.getInboxes()).doesNotContain(messageBack);
        assertThat(messageBack.getTos()).doesNotContain(employee);

        employee.inboxes(new HashSet<>(Set.of(messageBack)));
        assertThat(employee.getInboxes()).containsOnly(messageBack);
        assertThat(messageBack.getTos()).containsOnly(employee);

        employee.setInboxes(new HashSet<>());
        assertThat(employee.getInboxes()).doesNotContain(messageBack);
        assertThat(messageBack.getTos()).doesNotContain(employee);
    }

    @Test
    void teamsTest() throws Exception {
        Employee employee = getEmployeeRandomSampleGenerator();
        Team teamBack = getTeamRandomSampleGenerator();

        employee.addTeams(teamBack);
        assertThat(employee.getTeams()).containsOnly(teamBack);
        assertThat(teamBack.getMembers()).containsOnly(employee);

        employee.removeTeams(teamBack);
        assertThat(employee.getTeams()).doesNotContain(teamBack);
        assertThat(teamBack.getMembers()).doesNotContain(employee);

        employee.teams(new HashSet<>(Set.of(teamBack)));
        assertThat(employee.getTeams()).containsOnly(teamBack);
        assertThat(teamBack.getMembers()).containsOnly(employee);

        employee.setTeams(new HashSet<>());
        assertThat(employee.getTeams()).doesNotContain(teamBack);
        assertThat(teamBack.getMembers()).doesNotContain(employee);
    }
}
