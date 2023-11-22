package com.sing.singabsence.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class WorkflowTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Workflow getWorkflowSample1() {
        return new Workflow().id(1L).description("description1");
    }

    public static Workflow getWorkflowSample2() {
        return new Workflow().id(2L).description("description2");
    }

    public static Workflow getWorkflowRandomSampleGenerator() {
        return new Workflow().id(longCount.incrementAndGet()).description(UUID.randomUUID().toString());
    }
}
