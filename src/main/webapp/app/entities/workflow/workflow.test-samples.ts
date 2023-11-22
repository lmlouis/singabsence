import { IWorkflow, NewWorkflow } from './workflow.model';

export const sampleWithRequiredData: IWorkflow = {
  id: 9494,
  status: 'APPROVED',
  description: 'cocorico gratis paf',
};

export const sampleWithPartialData: IWorkflow = {
  id: 10674,
  status: 'APPROVED',
  description: 'fidèle vlan',
};

export const sampleWithFullData: IWorkflow = {
  id: 9054,
  status: 'APPROVED',
  description: 'étaler',
};

export const sampleWithNewData: NewWorkflow = {
  status: 'REJECTED',
  description: 'divinement bè',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
