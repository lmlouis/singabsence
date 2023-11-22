import dayjs from 'dayjs/esm';
import { IEmployee } from 'app/entities/employee/employee.model';
import { ICalendar } from 'app/entities/calendar/calendar.model';

export interface IEvent {
  id: number;
  title?: string | null;
  start?: dayjs.Dayjs | null;
  end?: dayjs.Dayjs | null;
  link?: string | null;
  creator?: Pick<IEmployee, 'id'> | null;
  owncalendars?: Pick<ICalendar, 'id'>[] | null;
}

export type NewEvent = Omit<IEvent, 'id'> & { id: null };
