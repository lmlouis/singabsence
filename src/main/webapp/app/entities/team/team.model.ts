import { IEmployee } from 'app/entities/employee/employee.model';
import { ICalendar } from 'app/entities/calendar/calendar.model';
import { IOrganization } from 'app/entities/organization/organization.model';

export interface ITeam {
  id: number;
  name?: string | null;
  lead?: Pick<IEmployee, 'id'> | null;
  planing?: Pick<ICalendar, 'id'> | null;
  members?: Pick<IEmployee, 'id'>[] | null;
  organizations?: Pick<IOrganization, 'id'>[] | null;
}

export type NewTeam = Omit<ITeam, 'id'> & { id: null };
