package com.sing.singabsence.service;

import com.sing.singabsence.service.dto.CalendarDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.sing.singabsence.domain.Calendar}.
 */
public interface CalendarService {
    /**
     * Save a calendar.
     *
     * @param calendarDTO the entity to save.
     * @return the persisted entity.
     */
    CalendarDTO save(CalendarDTO calendarDTO);

    /**
     * Updates a calendar.
     *
     * @param calendarDTO the entity to update.
     * @return the persisted entity.
     */
    CalendarDTO update(CalendarDTO calendarDTO);

    /**
     * Partially updates a calendar.
     *
     * @param calendarDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CalendarDTO> partialUpdate(CalendarDTO calendarDTO);

    /**
     * Get all the calendars.
     *
     * @return the list of entities.
     */
    List<CalendarDTO> findAll();

    /**
     * Get all the CalendarDTO where Owner is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<CalendarDTO> findAllWhereOwnerIsNull();
    /**
     * Get all the CalendarDTO where Ownteam is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<CalendarDTO> findAllWhereOwnteamIsNull();

    /**
     * Get all the calendars with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CalendarDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" calendar.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CalendarDTO> findOne(Long id);

    /**
     * Delete the "id" calendar.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
