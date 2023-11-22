package com.sing.singabsence.repository;

import com.sing.singabsence.domain.Organization;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface OrganizationRepositoryWithBagRelationships {
    Optional<Organization> fetchBagRelationships(Optional<Organization> organization);

    List<Organization> fetchBagRelationships(List<Organization> organizations);

    Page<Organization> fetchBagRelationships(Page<Organization> organizations);
}
