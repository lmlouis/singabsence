package com.sing.singabsence.service;

import com.sing.singabsence.service.dto.WorkflowDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.sing.singabsence.domain.Workflow}.
 */
public interface WorkflowService {
    /**
     * Save a workflow.
     *
     * @param workflowDTO the entity to save.
     * @return the persisted entity.
     */
    WorkflowDTO save(WorkflowDTO workflowDTO);

    /**
     * Updates a workflow.
     *
     * @param workflowDTO the entity to update.
     * @return the persisted entity.
     */
    WorkflowDTO update(WorkflowDTO workflowDTO);

    /**
     * Partially updates a workflow.
     *
     * @param workflowDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<WorkflowDTO> partialUpdate(WorkflowDTO workflowDTO);

    /**
     * Get all the workflows.
     *
     * @return the list of entities.
     */
    List<WorkflowDTO> findAll();

    /**
     * Get the "id" workflow.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WorkflowDTO> findOne(Long id);

    /**
     * Delete the "id" workflow.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
