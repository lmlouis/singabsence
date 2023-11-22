import { IMessage } from 'app/entities/message/message.model';
import { ILeave } from 'app/entities/leave/leave.model';
import { LeaveStatus } from 'app/entities/enumerations/leave-status.model';

export interface IWorkflow {
  id: number;
  status?: keyof typeof LeaveStatus | null;
  description?: string | null;
  validation?: Pick<IMessage, 'id'> | null;
  request?: Pick<ILeave, 'id'> | null;
}

export type NewWorkflow = Omit<IWorkflow, 'id'> & { id: null };
