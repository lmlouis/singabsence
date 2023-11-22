import { IEmployee } from 'app/entities/employee/employee.model';
import { IMessage } from 'app/entities/message/message.model';
import { TypeOfAttachment } from 'app/entities/enumerations/type-of-attachment.model';

export interface IAttachment {
  id: number;
  name?: string | null;
  category?: keyof typeof TypeOfAttachment | null;
  picture?: string | null;
  pictureContentType?: string | null;
  document?: string | null;
  documentContentType?: string | null;
  owner?: Pick<IEmployee, 'id'> | null;
  msgs?: Pick<IMessage, 'id'>[] | null;
}

export type NewAttachment = Omit<IAttachment, 'id'> & { id: null };
