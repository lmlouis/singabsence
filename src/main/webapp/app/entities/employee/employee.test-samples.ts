import dayjs from 'dayjs/esm';

import { IEmployee, NewEmployee } from './employee.model';

export const sampleWithRequiredData: IEmployee = {
  id: 11208,
  fullname: 'à raison de coupable pendant',
  phone: '0344153339',
  position: 'naguère areu areu sous couleur de',
  location: 'hors paf blême',
  birthday: dayjs('2023-11-21'),
};

export const sampleWithPartialData: IEmployee = {
  id: 16363,
  fullname: 'debout horrible trop',
  phone: '+33 574237131',
  position: 'vers',
  location: 'arrêter',
  birthday: dayjs('2023-11-21'),
};

export const sampleWithFullData: IEmployee = {
  id: 15879,
  fullname: 'plus',
  phone: '+33 252886675',
  position: 'fouiller',
  location: 'personnel professionnel',
  birthday: dayjs('2023-11-21'),
};

export const sampleWithNewData: NewEmployee = {
  fullname: 'pour brusque',
  phone: '+33 298456260',
  position: 'passablement',
  location: 'tranquille',
  birthday: dayjs('2023-11-21'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
