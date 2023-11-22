package com.sing.singabsence.service.mapper;

import com.sing.singabsence.domain.Employee;
import com.sing.singabsence.domain.Organization;
import com.sing.singabsence.domain.Team;
import com.sing.singabsence.service.dto.EmployeeDTO;
import com.sing.singabsence.service.dto.OrganizationDTO;
import com.sing.singabsence.service.dto.TeamDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Organization} and its DTO {@link OrganizationDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrganizationMapper extends EntityMapper<OrganizationDTO, Organization> {
    @Mapping(target = "director", source = "director", qualifiedByName = "employeeId")
    @Mapping(target = "units", source = "units", qualifiedByName = "teamIdSet")
    OrganizationDTO toDto(Organization s);

    @Mapping(target = "removeUnits", ignore = true)
    Organization toEntity(OrganizationDTO organizationDTO);

    @Named("employeeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmployeeDTO toDtoEmployeeId(Employee employee);

    @Named("teamId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TeamDTO toDtoTeamId(Team team);

    @Named("teamIdSet")
    default Set<TeamDTO> toDtoTeamIdSet(Set<Team> team) {
        return team.stream().map(this::toDtoTeamId).collect(Collectors.toSet());
    }
}
