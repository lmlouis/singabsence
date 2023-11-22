import { ITeam, NewTeam } from './team.model';

export const sampleWithRequiredData: ITeam = {
  id: 25293,
  name: 'prétendre calme comme',
};

export const sampleWithPartialData: ITeam = {
  id: 18623,
  name: 'hebdomadaire',
};

export const sampleWithFullData: ITeam = {
  id: 25152,
  name: 'pschitt au-dedans de',
};

export const sampleWithNewData: NewTeam = {
  name: 'au prix de concernant',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
