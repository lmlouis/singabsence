package com.sing.singabsence.repository;

import com.sing.singabsence.domain.Message;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface MessageRepositoryWithBagRelationships {
    Optional<Message> fetchBagRelationships(Optional<Message> message);

    List<Message> fetchBagRelationships(List<Message> messages);

    Page<Message> fetchBagRelationships(Page<Message> messages);
}
