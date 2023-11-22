package com.sing.singabsence.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class WorkflowTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Workflow getWorkflowSample1() {
        return new Workflow().id(1L).validation("validation1").description("description1").state(1).label("label1");
    }

    public static Workflow getWorkflowSample2() {
        return new Workflow().id(2L).validation("validation2").description("description2").state(2).label("label2");
    }

    public static Workflow getWorkflowRandomSampleGenerator() {
        return new Workflow()
            .id(longCount.incrementAndGet())
            .validation(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .state(intCount.incrementAndGet())
            .label(UUID.randomUUID().toString());
    }
}
