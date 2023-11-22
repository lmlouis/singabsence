package com.sing.singabsence.repository;

import com.sing.singabsence.domain.Calendar;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class CalendarRepositoryWithBagRelationshipsImpl implements CalendarRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Calendar> fetchBagRelationships(Optional<Calendar> calendar) {
        return calendar.map(this::fetchEvents);
    }

    @Override
    public Page<Calendar> fetchBagRelationships(Page<Calendar> calendars) {
        return new PageImpl<>(fetchBagRelationships(calendars.getContent()), calendars.getPageable(), calendars.getTotalElements());
    }

    @Override
    public List<Calendar> fetchBagRelationships(List<Calendar> calendars) {
        return Optional.of(calendars).map(this::fetchEvents).orElse(Collections.emptyList());
    }

    Calendar fetchEvents(Calendar result) {
        return entityManager
            .createQuery("select calendar from Calendar calendar left join fetch calendar.events where calendar.id = :id", Calendar.class)
            .setParameter("id", result.getId())
            .getSingleResult();
    }

    List<Calendar> fetchEvents(List<Calendar> calendars) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, calendars.size()).forEach(index -> order.put(calendars.get(index).getId(), index));
        List<Calendar> result = entityManager
            .createQuery(
                "select calendar from Calendar calendar left join fetch calendar.events where calendar in :calendars",
                Calendar.class
            )
            .setParameter("calendars", calendars)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
