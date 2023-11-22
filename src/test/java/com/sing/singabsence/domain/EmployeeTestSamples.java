package com.sing.singabsence.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class EmployeeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Employee getEmployeeSample1() {
        return new Employee().id(1L).fullname("fullname1").phone("phone1").position("position1").location("location1");
    }

    public static Employee getEmployeeSample2() {
        return new Employee().id(2L).fullname("fullname2").phone("phone2").position("position2").location("location2");
    }

    public static Employee getEmployeeRandomSampleGenerator() {
        return new Employee()
            .id(longCount.incrementAndGet())
            .fullname(UUID.randomUUID().toString())
            .phone(UUID.randomUUID().toString())
            .position(UUID.randomUUID().toString())
            .location(UUID.randomUUID().toString());
    }
}
