package com.sing.singabsence.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CalendarTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Calendar getCalendarSample1() {
        return new Calendar().id(1L).title("title1").summury("summury1");
    }

    public static Calendar getCalendarSample2() {
        return new Calendar().id(2L).title("title2").summury("summury2");
    }

    public static Calendar getCalendarRandomSampleGenerator() {
        return new Calendar().id(longCount.incrementAndGet()).title(UUID.randomUUID().toString()).summury(UUID.randomUUID().toString());
    }
}
