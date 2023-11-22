import dayjs from 'dayjs/esm';
import { IEvent } from 'app/entities/event/event.model';

export interface ICalendar {
  id: number;
  title?: string | null;
  summury?: string | null;
  createdat?: dayjs.Dayjs | null;
  events?: Pick<IEvent, 'id'>[] | null;
}

export type NewCalendar = Omit<ICalendar, 'id'> & { id: null };
