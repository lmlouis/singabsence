package com.sing.singabsence.service;

import com.sing.singabsence.service.dto.TeamDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.sing.singabsence.domain.Team}.
 */
public interface TeamService {
    /**
     * Save a team.
     *
     * @param teamDTO the entity to save.
     * @return the persisted entity.
     */
    TeamDTO save(TeamDTO teamDTO);

    /**
     * Updates a team.
     *
     * @param teamDTO the entity to update.
     * @return the persisted entity.
     */
    TeamDTO update(TeamDTO teamDTO);

    /**
     * Partially updates a team.
     *
     * @param teamDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TeamDTO> partialUpdate(TeamDTO teamDTO);

    /**
     * Get all the teams.
     *
     * @return the list of entities.
     */
    List<TeamDTO> findAll();

    /**
     * Get all the teams with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TeamDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" team.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TeamDTO> findOne(Long id);

    /**
     * Delete the "id" team.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
