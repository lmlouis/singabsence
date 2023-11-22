package com.sing.singabsence.service.impl;

import com.sing.singabsence.domain.Leave;
import com.sing.singabsence.repository.LeaveRepository;
import com.sing.singabsence.service.LeaveService;
import com.sing.singabsence.service.dto.LeaveDTO;
import com.sing.singabsence.service.mapper.LeaveMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.sing.singabsence.domain.Leave}.
 */
@Service
@Transactional
public class LeaveServiceImpl implements LeaveService {

    private final Logger log = LoggerFactory.getLogger(LeaveServiceImpl.class);

    private final LeaveRepository leaveRepository;

    private final LeaveMapper leaveMapper;

    public LeaveServiceImpl(LeaveRepository leaveRepository, LeaveMapper leaveMapper) {
        this.leaveRepository = leaveRepository;
        this.leaveMapper = leaveMapper;
    }

    @Override
    public LeaveDTO save(LeaveDTO leaveDTO) {
        log.debug("Request to save Leave : {}", leaveDTO);
        Leave leave = leaveMapper.toEntity(leaveDTO);
        leave = leaveRepository.save(leave);
        return leaveMapper.toDto(leave);
    }

    @Override
    public LeaveDTO update(LeaveDTO leaveDTO) {
        log.debug("Request to update Leave : {}", leaveDTO);
        Leave leave = leaveMapper.toEntity(leaveDTO);
        leave = leaveRepository.save(leave);
        return leaveMapper.toDto(leave);
    }

    @Override
    public Optional<LeaveDTO> partialUpdate(LeaveDTO leaveDTO) {
        log.debug("Request to partially update Leave : {}", leaveDTO);

        return leaveRepository
            .findById(leaveDTO.getId())
            .map(existingLeave -> {
                leaveMapper.partialUpdate(existingLeave, leaveDTO);

                return existingLeave;
            })
            .map(leaveRepository::save)
            .map(leaveMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LeaveDTO> findAll() {
        log.debug("Request to get all Leaves");
        return leaveRepository.findAll().stream().map(leaveMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LeaveDTO> findOne(Long id) {
        log.debug("Request to get Leave : {}", id);
        return leaveRepository.findById(id).map(leaveMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Leave : {}", id);
        leaveRepository.deleteById(id);
    }
}
