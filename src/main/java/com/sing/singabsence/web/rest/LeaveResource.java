package com.sing.singabsence.web.rest;

import com.sing.singabsence.repository.LeaveRepository;
import com.sing.singabsence.service.LeaveService;
import com.sing.singabsence.service.dto.LeaveDTO;
import com.sing.singabsence.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.sing.singabsence.domain.Leave}.
 */
@RestController
@RequestMapping("/api/leaves")
public class LeaveResource {

    private final Logger log = LoggerFactory.getLogger(LeaveResource.class);

    private static final String ENTITY_NAME = "leave";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LeaveService leaveService;

    private final LeaveRepository leaveRepository;

    public LeaveResource(LeaveService leaveService, LeaveRepository leaveRepository) {
        this.leaveService = leaveService;
        this.leaveRepository = leaveRepository;
    }

    /**
     * {@code POST  /leaves} : Create a new leave.
     *
     * @param leaveDTO the leaveDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new leaveDTO, or with status {@code 400 (Bad Request)} if the leave has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<LeaveDTO> createLeave(@Valid @RequestBody LeaveDTO leaveDTO) throws URISyntaxException {
        log.debug("REST request to save Leave : {}", leaveDTO);
        if (leaveDTO.getId() != null) {
            throw new BadRequestAlertException("A new leave cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LeaveDTO result = leaveService.save(leaveDTO);
        return ResponseEntity
            .created(new URI("/api/leaves/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /leaves/:id} : Updates an existing leave.
     *
     * @param id the id of the leaveDTO to save.
     * @param leaveDTO the leaveDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leaveDTO,
     * or with status {@code 400 (Bad Request)} if the leaveDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the leaveDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<LeaveDTO> updateLeave(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LeaveDTO leaveDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Leave : {}, {}", id, leaveDTO);
        if (leaveDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leaveDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leaveRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LeaveDTO result = leaveService.update(leaveDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, leaveDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /leaves/:id} : Partial updates given fields of an existing leave, field will ignore if it is null
     *
     * @param id the id of the leaveDTO to save.
     * @param leaveDTO the leaveDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leaveDTO,
     * or with status {@code 400 (Bad Request)} if the leaveDTO is not valid,
     * or with status {@code 404 (Not Found)} if the leaveDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the leaveDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LeaveDTO> partialUpdateLeave(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LeaveDTO leaveDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Leave partially : {}, {}", id, leaveDTO);
        if (leaveDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leaveDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leaveRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LeaveDTO> result = leaveService.partialUpdate(leaveDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, leaveDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /leaves} : get all the leaves.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of leaves in body.
     */
    @GetMapping("")
    public List<LeaveDTO> getAllLeaves() {
        log.debug("REST request to get all Leaves");
        return leaveService.findAll();
    }

    /**
     * {@code GET  /leaves/:id} : get the "id" leave.
     *
     * @param id the id of the leaveDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the leaveDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<LeaveDTO> getLeave(@PathVariable Long id) {
        log.debug("REST request to get Leave : {}", id);
        Optional<LeaveDTO> leaveDTO = leaveService.findOne(id);
        return ResponseUtil.wrapOrNotFound(leaveDTO);
    }

    /**
     * {@code DELETE  /leaves/:id} : delete the "id" leave.
     *
     * @param id the id of the leaveDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLeave(@PathVariable Long id) {
        log.debug("REST request to delete Leave : {}", id);
        leaveService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
