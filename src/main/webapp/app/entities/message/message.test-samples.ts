import dayjs from 'dayjs/esm';

import { IMessage, NewMessage } from './message.model';

export const sampleWithRequiredData: IMessage = {
  id: 19649,
  purpose: 'd’autant que oups',
  content: 'de crainte que parlementaire',
};

export const sampleWithPartialData: IMessage = {
  id: 4504,
  purpose: 'hé bien que brusque',
  content: 'en plus de combien responsable',
  isread: true,
};

export const sampleWithFullData: IMessage = {
  id: 17512,
  purpose: 'autour de tout',
  content: 'ouah indiquer',
  sentat: dayjs('2023-11-21T11:33'),
  isread: false,
};

export const sampleWithNewData: NewMessage = {
  purpose: 'vorace',
  content: 'turquoise à demi',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
