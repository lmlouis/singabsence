package com.sing.singabsence.domain;

import static com.sing.singabsence.domain.EmployeeTestSamples.*;
import static com.sing.singabsence.domain.EventTestSamples.*;
import static com.sing.singabsence.domain.LeaveTestSamples.*;
import static com.sing.singabsence.domain.MessageTestSamples.*;
import static com.sing.singabsence.domain.WorkflowTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.sing.singabsence.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class LeaveTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Leave.class);
        Leave leave1 = getLeaveSample1();
        Leave leave2 = new Leave();
        assertThat(leave1).isNotEqualTo(leave2);

        leave2.setId(leave1.getId());
        assertThat(leave1).isEqualTo(leave2);

        leave2 = getLeaveSample2();
        assertThat(leave1).isNotEqualTo(leave2);
    }

    @Test
    void ownerTest() throws Exception {
        Leave leave = getLeaveRandomSampleGenerator();
        Employee employeeBack = getEmployeeRandomSampleGenerator();

        leave.setOwner(employeeBack);
        assertThat(leave.getOwner()).isEqualTo(employeeBack);

        leave.owner(null);
        assertThat(leave.getOwner()).isNull();
    }

    @Test
    void workflowTest() throws Exception {
        Leave leave = getLeaveRandomSampleGenerator();
        Workflow workflowBack = getWorkflowRandomSampleGenerator();

        leave.setWorkflow(workflowBack);
        assertThat(leave.getWorkflow()).isEqualTo(workflowBack);

        leave.workflow(null);
        assertThat(leave.getWorkflow()).isNull();
    }

    @Test
    void reasonTest() throws Exception {
        Leave leave = getLeaveRandomSampleGenerator();
        Message messageBack = getMessageRandomSampleGenerator();

        leave.setReason(messageBack);
        assertThat(leave.getReason()).isEqualTo(messageBack);

        leave.reason(null);
        assertThat(leave.getReason()).isNull();
    }

    @Test
    void periodTest() throws Exception {
        Leave leave = getLeaveRandomSampleGenerator();
        Event eventBack = getEventRandomSampleGenerator();

        leave.setPeriod(eventBack);
        assertThat(leave.getPeriod()).isEqualTo(eventBack);

        leave.period(null);
        assertThat(leave.getPeriod()).isNull();
    }

    @Test
    void senttoTest() throws Exception {
        Leave leave = getLeaveRandomSampleGenerator();
        Employee employeeBack = getEmployeeRandomSampleGenerator();

        leave.addSentto(employeeBack);
        assertThat(leave.getSenttos()).containsOnly(employeeBack);

        leave.removeSentto(employeeBack);
        assertThat(leave.getSenttos()).doesNotContain(employeeBack);

        leave.senttos(new HashSet<>(Set.of(employeeBack)));
        assertThat(leave.getSenttos()).containsOnly(employeeBack);

        leave.setSenttos(new HashSet<>());
        assertThat(leave.getSenttos()).doesNotContain(employeeBack);
    }

    @Test
    void ownworkflowTest() throws Exception {
        Leave leave = getLeaveRandomSampleGenerator();
        Workflow workflowBack = getWorkflowRandomSampleGenerator();

        leave.addOwnworkflow(workflowBack);
        assertThat(leave.getOwnworkflows()).containsOnly(workflowBack);
        assertThat(workflowBack.getRequest()).isEqualTo(leave);

        leave.removeOwnworkflow(workflowBack);
        assertThat(leave.getOwnworkflows()).doesNotContain(workflowBack);
        assertThat(workflowBack.getRequest()).isNull();

        leave.ownworkflows(new HashSet<>(Set.of(workflowBack)));
        assertThat(leave.getOwnworkflows()).containsOnly(workflowBack);
        assertThat(workflowBack.getRequest()).isEqualTo(leave);

        leave.setOwnworkflows(new HashSet<>());
        assertThat(leave.getOwnworkflows()).doesNotContain(workflowBack);
        assertThat(workflowBack.getRequest()).isNull();
    }

    @Test
    void requestedbyTest() throws Exception {
        Leave leave = getLeaveRandomSampleGenerator();
        Employee employeeBack = getEmployeeRandomSampleGenerator();

        leave.addRequestedby(employeeBack);
        assertThat(leave.getRequestedbies()).containsOnly(employeeBack);
        assertThat(employeeBack.getLeaves()).containsOnly(leave);

        leave.removeRequestedby(employeeBack);
        assertThat(leave.getRequestedbies()).doesNotContain(employeeBack);
        assertThat(employeeBack.getLeaves()).doesNotContain(leave);

        leave.requestedbies(new HashSet<>(Set.of(employeeBack)));
        assertThat(leave.getRequestedbies()).containsOnly(employeeBack);
        assertThat(employeeBack.getLeaves()).containsOnly(leave);

        leave.setRequestedbies(new HashSet<>());
        assertThat(leave.getRequestedbies()).doesNotContain(employeeBack);
        assertThat(employeeBack.getLeaves()).doesNotContain(leave);
    }
}
