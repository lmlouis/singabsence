package com.sing.singabsence.domain;

import static com.sing.singabsence.domain.LeaveTestSamples.*;
import static com.sing.singabsence.domain.WorkflowTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.sing.singabsence.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class WorkflowTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Workflow.class);
        Workflow workflow1 = getWorkflowSample1();
        Workflow workflow2 = new Workflow();
        assertThat(workflow1).isNotEqualTo(workflow2);

        workflow2.setId(workflow1.getId());
        assertThat(workflow1).isEqualTo(workflow2);

        workflow2 = getWorkflowSample2();
        assertThat(workflow1).isNotEqualTo(workflow2);
    }

    @Test
    void ownrequestsTest() throws Exception {
        Workflow workflow = getWorkflowRandomSampleGenerator();
        Leave leaveBack = getLeaveRandomSampleGenerator();

        workflow.addOwnrequests(leaveBack);
        assertThat(workflow.getOwnrequests()).containsOnly(leaveBack);
        assertThat(leaveBack.getWorkflow()).isEqualTo(workflow);

        workflow.removeOwnrequests(leaveBack);
        assertThat(workflow.getOwnrequests()).doesNotContain(leaveBack);
        assertThat(leaveBack.getWorkflow()).isNull();

        workflow.ownrequests(new HashSet<>(Set.of(leaveBack)));
        assertThat(workflow.getOwnrequests()).containsOnly(leaveBack);
        assertThat(leaveBack.getWorkflow()).isEqualTo(workflow);

        workflow.setOwnrequests(new HashSet<>());
        assertThat(workflow.getOwnrequests()).doesNotContain(leaveBack);
        assertThat(leaveBack.getWorkflow()).isNull();
    }
}
