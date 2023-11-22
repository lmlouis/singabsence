import { LeaveStatus } from 'app/entities/enumerations/leave-status.model';

export interface IWorkflow {
  id: number;
  status?: keyof typeof LeaveStatus | null;
  validation?: string | null;
  description?: string | null;
  state?: number | null;
  label?: string | null;
}

export type NewWorkflow = Omit<IWorkflow, 'id'> & { id: null };
