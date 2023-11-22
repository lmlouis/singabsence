package com.sing.singabsence.repository;

import com.sing.singabsence.domain.Message;
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
public class MessageRepositoryWithBagRelationshipsImpl implements MessageRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Message> fetchBagRelationships(Optional<Message> message) {
        return message.map(this::fetchAttachements).map(this::fetchTos);
    }

    @Override
    public Page<Message> fetchBagRelationships(Page<Message> messages) {
        return new PageImpl<>(fetchBagRelationships(messages.getContent()), messages.getPageable(), messages.getTotalElements());
    }

    @Override
    public List<Message> fetchBagRelationships(List<Message> messages) {
        return Optional.of(messages).map(this::fetchAttachements).map(this::fetchTos).orElse(Collections.emptyList());
    }

    Message fetchAttachements(Message result) {
        return entityManager
            .createQuery("select message from Message message left join fetch message.attachements where message.id = :id", Message.class)
            .setParameter("id", result.getId())
            .getSingleResult();
    }

    List<Message> fetchAttachements(List<Message> messages) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, messages.size()).forEach(index -> order.put(messages.get(index).getId(), index));
        List<Message> result = entityManager
            .createQuery(
                "select message from Message message left join fetch message.attachements where message in :messages",
                Message.class
            )
            .setParameter("messages", messages)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }

    Message fetchTos(Message result) {
        return entityManager
            .createQuery("select message from Message message left join fetch message.tos where message.id = :id", Message.class)
            .setParameter("id", result.getId())
            .getSingleResult();
    }

    List<Message> fetchTos(List<Message> messages) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, messages.size()).forEach(index -> order.put(messages.get(index).getId(), index));
        List<Message> result = entityManager
            .createQuery("select message from Message message left join fetch message.tos where message in :messages", Message.class)
            .setParameter("messages", messages)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
