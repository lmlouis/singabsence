package com.sing.singabsence.service;

import com.sing.singabsence.service.dto.EmployeeDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.sing.singabsence.domain.Employee}.
 */
public interface EmployeeService {
    /**
     * Save a employee.
     *
     * @param employeeDTO the entity to save.
     * @return the persisted entity.
     */
    EmployeeDTO save(EmployeeDTO employeeDTO);

    /**
     * Updates a employee.
     *
     * @param employeeDTO the entity to update.
     * @return the persisted entity.
     */
    EmployeeDTO update(EmployeeDTO employeeDTO);

    /**
     * Partially updates a employee.
     *
     * @param employeeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EmployeeDTO> partialUpdate(EmployeeDTO employeeDTO);

    /**
     * Get all the employees.
     *
     * @return the list of entities.
     */
    List<EmployeeDTO> findAll();

    /**
     * Get all the EmployeeDTO where Ownenterprise is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<EmployeeDTO> findAllWhereOwnenterpriseIsNull();
    /**
     * Get all the EmployeeDTO where Ownteam is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<EmployeeDTO> findAllWhereOwnteamIsNull();
    /**
     * Get all the EmployeeDTO where Ownmessage is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<EmployeeDTO> findAllWhereOwnmessageIsNull();
    /**
     * Get all the EmployeeDTO where Request is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<EmployeeDTO> findAllWhereRequestIsNull();

    /**
     * Get all the employees with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EmployeeDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" employee.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EmployeeDTO> findOne(Long id);

    /**
     * Delete the "id" employee.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
