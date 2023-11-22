package com.sing.singabsence.repository;

import com.sing.singabsence.domain.Leave;
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
public class LeaveRepositoryWithBagRelationshipsImpl implements LeaveRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Leave> fetchBagRelationships(Optional<Leave> leave) {
        return leave.map(this::fetchSenttos);
    }

    @Override
    public Page<Leave> fetchBagRelationships(Page<Leave> leaves) {
        return new PageImpl<>(fetchBagRelationships(leaves.getContent()), leaves.getPageable(), leaves.getTotalElements());
    }

    @Override
    public List<Leave> fetchBagRelationships(List<Leave> leaves) {
        return Optional.of(leaves).map(this::fetchSenttos).orElse(Collections.emptyList());
    }

    Leave fetchSenttos(Leave result) {
        return entityManager
            .createQuery("select leave from Leave leave left join fetch leave.senttos where leave.id = :id", Leave.class)
            .setParameter("id", result.getId())
            .getSingleResult();
    }

    List<Leave> fetchSenttos(List<Leave> leaves) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, leaves.size()).forEach(index -> order.put(leaves.get(index).getId(), index));
        List<Leave> result = entityManager
            .createQuery("select leave from Leave leave left join fetch leave.senttos where leave in :leaves", Leave.class)
            .setParameter("leaves", leaves)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
