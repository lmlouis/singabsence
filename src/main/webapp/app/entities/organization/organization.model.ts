import { IEmployee } from 'app/entities/employee/employee.model';
import { ITeam } from 'app/entities/team/team.model';

export interface IOrganization {
  id: number;
  name?: string | null;
  logo?: string | null;
  logoContentType?: string | null;
  website?: string | null;
  domain?: string | null;
  phone?: string | null;
  director?: Pick<IEmployee, 'id'> | null;
  units?: Pick<ITeam, 'id'>[] | null;
}

export type NewOrganization = Omit<IOrganization, 'id'> & { id: null };
