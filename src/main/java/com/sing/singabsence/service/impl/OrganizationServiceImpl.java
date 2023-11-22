package com.sing.singabsence.service.impl;

import com.sing.singabsence.domain.Organization;
import com.sing.singabsence.repository.OrganizationRepository;
import com.sing.singabsence.service.OrganizationService;
import com.sing.singabsence.service.dto.OrganizationDTO;
import com.sing.singabsence.service.mapper.OrganizationMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.sing.singabsence.domain.Organization}.
 */
@Service
@Transactional
public class OrganizationServiceImpl implements OrganizationService {

    private final Logger log = LoggerFactory.getLogger(OrganizationServiceImpl.class);

    private final OrganizationRepository organizationRepository;

    private final OrganizationMapper organizationMapper;

    public OrganizationServiceImpl(OrganizationRepository organizationRepository, OrganizationMapper organizationMapper) {
        this.organizationRepository = organizationRepository;
        this.organizationMapper = organizationMapper;
    }

    @Override
    public OrganizationDTO save(OrganizationDTO organizationDTO) {
        log.debug("Request to save Organization : {}", organizationDTO);
        Organization organization = organizationMapper.toEntity(organizationDTO);
        organization = organizationRepository.save(organization);
        return organizationMapper.toDto(organization);
    }

    @Override
    public OrganizationDTO update(OrganizationDTO organizationDTO) {
        log.debug("Request to update Organization : {}", organizationDTO);
        Organization organization = organizationMapper.toEntity(organizationDTO);
        organization = organizationRepository.save(organization);
        return organizationMapper.toDto(organization);
    }

    @Override
    public Optional<OrganizationDTO> partialUpdate(OrganizationDTO organizationDTO) {
        log.debug("Request to partially update Organization : {}", organizationDTO);

        return organizationRepository
            .findById(organizationDTO.getId())
            .map(existingOrganization -> {
                organizationMapper.partialUpdate(existingOrganization, organizationDTO);

                return existingOrganization;
            })
            .map(organizationRepository::save)
            .map(organizationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrganizationDTO> findAll() {
        log.debug("Request to get all Organizations");
        return organizationRepository.findAll().stream().map(organizationMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    public Page<OrganizationDTO> findAllWithEagerRelationships(Pageable pageable) {
        return organizationRepository.findAllWithEagerRelationships(pageable).map(organizationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrganizationDTO> findOne(Long id) {
        log.debug("Request to get Organization : {}", id);
        return organizationRepository.findOneWithEagerRelationships(id).map(organizationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Organization : {}", id);
        organizationRepository.deleteById(id);
    }
}
