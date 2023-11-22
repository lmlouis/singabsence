import dayjs from 'dayjs/esm';
import { IEmployee } from 'app/entities/employee/employee.model';
import { IAttachment } from 'app/entities/attachment/attachment.model';

export interface IMessage {
  id: number;
  purpose?: string | null;
  content?: string | null;
  sentat?: dayjs.Dayjs | null;
  isread?: boolean | null;
  from?: Pick<IEmployee, 'id'> | null;
  attachements?: Pick<IAttachment, 'id'>[] | null;
  tos?: Pick<IEmployee, 'id'>[] | null;
}

export type NewMessage = Omit<IMessage, 'id'> & { id: null };
