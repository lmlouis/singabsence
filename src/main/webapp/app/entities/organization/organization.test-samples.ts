import { IOrganization, NewOrganization } from './organization.model';

export const sampleWithRequiredData: IOrganization = {
  id: 9576,
  name: 'du fait que corps enseignant',
  domain: 'fourbe étant donné que avant',
};

export const sampleWithPartialData: IOrganization = {
  id: 23976,
  name: 'prestataire de services semer',
  website: 'interdire de manière à ce que',
  domain: 'également',
  phone: '+33 498555500',
};

export const sampleWithFullData: IOrganization = {
  id: 8747,
  name: 'parce que',
  logo: '../fake-data/blob/hipster.png',
  logoContentType: 'unknown',
  website: 'louer ha',
  domain: 'tandis que',
  phone: '0771669517',
};

export const sampleWithNewData: NewOrganization = {
  name: 'puisque',
  domain: 'au-dehors',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
