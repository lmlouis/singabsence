import { ILeave, NewLeave } from './leave.model';

export const sampleWithRequiredData: ILeave = {
  id: 26944,
  type: 'CIVIC_SERVICE_LEAVE',
};

export const sampleWithPartialData: ILeave = {
  id: 7879,
  type: 'PAID_LEAVE',
};

export const sampleWithFullData: ILeave = {
  id: 16612,
  type: 'FAMILY_EVENTS_LEAVE',
};

export const sampleWithNewData: NewLeave = {
  type: 'CIVIC_SERVICE_LEAVE',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
