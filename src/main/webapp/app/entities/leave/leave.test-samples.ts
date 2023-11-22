import { ILeave, NewLeave } from './leave.model';

export const sampleWithRequiredData: ILeave = {
  id: 29163,
  type: 'MATERNITY_PATERNITY_LEAVE',
  reason: 'arrière',
};

export const sampleWithPartialData: ILeave = {
  id: 28961,
  type: 'COMPENSATORY_LEAVE',
  reason: 'tant que',
};

export const sampleWithFullData: ILeave = {
  id: 29826,
  type: 'BLOOD_DONATION_LEAVE',
  reason: 'méditer',
};

export const sampleWithNewData: NewLeave = {
  type: 'PAID_LEAVE',
  reason: 'jusqu’à ce que juriste',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
