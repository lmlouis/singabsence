package com.sing.singabsence.repository;

import com.sing.singabsence.domain.Employee;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Employee entity.
 *
 * When extending this class, extend EmployeeRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface EmployeeRepository extends EmployeeRepositoryWithBagRelationships, JpaRepository<Employee, Long> {
    default Optional<Employee> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findOneWithToOneRelationships(id));
    }

    default List<Employee> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships());
    }

    default Page<Employee> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships(pageable));
    }

    @Query(
        value = "select employee from Employee employee left join fetch employee.authenticateby",
        countQuery = "select count(employee) from Employee employee"
    )
    Page<Employee> findAllWithToOneRelationships(Pageable pageable);

    @Query("select employee from Employee employee left join fetch employee.authenticateby")
    List<Employee> findAllWithToOneRelationships();

    @Query("select employee from Employee employee left join fetch employee.authenticateby where employee.id =:id")
    Optional<Employee> findOneWithToOneRelationships(@Param("id") Long id);
}
