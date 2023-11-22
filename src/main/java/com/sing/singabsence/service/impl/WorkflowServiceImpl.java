package com.sing.singabsence.service.impl;

import com.sing.singabsence.domain.Workflow;
import com.sing.singabsence.repository.WorkflowRepository;
import com.sing.singabsence.service.WorkflowService;
import com.sing.singabsence.service.dto.WorkflowDTO;
import com.sing.singabsence.service.mapper.WorkflowMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.sing.singabsence.domain.Workflow}.
 */
@Service
@Transactional
public class WorkflowServiceImpl implements WorkflowService {

    private final Logger log = LoggerFactory.getLogger(WorkflowServiceImpl.class);

    private final WorkflowRepository workflowRepository;

    private final WorkflowMapper workflowMapper;

    public WorkflowServiceImpl(WorkflowRepository workflowRepository, WorkflowMapper workflowMapper) {
        this.workflowRepository = workflowRepository;
        this.workflowMapper = workflowMapper;
    }

    @Override
    public WorkflowDTO save(WorkflowDTO workflowDTO) {
        log.debug("Request to save Workflow : {}", workflowDTO);
        Workflow workflow = workflowMapper.toEntity(workflowDTO);
        workflow = workflowRepository.save(workflow);
        return workflowMapper.toDto(workflow);
    }

    @Override
    public WorkflowDTO update(WorkflowDTO workflowDTO) {
        log.debug("Request to update Workflow : {}", workflowDTO);
        Workflow workflow = workflowMapper.toEntity(workflowDTO);
        workflow = workflowRepository.save(workflow);
        return workflowMapper.toDto(workflow);
    }

    @Override
    public Optional<WorkflowDTO> partialUpdate(WorkflowDTO workflowDTO) {
        log.debug("Request to partially update Workflow : {}", workflowDTO);

        return workflowRepository
            .findById(workflowDTO.getId())
            .map(existingWorkflow -> {
                workflowMapper.partialUpdate(existingWorkflow, workflowDTO);

                return existingWorkflow;
            })
            .map(workflowRepository::save)
            .map(workflowMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkflowDTO> findAll() {
        log.debug("Request to get all Workflows");
        return workflowRepository.findAll().stream().map(workflowMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WorkflowDTO> findOne(Long id) {
        log.debug("Request to get Workflow : {}", id);
        return workflowRepository.findById(id).map(workflowMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Workflow : {}", id);
        workflowRepository.deleteById(id);
    }
}
