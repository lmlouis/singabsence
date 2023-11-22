package com.sing.singabsence.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sing.singabsence.IntegrationTest;
import com.sing.singabsence.domain.Workflow;
import com.sing.singabsence.domain.enumeration.LeaveStatus;
import com.sing.singabsence.repository.WorkflowRepository;
import com.sing.singabsence.service.dto.WorkflowDTO;
import com.sing.singabsence.service.mapper.WorkflowMapper;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link WorkflowResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WorkflowResourceIT {

    private static final LeaveStatus DEFAULT_STATUS = LeaveStatus.PENDING;
    private static final LeaveStatus UPDATED_STATUS = LeaveStatus.APPROVED;

    private static final String DEFAULT_VALIDATION = "AAAAAAAAAA";
    private static final String UPDATED_VALIDATION = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_STATE = 1;
    private static final Integer UPDATED_STATE = 2;

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/workflows";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private WorkflowRepository workflowRepository;

    @Autowired
    private WorkflowMapper workflowMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWorkflowMockMvc;

    private Workflow workflow;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Workflow createEntity(EntityManager em) {
        Workflow workflow = new Workflow()
            .status(DEFAULT_STATUS)
            .validation(DEFAULT_VALIDATION)
            .description(DEFAULT_DESCRIPTION)
            .state(DEFAULT_STATE)
            .label(DEFAULT_LABEL);
        return workflow;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Workflow createUpdatedEntity(EntityManager em) {
        Workflow workflow = new Workflow()
            .status(UPDATED_STATUS)
            .validation(UPDATED_VALIDATION)
            .description(UPDATED_DESCRIPTION)
            .state(UPDATED_STATE)
            .label(UPDATED_LABEL);
        return workflow;
    }

    @BeforeEach
    public void initTest() {
        workflow = createEntity(em);
    }

    @Test
    @Transactional
    void createWorkflow() throws Exception {
        int databaseSizeBeforeCreate = workflowRepository.findAll().size();
        // Create the Workflow
        WorkflowDTO workflowDTO = workflowMapper.toDto(workflow);
        restWorkflowMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workflowDTO)))
            .andExpect(status().isCreated());

        // Validate the Workflow in the database
        List<Workflow> workflowList = workflowRepository.findAll();
        assertThat(workflowList).hasSize(databaseSizeBeforeCreate + 1);
        Workflow testWorkflow = workflowList.get(workflowList.size() - 1);
        assertThat(testWorkflow.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testWorkflow.getValidation()).isEqualTo(DEFAULT_VALIDATION);
        assertThat(testWorkflow.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testWorkflow.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testWorkflow.getLabel()).isEqualTo(DEFAULT_LABEL);
    }

    @Test
    @Transactional
    void createWorkflowWithExistingId() throws Exception {
        // Create the Workflow with an existing ID
        workflow.setId(1L);
        WorkflowDTO workflowDTO = workflowMapper.toDto(workflow);

        int databaseSizeBeforeCreate = workflowRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkflowMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workflowDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Workflow in the database
        List<Workflow> workflowList = workflowRepository.findAll();
        assertThat(workflowList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = workflowRepository.findAll().size();
        // set the field null
        workflow.setStatus(null);

        // Create the Workflow, which fails.
        WorkflowDTO workflowDTO = workflowMapper.toDto(workflow);

        restWorkflowMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workflowDTO)))
            .andExpect(status().isBadRequest());

        List<Workflow> workflowList = workflowRepository.findAll();
        assertThat(workflowList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = workflowRepository.findAll().size();
        // set the field null
        workflow.setDescription(null);

        // Create the Workflow, which fails.
        WorkflowDTO workflowDTO = workflowMapper.toDto(workflow);

        restWorkflowMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workflowDTO)))
            .andExpect(status().isBadRequest());

        List<Workflow> workflowList = workflowRepository.findAll();
        assertThat(workflowList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllWorkflows() throws Exception {
        // Initialize the database
        workflowRepository.saveAndFlush(workflow);

        // Get all the workflowList
        restWorkflowMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workflow.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].validation").value(hasItem(DEFAULT_VALIDATION)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL)));
    }

    @Test
    @Transactional
    void getWorkflow() throws Exception {
        // Initialize the database
        workflowRepository.saveAndFlush(workflow);

        // Get the workflow
        restWorkflowMockMvc
            .perform(get(ENTITY_API_URL_ID, workflow.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(workflow.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.validation").value(DEFAULT_VALIDATION))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL));
    }

    @Test
    @Transactional
    void getNonExistingWorkflow() throws Exception {
        // Get the workflow
        restWorkflowMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingWorkflow() throws Exception {
        // Initialize the database
        workflowRepository.saveAndFlush(workflow);

        int databaseSizeBeforeUpdate = workflowRepository.findAll().size();

        // Update the workflow
        Workflow updatedWorkflow = workflowRepository.findById(workflow.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedWorkflow are not directly saved in db
        em.detach(updatedWorkflow);
        updatedWorkflow
            .status(UPDATED_STATUS)
            .validation(UPDATED_VALIDATION)
            .description(UPDATED_DESCRIPTION)
            .state(UPDATED_STATE)
            .label(UPDATED_LABEL);
        WorkflowDTO workflowDTO = workflowMapper.toDto(updatedWorkflow);

        restWorkflowMockMvc
            .perform(
                put(ENTITY_API_URL_ID, workflowDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workflowDTO))
            )
            .andExpect(status().isOk());

        // Validate the Workflow in the database
        List<Workflow> workflowList = workflowRepository.findAll();
        assertThat(workflowList).hasSize(databaseSizeBeforeUpdate);
        Workflow testWorkflow = workflowList.get(workflowList.size() - 1);
        assertThat(testWorkflow.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testWorkflow.getValidation()).isEqualTo(UPDATED_VALIDATION);
        assertThat(testWorkflow.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testWorkflow.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testWorkflow.getLabel()).isEqualTo(UPDATED_LABEL);
    }

    @Test
    @Transactional
    void putNonExistingWorkflow() throws Exception {
        int databaseSizeBeforeUpdate = workflowRepository.findAll().size();
        workflow.setId(longCount.incrementAndGet());

        // Create the Workflow
        WorkflowDTO workflowDTO = workflowMapper.toDto(workflow);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkflowMockMvc
            .perform(
                put(ENTITY_API_URL_ID, workflowDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workflowDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Workflow in the database
        List<Workflow> workflowList = workflowRepository.findAll();
        assertThat(workflowList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWorkflow() throws Exception {
        int databaseSizeBeforeUpdate = workflowRepository.findAll().size();
        workflow.setId(longCount.incrementAndGet());

        // Create the Workflow
        WorkflowDTO workflowDTO = workflowMapper.toDto(workflow);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkflowMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workflowDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Workflow in the database
        List<Workflow> workflowList = workflowRepository.findAll();
        assertThat(workflowList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWorkflow() throws Exception {
        int databaseSizeBeforeUpdate = workflowRepository.findAll().size();
        workflow.setId(longCount.incrementAndGet());

        // Create the Workflow
        WorkflowDTO workflowDTO = workflowMapper.toDto(workflow);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkflowMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workflowDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Workflow in the database
        List<Workflow> workflowList = workflowRepository.findAll();
        assertThat(workflowList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWorkflowWithPatch() throws Exception {
        // Initialize the database
        workflowRepository.saveAndFlush(workflow);

        int databaseSizeBeforeUpdate = workflowRepository.findAll().size();

        // Update the workflow using partial update
        Workflow partialUpdatedWorkflow = new Workflow();
        partialUpdatedWorkflow.setId(workflow.getId());

        partialUpdatedWorkflow.validation(UPDATED_VALIDATION).description(UPDATED_DESCRIPTION);

        restWorkflowMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWorkflow.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorkflow))
            )
            .andExpect(status().isOk());

        // Validate the Workflow in the database
        List<Workflow> workflowList = workflowRepository.findAll();
        assertThat(workflowList).hasSize(databaseSizeBeforeUpdate);
        Workflow testWorkflow = workflowList.get(workflowList.size() - 1);
        assertThat(testWorkflow.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testWorkflow.getValidation()).isEqualTo(UPDATED_VALIDATION);
        assertThat(testWorkflow.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testWorkflow.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testWorkflow.getLabel()).isEqualTo(DEFAULT_LABEL);
    }

    @Test
    @Transactional
    void fullUpdateWorkflowWithPatch() throws Exception {
        // Initialize the database
        workflowRepository.saveAndFlush(workflow);

        int databaseSizeBeforeUpdate = workflowRepository.findAll().size();

        // Update the workflow using partial update
        Workflow partialUpdatedWorkflow = new Workflow();
        partialUpdatedWorkflow.setId(workflow.getId());

        partialUpdatedWorkflow
            .status(UPDATED_STATUS)
            .validation(UPDATED_VALIDATION)
            .description(UPDATED_DESCRIPTION)
            .state(UPDATED_STATE)
            .label(UPDATED_LABEL);

        restWorkflowMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWorkflow.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorkflow))
            )
            .andExpect(status().isOk());

        // Validate the Workflow in the database
        List<Workflow> workflowList = workflowRepository.findAll();
        assertThat(workflowList).hasSize(databaseSizeBeforeUpdate);
        Workflow testWorkflow = workflowList.get(workflowList.size() - 1);
        assertThat(testWorkflow.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testWorkflow.getValidation()).isEqualTo(UPDATED_VALIDATION);
        assertThat(testWorkflow.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testWorkflow.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testWorkflow.getLabel()).isEqualTo(UPDATED_LABEL);
    }

    @Test
    @Transactional
    void patchNonExistingWorkflow() throws Exception {
        int databaseSizeBeforeUpdate = workflowRepository.findAll().size();
        workflow.setId(longCount.incrementAndGet());

        // Create the Workflow
        WorkflowDTO workflowDTO = workflowMapper.toDto(workflow);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkflowMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, workflowDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workflowDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Workflow in the database
        List<Workflow> workflowList = workflowRepository.findAll();
        assertThat(workflowList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWorkflow() throws Exception {
        int databaseSizeBeforeUpdate = workflowRepository.findAll().size();
        workflow.setId(longCount.incrementAndGet());

        // Create the Workflow
        WorkflowDTO workflowDTO = workflowMapper.toDto(workflow);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkflowMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workflowDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Workflow in the database
        List<Workflow> workflowList = workflowRepository.findAll();
        assertThat(workflowList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWorkflow() throws Exception {
        int databaseSizeBeforeUpdate = workflowRepository.findAll().size();
        workflow.setId(longCount.incrementAndGet());

        // Create the Workflow
        WorkflowDTO workflowDTO = workflowMapper.toDto(workflow);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkflowMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(workflowDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Workflow in the database
        List<Workflow> workflowList = workflowRepository.findAll();
        assertThat(workflowList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWorkflow() throws Exception {
        // Initialize the database
        workflowRepository.saveAndFlush(workflow);

        int databaseSizeBeforeDelete = workflowRepository.findAll().size();

        // Delete the workflow
        restWorkflowMockMvc
            .perform(delete(ENTITY_API_URL_ID, workflow.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Workflow> workflowList = workflowRepository.findAll();
        assertThat(workflowList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
