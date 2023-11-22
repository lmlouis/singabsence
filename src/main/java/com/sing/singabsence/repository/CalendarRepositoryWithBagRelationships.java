package com.sing.singabsence.repository;

import com.sing.singabsence.domain.Calendar;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface CalendarRepositoryWithBagRelationships {
    Optional<Calendar> fetchBagRelationships(Optional<Calendar> calendar);

    List<Calendar> fetchBagRelationships(List<Calendar> calendars);

    Page<Calendar> fetchBagRelationships(Page<Calendar> calendars);
}
