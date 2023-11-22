package com.sing.singabsence.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sing.singabsence.IntegrationTest;
import com.sing.singabsence.domain.Leave;
import com.sing.singabsence.domain.enumeration.LeaveType;
import com.sing.singabsence.repository.LeaveRepository;
import com.sing.singabsence.service.dto.LeaveDTO;
import com.sing.singabsence.service.mapper.LeaveMapper;
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
 * Integration tests for the {@link LeaveResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LeaveResourceIT {

    private static final LeaveType DEFAULT_TYPE = LeaveType.PAID_LEAVE;
    private static final LeaveType UPDATED_TYPE = LeaveType.UNPAID_LEAVE;

    private static final String DEFAULT_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REASON = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/leaves";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LeaveRepository leaveRepository;

    @Autowired
    private LeaveMapper leaveMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLeaveMockMvc;

    private Leave leave;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Leave createEntity(EntityManager em) {
        Leave leave = new Leave().type(DEFAULT_TYPE).reason(DEFAULT_REASON);
        return leave;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Leave createUpdatedEntity(EntityManager em) {
        Leave leave = new Leave().type(UPDATED_TYPE).reason(UPDATED_REASON);
        return leave;
    }

    @BeforeEach
    public void initTest() {
        leave = createEntity(em);
    }

    @Test
    @Transactional
    void createLeave() throws Exception {
        int databaseSizeBeforeCreate = leaveRepository.findAll().size();
        // Create the Leave
        LeaveDTO leaveDTO = leaveMapper.toDto(leave);
        restLeaveMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(leaveDTO)))
            .andExpect(status().isCreated());

        // Validate the Leave in the database
        List<Leave> leaveList = leaveRepository.findAll();
        assertThat(leaveList).hasSize(databaseSizeBeforeCreate + 1);
        Leave testLeave = leaveList.get(leaveList.size() - 1);
        assertThat(testLeave.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testLeave.getReason()).isEqualTo(DEFAULT_REASON);
    }

    @Test
    @Transactional
    void createLeaveWithExistingId() throws Exception {
        // Create the Leave with an existing ID
        leave.setId(1L);
        LeaveDTO leaveDTO = leaveMapper.toDto(leave);

        int databaseSizeBeforeCreate = leaveRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeaveMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(leaveDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Leave in the database
        List<Leave> leaveList = leaveRepository.findAll();
        assertThat(leaveList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveRepository.findAll().size();
        // set the field null
        leave.setType(null);

        // Create the Leave, which fails.
        LeaveDTO leaveDTO = leaveMapper.toDto(leave);

        restLeaveMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(leaveDTO)))
            .andExpect(status().isBadRequest());

        List<Leave> leaveList = leaveRepository.findAll();
        assertThat(leaveList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkReasonIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveRepository.findAll().size();
        // set the field null
        leave.setReason(null);

        // Create the Leave, which fails.
        LeaveDTO leaveDTO = leaveMapper.toDto(leave);

        restLeaveMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(leaveDTO)))
            .andExpect(status().isBadRequest());

        List<Leave> leaveList = leaveRepository.findAll();
        assertThat(leaveList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLeaves() throws Exception {
        // Initialize the database
        leaveRepository.saveAndFlush(leave);

        // Get all the leaveList
        restLeaveMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leave.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON)));
    }

    @Test
    @Transactional
    void getLeave() throws Exception {
        // Initialize the database
        leaveRepository.saveAndFlush(leave);

        // Get the leave
        restLeaveMockMvc
            .perform(get(ENTITY_API_URL_ID, leave.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(leave.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON));
    }

    @Test
    @Transactional
    void getNonExistingLeave() throws Exception {
        // Get the leave
        restLeaveMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLeave() throws Exception {
        // Initialize the database
        leaveRepository.saveAndFlush(leave);

        int databaseSizeBeforeUpdate = leaveRepository.findAll().size();

        // Update the leave
        Leave updatedLeave = leaveRepository.findById(leave.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedLeave are not directly saved in db
        em.detach(updatedLeave);
        updatedLeave.type(UPDATED_TYPE).reason(UPDATED_REASON);
        LeaveDTO leaveDTO = leaveMapper.toDto(updatedLeave);

        restLeaveMockMvc
            .perform(
                put(ENTITY_API_URL_ID, leaveDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveDTO))
            )
            .andExpect(status().isOk());

        // Validate the Leave in the database
        List<Leave> leaveList = leaveRepository.findAll();
        assertThat(leaveList).hasSize(databaseSizeBeforeUpdate);
        Leave testLeave = leaveList.get(leaveList.size() - 1);
        assertThat(testLeave.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testLeave.getReason()).isEqualTo(UPDATED_REASON);
    }

    @Test
    @Transactional
    void putNonExistingLeave() throws Exception {
        int databaseSizeBeforeUpdate = leaveRepository.findAll().size();
        leave.setId(longCount.incrementAndGet());

        // Create the Leave
        LeaveDTO leaveDTO = leaveMapper.toDto(leave);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeaveMockMvc
            .perform(
                put(ENTITY_API_URL_ID, leaveDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Leave in the database
        List<Leave> leaveList = leaveRepository.findAll();
        assertThat(leaveList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLeave() throws Exception {
        int databaseSizeBeforeUpdate = leaveRepository.findAll().size();
        leave.setId(longCount.incrementAndGet());

        // Create the Leave
        LeaveDTO leaveDTO = leaveMapper.toDto(leave);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Leave in the database
        List<Leave> leaveList = leaveRepository.findAll();
        assertThat(leaveList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLeave() throws Exception {
        int databaseSizeBeforeUpdate = leaveRepository.findAll().size();
        leave.setId(longCount.incrementAndGet());

        // Create the Leave
        LeaveDTO leaveDTO = leaveMapper.toDto(leave);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(leaveDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Leave in the database
        List<Leave> leaveList = leaveRepository.findAll();
        assertThat(leaveList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLeaveWithPatch() throws Exception {
        // Initialize the database
        leaveRepository.saveAndFlush(leave);

        int databaseSizeBeforeUpdate = leaveRepository.findAll().size();

        // Update the leave using partial update
        Leave partialUpdatedLeave = new Leave();
        partialUpdatedLeave.setId(leave.getId());

        partialUpdatedLeave.type(UPDATED_TYPE);

        restLeaveMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeave.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeave))
            )
            .andExpect(status().isOk());

        // Validate the Leave in the database
        List<Leave> leaveList = leaveRepository.findAll();
        assertThat(leaveList).hasSize(databaseSizeBeforeUpdate);
        Leave testLeave = leaveList.get(leaveList.size() - 1);
        assertThat(testLeave.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testLeave.getReason()).isEqualTo(DEFAULT_REASON);
    }

    @Test
    @Transactional
    void fullUpdateLeaveWithPatch() throws Exception {
        // Initialize the database
        leaveRepository.saveAndFlush(leave);

        int databaseSizeBeforeUpdate = leaveRepository.findAll().size();

        // Update the leave using partial update
        Leave partialUpdatedLeave = new Leave();
        partialUpdatedLeave.setId(leave.getId());

        partialUpdatedLeave.type(UPDATED_TYPE).reason(UPDATED_REASON);

        restLeaveMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeave.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeave))
            )
            .andExpect(status().isOk());

        // Validate the Leave in the database
        List<Leave> leaveList = leaveRepository.findAll();
        assertThat(leaveList).hasSize(databaseSizeBeforeUpdate);
        Leave testLeave = leaveList.get(leaveList.size() - 1);
        assertThat(testLeave.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testLeave.getReason()).isEqualTo(UPDATED_REASON);
    }

    @Test
    @Transactional
    void patchNonExistingLeave() throws Exception {
        int databaseSizeBeforeUpdate = leaveRepository.findAll().size();
        leave.setId(longCount.incrementAndGet());

        // Create the Leave
        LeaveDTO leaveDTO = leaveMapper.toDto(leave);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeaveMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, leaveDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaveDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Leave in the database
        List<Leave> leaveList = leaveRepository.findAll();
        assertThat(leaveList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLeave() throws Exception {
        int databaseSizeBeforeUpdate = leaveRepository.findAll().size();
        leave.setId(longCount.incrementAndGet());

        // Create the Leave
        LeaveDTO leaveDTO = leaveMapper.toDto(leave);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaveDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Leave in the database
        List<Leave> leaveList = leaveRepository.findAll();
        assertThat(leaveList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLeave() throws Exception {
        int databaseSizeBeforeUpdate = leaveRepository.findAll().size();
        leave.setId(longCount.incrementAndGet());

        // Create the Leave
        LeaveDTO leaveDTO = leaveMapper.toDto(leave);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(leaveDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Leave in the database
        List<Leave> leaveList = leaveRepository.findAll();
        assertThat(leaveList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLeave() throws Exception {
        // Initialize the database
        leaveRepository.saveAndFlush(leave);

        int databaseSizeBeforeDelete = leaveRepository.findAll().size();

        // Delete the leave
        restLeaveMockMvc
            .perform(delete(ENTITY_API_URL_ID, leave.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Leave> leaveList = leaveRepository.findAll();
        assertThat(leaveList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
