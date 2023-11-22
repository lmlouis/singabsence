package com.sing.singabsence.service.impl;

import com.sing.singabsence.domain.Employee;
import com.sing.singabsence.repository.EmployeeRepository;
import com.sing.singabsence.service.EmployeeService;
import com.sing.singabsence.service.dto.EmployeeDTO;
import com.sing.singabsence.service.mapper.EmployeeMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.sing.singabsence.domain.Employee}.
 */
@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private final EmployeeRepository employeeRepository;

    private final EmployeeMapper employeeMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public EmployeeDTO save(EmployeeDTO employeeDTO) {
        log.debug("Request to save Employee : {}", employeeDTO);
        Employee employee = employeeMapper.toEntity(employeeDTO);
        employee = employeeRepository.save(employee);
        return employeeMapper.toDto(employee);
    }

    @Override
    public EmployeeDTO update(EmployeeDTO employeeDTO) {
        log.debug("Request to update Employee : {}", employeeDTO);
        Employee employee = employeeMapper.toEntity(employeeDTO);
        employee = employeeRepository.save(employee);
        return employeeMapper.toDto(employee);
    }

    @Override
    public Optional<EmployeeDTO> partialUpdate(EmployeeDTO employeeDTO) {
        log.debug("Request to partially update Employee : {}", employeeDTO);

        return employeeRepository
            .findById(employeeDTO.getId())
            .map(existingEmployee -> {
                employeeMapper.partialUpdate(existingEmployee, employeeDTO);

                return existingEmployee;
            })
            .map(employeeRepository::save)
            .map(employeeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeDTO> findAll() {
        log.debug("Request to get all Employees");
        return employeeRepository.findAll().stream().map(employeeMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    public Page<EmployeeDTO> findAllWithEagerRelationships(Pageable pageable) {
        return employeeRepository.findAllWithEagerRelationships(pageable).map(employeeMapper::toDto);
    }

    /**
     *  Get all the employees where Ownenterprise is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<EmployeeDTO> findAllWhereOwnenterpriseIsNull() {
        log.debug("Request to get all employees where Ownenterprise is null");
        return StreamSupport
            .stream(employeeRepository.findAll().spliterator(), false)
            .filter(employee -> employee.getOwnenterprise() == null)
            .map(employeeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the employees where Ownteam is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<EmployeeDTO> findAllWhereOwnteamIsNull() {
        log.debug("Request to get all employees where Ownteam is null");
        return StreamSupport
            .stream(employeeRepository.findAll().spliterator(), false)
            .filter(employee -> employee.getOwnteam() == null)
            .map(employeeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the employees where Ownmessage is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<EmployeeDTO> findAllWhereOwnmessageIsNull() {
        log.debug("Request to get all employees where Ownmessage is null");
        return StreamSupport
            .stream(employeeRepository.findAll().spliterator(), false)
            .filter(employee -> employee.getOwnmessage() == null)
            .map(employeeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the employees where Request is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<EmployeeDTO> findAllWhereRequestIsNull() {
        log.debug("Request to get all employees where Request is null");
        return StreamSupport
            .stream(employeeRepository.findAll().spliterator(), false)
            .filter(employee -> employee.getRequest() == null)
            .map(employeeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EmployeeDTO> findOne(Long id) {
        log.debug("Request to get Employee : {}", id);
        return employeeRepository.findOneWithEagerRelationships(id).map(employeeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Employee : {}", id);
        employeeRepository.deleteById(id);
    }
}
