import { IWorkflow, NewWorkflow } from './workflow.model';

export const sampleWithRequiredData: IWorkflow = {
  id: 6083,
  status: 'PENDING',
  description: 'aigre aïe',
};

export const sampleWithPartialData: IWorkflow = {
  id: 22222,
  status: 'PENDING',
  validation: "bientôt à l'instar de",
  description: 'conseil municipal sursauter parce que',
  state: 17267,
};

export const sampleWithFullData: IWorkflow = {
  id: 8174,
  status: 'APPROVED',
  validation: 'avant-hier pendant que',
  description: 'ainsi juriste gens',
  state: 27775,
  label: 'si bien que',
};

export const sampleWithNewData: NewWorkflow = {
  status: 'APPROVED',
  description: 'conseil municipal',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
