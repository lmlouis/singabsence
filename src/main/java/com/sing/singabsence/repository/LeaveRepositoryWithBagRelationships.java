package com.sing.singabsence.repository;

import com.sing.singabsence.domain.Leave;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface LeaveRepositoryWithBagRelationships {
    Optional<Leave> fetchBagRelationships(Optional<Leave> leave);

    List<Leave> fetchBagRelationships(List<Leave> leaves);

    Page<Leave> fetchBagRelationships(Page<Leave> leaves);
}
