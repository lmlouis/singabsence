package com.sing.singabsence.domain;

import static com.sing.singabsence.domain.AttachmentTestSamples.*;
import static com.sing.singabsence.domain.EmployeeTestSamples.*;
import static com.sing.singabsence.domain.LeaveTestSamples.*;
import static com.sing.singabsence.domain.MessageTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.sing.singabsence.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class AttachmentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Attachment.class);
        Attachment attachment1 = getAttachmentSample1();
        Attachment attachment2 = new Attachment();
        assertThat(attachment1).isNotEqualTo(attachment2);

        attachment2.setId(attachment1.getId());
        assertThat(attachment1).isEqualTo(attachment2);

        attachment2 = getAttachmentSample2();
        assertThat(attachment1).isNotEqualTo(attachment2);
    }

    @Test
    void ownerTest() throws Exception {
        Attachment attachment = getAttachmentRandomSampleGenerator();
        Employee employeeBack = getEmployeeRandomSampleGenerator();

        attachment.setOwner(employeeBack);
        assertThat(attachment.getOwner()).isEqualTo(employeeBack);

        attachment.owner(null);
        assertThat(attachment.getOwner()).isNull();
    }

    @Test
    void leaverequestTest() throws Exception {
        Attachment attachment = getAttachmentRandomSampleGenerator();
        Leave leaveBack = getLeaveRandomSampleGenerator();

        attachment.addLeaverequest(leaveBack);
        assertThat(attachment.getLeaverequests()).containsOnly(leaveBack);
        assertThat(leaveBack.getAttachment()).isEqualTo(attachment);

        attachment.removeLeaverequest(leaveBack);
        assertThat(attachment.getLeaverequests()).doesNotContain(leaveBack);
        assertThat(leaveBack.getAttachment()).isNull();

        attachment.leaverequests(new HashSet<>(Set.of(leaveBack)));
        assertThat(attachment.getLeaverequests()).containsOnly(leaveBack);
        assertThat(leaveBack.getAttachment()).isEqualTo(attachment);

        attachment.setLeaverequests(new HashSet<>());
        assertThat(attachment.getLeaverequests()).doesNotContain(leaveBack);
        assertThat(leaveBack.getAttachment()).isNull();
    }

    @Test
    void msgTest() throws Exception {
        Attachment attachment = getAttachmentRandomSampleGenerator();
        Message messageBack = getMessageRandomSampleGenerator();

        attachment.addMsg(messageBack);
        assertThat(attachment.getMsgs()).containsOnly(messageBack);
        assertThat(messageBack.getAttachements()).containsOnly(attachment);

        attachment.removeMsg(messageBack);
        assertThat(attachment.getMsgs()).doesNotContain(messageBack);
        assertThat(messageBack.getAttachements()).doesNotContain(attachment);

        attachment.msgs(new HashSet<>(Set.of(messageBack)));
        assertThat(attachment.getMsgs()).containsOnly(messageBack);
        assertThat(messageBack.getAttachements()).containsOnly(attachment);

        attachment.setMsgs(new HashSet<>());
        assertThat(attachment.getMsgs()).doesNotContain(messageBack);
        assertThat(messageBack.getAttachements()).doesNotContain(attachment);
    }
}
