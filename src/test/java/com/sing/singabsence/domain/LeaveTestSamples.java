package com.sing.singabsence.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class LeaveTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Leave getLeaveSample1() {
        return new Leave().id(1L);
    }

    public static Leave getLeaveSample2() {
        return new Leave().id(2L);
    }

    public static Leave getLeaveRandomSampleGenerator() {
        return new Leave().id(longCount.incrementAndGet());
    }
}
