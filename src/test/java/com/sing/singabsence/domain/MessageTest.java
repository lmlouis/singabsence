package com.sing.singabsence.domain;

import static com.sing.singabsence.domain.AttachmentTestSamples.*;
import static com.sing.singabsence.domain.EmployeeTestSamples.*;
import static com.sing.singabsence.domain.MessageTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.sing.singabsence.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class MessageTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Message.class);
        Message message1 = getMessageSample1();
        Message message2 = new Message();
        assertThat(message1).isNotEqualTo(message2);

        message2.setId(message1.getId());
        assertThat(message1).isEqualTo(message2);

        message2 = getMessageSample2();
        assertThat(message1).isNotEqualTo(message2);
    }

    @Test
    void fromTest() throws Exception {
        Message message = getMessageRandomSampleGenerator();
        Employee employeeBack = getEmployeeRandomSampleGenerator();

        message.setFrom(employeeBack);
        assertThat(message.getFrom()).isEqualTo(employeeBack);

        message.from(null);
        assertThat(message.getFrom()).isNull();
    }

    @Test
    void attachementsTest() throws Exception {
        Message message = getMessageRandomSampleGenerator();
        Attachment attachmentBack = getAttachmentRandomSampleGenerator();

        message.addAttachements(attachmentBack);
        assertThat(message.getAttachements()).containsOnly(attachmentBack);

        message.removeAttachements(attachmentBack);
        assertThat(message.getAttachements()).doesNotContain(attachmentBack);

        message.attachements(new HashSet<>(Set.of(attachmentBack)));
        assertThat(message.getAttachements()).containsOnly(attachmentBack);

        message.setAttachements(new HashSet<>());
        assertThat(message.getAttachements()).doesNotContain(attachmentBack);
    }

    @Test
    void toTest() throws Exception {
        Message message = getMessageRandomSampleGenerator();
        Employee employeeBack = getEmployeeRandomSampleGenerator();

        message.addTo(employeeBack);
        assertThat(message.getTos()).containsOnly(employeeBack);

        message.removeTo(employeeBack);
        assertThat(message.getTos()).doesNotContain(employeeBack);

        message.tos(new HashSet<>(Set.of(employeeBack)));
        assertThat(message.getTos()).containsOnly(employeeBack);

        message.setTos(new HashSet<>());
        assertThat(message.getTos()).doesNotContain(employeeBack);
    }
}
