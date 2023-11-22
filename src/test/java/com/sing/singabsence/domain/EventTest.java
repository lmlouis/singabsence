package com.sing.singabsence.domain;

import static com.sing.singabsence.domain.CalendarTestSamples.*;
import static com.sing.singabsence.domain.EmployeeTestSamples.*;
import static com.sing.singabsence.domain.EventTestSamples.*;
import static com.sing.singabsence.domain.LeaveTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.sing.singabsence.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class EventTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Event.class);
        Event event1 = getEventSample1();
        Event event2 = new Event();
        assertThat(event1).isNotEqualTo(event2);

        event2.setId(event1.getId());
        assertThat(event1).isEqualTo(event2);

        event2 = getEventSample2();
        assertThat(event1).isNotEqualTo(event2);
    }

    @Test
    void creatorTest() throws Exception {
        Event event = getEventRandomSampleGenerator();
        Employee employeeBack = getEmployeeRandomSampleGenerator();

        event.setCreator(employeeBack);
        assertThat(event.getCreator()).isEqualTo(employeeBack);

        event.creator(null);
        assertThat(event.getCreator()).isNull();
    }

    @Test
    void oneleaveTest() throws Exception {
        Event event = getEventRandomSampleGenerator();
        Leave leaveBack = getLeaveRandomSampleGenerator();

        event.addOneleave(leaveBack);
        assertThat(event.getOneleaves()).containsOnly(leaveBack);
        assertThat(leaveBack.getPeriod()).isEqualTo(event);

        event.removeOneleave(leaveBack);
        assertThat(event.getOneleaves()).doesNotContain(leaveBack);
        assertThat(leaveBack.getPeriod()).isNull();

        event.oneleaves(new HashSet<>(Set.of(leaveBack)));
        assertThat(event.getOneleaves()).containsOnly(leaveBack);
        assertThat(leaveBack.getPeriod()).isEqualTo(event);

        event.setOneleaves(new HashSet<>());
        assertThat(event.getOneleaves()).doesNotContain(leaveBack);
        assertThat(leaveBack.getPeriod()).isNull();
    }

    @Test
    void owncalendarTest() throws Exception {
        Event event = getEventRandomSampleGenerator();
        Calendar calendarBack = getCalendarRandomSampleGenerator();

        event.addOwncalendar(calendarBack);
        assertThat(event.getOwncalendars()).containsOnly(calendarBack);
        assertThat(calendarBack.getEvents()).containsOnly(event);

        event.removeOwncalendar(calendarBack);
        assertThat(event.getOwncalendars()).doesNotContain(calendarBack);
        assertThat(calendarBack.getEvents()).doesNotContain(event);

        event.owncalendars(new HashSet<>(Set.of(calendarBack)));
        assertThat(event.getOwncalendars()).containsOnly(calendarBack);
        assertThat(calendarBack.getEvents()).containsOnly(event);

        event.setOwncalendars(new HashSet<>());
        assertThat(event.getOwncalendars()).doesNotContain(calendarBack);
        assertThat(calendarBack.getEvents()).doesNotContain(event);
    }
}
