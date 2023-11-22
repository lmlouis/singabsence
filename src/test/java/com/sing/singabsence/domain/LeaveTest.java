package com.sing.singabsence.domain;

import static com.sing.singabsence.domain.AttachmentTestSamples.*;
import static com.sing.singabsence.domain.EmployeeTestSamples.*;
import static com.sing.singabsence.domain.LeaveTestSamples.*;
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
    void workflowTest() throws Exception {
        Leave leave = getLeaveRandomSampleGenerator();
        Workflow workflowBack = getWorkflowRandomSampleGenerator();

        leave.setWorkflow(workflowBack);
        assertThat(leave.getWorkflow()).isEqualTo(workflowBack);

        leave.workflow(null);
        assertThat(leave.getWorkflow()).isNull();
    }

    @Test
    void attachmentTest() throws Exception {
        Leave leave = getLeaveRandomSampleGenerator();
        Attachment attachmentBack = getAttachmentRandomSampleGenerator();

        leave.setAttachment(attachmentBack);
        assertThat(leave.getAttachment()).isEqualTo(attachmentBack);

        leave.attachment(null);
        assertThat(leave.getAttachment()).isNull();
    }

    @Test
    void senttoTest() throws Exception {
        Leave leave = getLeaveRandomSampleGenerator();
        Employee employeeBack = getEmployeeRandomSampleGenerator();

        leave.setSentto(employeeBack);
        assertThat(leave.getSentto()).isEqualTo(employeeBack);

        leave.sentto(null);
        assertThat(leave.getSentto()).isNull();
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
