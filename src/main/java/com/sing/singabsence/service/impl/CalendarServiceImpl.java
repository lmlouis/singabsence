package com.sing.singabsence.service.impl;

import com.sing.singabsence.domain.Calendar;
import com.sing.singabsence.repository.CalendarRepository;
import com.sing.singabsence.service.CalendarService;
import com.sing.singabsence.service.dto.CalendarDTO;
import com.sing.singabsence.service.mapper.CalendarMapper;
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
 * Service Implementation for managing {@link com.sing.singabsence.domain.Calendar}.
 */
@Service
@Transactional
public class CalendarServiceImpl implements CalendarService {

    private final Logger log = LoggerFactory.getLogger(CalendarServiceImpl.class);

    private final CalendarRepository calendarRepository;

    private final CalendarMapper calendarMapper;

    public CalendarServiceImpl(CalendarRepository calendarRepository, CalendarMapper calendarMapper) {
        this.calendarRepository = calendarRepository;
        this.calendarMapper = calendarMapper;
    }

    @Override
    public CalendarDTO save(CalendarDTO calendarDTO) {
        log.debug("Request to save Calendar : {}", calendarDTO);
        Calendar calendar = calendarMapper.toEntity(calendarDTO);
        calendar = calendarRepository.save(calendar);
        return calendarMapper.toDto(calendar);
    }

    @Override
    public CalendarDTO update(CalendarDTO calendarDTO) {
        log.debug("Request to update Calendar : {}", calendarDTO);
        Calendar calendar = calendarMapper.toEntity(calendarDTO);
        calendar = calendarRepository.save(calendar);
        return calendarMapper.toDto(calendar);
    }

    @Override
    public Optional<CalendarDTO> partialUpdate(CalendarDTO calendarDTO) {
        log.debug("Request to partially update Calendar : {}", calendarDTO);

        return calendarRepository
            .findById(calendarDTO.getId())
            .map(existingCalendar -> {
                calendarMapper.partialUpdate(existingCalendar, calendarDTO);

                return existingCalendar;
            })
            .map(calendarRepository::save)
            .map(calendarMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CalendarDTO> findAll() {
        log.debug("Request to get all Calendars");
        return calendarRepository.findAll().stream().map(calendarMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    public Page<CalendarDTO> findAllWithEagerRelationships(Pageable pageable) {
        return calendarRepository.findAllWithEagerRelationships(pageable).map(calendarMapper::toDto);
    }

    /**
     *  Get all the calendars where Owner is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CalendarDTO> findAllWhereOwnerIsNull() {
        log.debug("Request to get all calendars where Owner is null");
        return StreamSupport
            .stream(calendarRepository.findAll().spliterator(), false)
            .filter(calendar -> calendar.getOwner() == null)
            .map(calendarMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the calendars where Ownteam is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CalendarDTO> findAllWhereOwnteamIsNull() {
        log.debug("Request to get all calendars where Ownteam is null");
        return StreamSupport
            .stream(calendarRepository.findAll().spliterator(), false)
            .filter(calendar -> calendar.getOwnteam() == null)
            .map(calendarMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CalendarDTO> findOne(Long id) {
        log.debug("Request to get Calendar : {}", id);
        return calendarRepository.findOneWithEagerRelationships(id).map(calendarMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Calendar : {}", id);
        calendarRepository.deleteById(id);
    }
}
