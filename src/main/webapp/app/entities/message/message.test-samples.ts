import dayjs from 'dayjs/esm';

import { IMessage, NewMessage } from './message.model';

export const sampleWithRequiredData: IMessage = {
  id: 16520,
  purpose: 'atchoum cuicui membre titulaire',
  content: 'en alors que',
};

export const sampleWithPartialData: IMessage = {
  id: 21733,
  purpose: 'collègue',
  content: 'que bien que',
  sentat: dayjs('2023-11-21T15:26'),
};

export const sampleWithFullData: IMessage = {
  id: 31754,
  purpose: 'après que loin de',
  content: 'préparer',
  sentat: dayjs('2023-11-21T12:51'),
  isread: true,
};

export const sampleWithNewData: NewMessage = {
  purpose: 'taper disputer',
  content: 'présidence malade',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
