package com.sing.singabsence.domain;

import static com.sing.singabsence.domain.CalendarTestSamples.*;
import static com.sing.singabsence.domain.EmployeeTestSamples.*;
import static com.sing.singabsence.domain.EventTestSamples.*;
import static com.sing.singabsence.domain.TeamTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.sing.singabsence.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CalendarTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Calendar.class);
        Calendar calendar1 = getCalendarSample1();
        Calendar calendar2 = new Calendar();
        assertThat(calendar1).isNotEqualTo(calendar2);

        calendar2.setId(calendar1.getId());
        assertThat(calendar1).isEqualTo(calendar2);

        calendar2 = getCalendarSample2();
        assertThat(calendar1).isNotEqualTo(calendar2);
    }

    @Test
    void eventsTest() throws Exception {
        Calendar calendar = getCalendarRandomSampleGenerator();
        Event eventBack = getEventRandomSampleGenerator();

        calendar.addEvents(eventBack);
        assertThat(calendar.getEvents()).containsOnly(eventBack);

        calendar.removeEvents(eventBack);
        assertThat(calendar.getEvents()).doesNotContain(eventBack);

        calendar.events(new HashSet<>(Set.of(eventBack)));
        assertThat(calendar.getEvents()).containsOnly(eventBack);

        calendar.setEvents(new HashSet<>());
        assertThat(calendar.getEvents()).doesNotContain(eventBack);
    }

    @Test
    void ownerTest() throws Exception {
        Calendar calendar = getCalendarRandomSampleGenerator();
        Employee employeeBack = getEmployeeRandomSampleGenerator();

        calendar.setOwner(employeeBack);
        assertThat(calendar.getOwner()).isEqualTo(employeeBack);
        assertThat(employeeBack.getCalendar()).isEqualTo(calendar);

        calendar.owner(null);
        assertThat(calendar.getOwner()).isNull();
        assertThat(employeeBack.getCalendar()).isNull();
    }

    @Test
    void ownteamTest() throws Exception {
        Calendar calendar = getCalendarRandomSampleGenerator();
        Team teamBack = getTeamRandomSampleGenerator();

        calendar.setOwnteam(teamBack);
        assertThat(calendar.getOwnteam()).isEqualTo(teamBack);
        assertThat(teamBack.getPlaning()).isEqualTo(calendar);

        calendar.ownteam(null);
        assertThat(calendar.getOwnteam()).isNull();
        assertThat(teamBack.getPlaning()).isNull();
    }
}
