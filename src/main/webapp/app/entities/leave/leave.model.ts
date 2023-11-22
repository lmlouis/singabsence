import { IWorkflow } from 'app/entities/workflow/workflow.model';
import { IAttachment } from 'app/entities/attachment/attachment.model';
import { IEmployee } from 'app/entities/employee/employee.model';
import { LeaveType } from 'app/entities/enumerations/leave-type.model';

export interface ILeave {
  id: number;
  type?: keyof typeof LeaveType | null;
  reason?: string | null;
  workflow?: Pick<IWorkflow, 'id'> | null;
  attachment?: Pick<IAttachment, 'id'> | null;
  sentto?: Pick<IEmployee, 'id'> | null;
  requestedbies?: Pick<IEmployee, 'id'>[] | null;
}

export type NewLeave = Omit<ILeave, 'id'> & { id: null };
