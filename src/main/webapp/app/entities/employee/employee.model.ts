import dayjs from 'dayjs/esm';
import { IUser } from 'app/entities/user/user.model';
import { ICalendar } from 'app/entities/calendar/calendar.model';
import { ILeave } from 'app/entities/leave/leave.model';
import { ITeam } from 'app/entities/team/team.model';
import { IMessage } from 'app/entities/message/message.model';

export interface IEmployee {
  id: number;
  fullname?: string | null;
  phone?: string | null;
  position?: string | null;
  location?: string | null;
  birthday?: dayjs.Dayjs | null;
  authenticateby?: Pick<IUser, 'id' | 'login'> | null;
  calendar?: Pick<ICalendar, 'id'> | null;
  leaves?: Pick<ILeave, 'id'>[] | null;
  service?: Pick<ITeam, 'id'> | null;
  inboxes?: Pick<IMessage, 'id'>[] | null;
  teams?: Pick<ITeam, 'id'>[] | null;
  requests?: Pick<ILeave, 'id'>[] | null;
}

export type NewEmployee = Omit<IEmployee, 'id'> & { id: null };
