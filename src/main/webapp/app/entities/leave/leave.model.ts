import { IEmployee } from 'app/entities/employee/employee.model';
import { IWorkflow } from 'app/entities/workflow/workflow.model';
import { IMessage } from 'app/entities/message/message.model';
import { IEvent } from 'app/entities/event/event.model';
import { LeaveType } from 'app/entities/enumerations/leave-type.model';

export interface ILeave {
  id: number;
  type?: keyof typeof LeaveType | null;
  owner?: Pick<IEmployee, 'id'> | null;
  workflow?: Pick<IWorkflow, 'id'> | null;
  reason?: Pick<IMessage, 'id'> | null;
  period?: Pick<IEvent, 'id'> | null;
  senttos?: Pick<IEmployee, 'id'>[] | null;
  requestedbies?: Pick<IEmployee, 'id'>[] | null;
}

export type NewLeave = Omit<ILeave, 'id'> & { id: null };
