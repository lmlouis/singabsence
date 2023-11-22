import dayjs from 'dayjs/esm';

import { IEvent, NewEvent } from './event.model';

export const sampleWithRequiredData: IEvent = {
  id: 10507,
  title: 'suivant hors',
  start: dayjs('2023-11-21T18:23'),
  end: dayjs('2023-11-21T09:53'),
};

export const sampleWithPartialData: IEvent = {
  id: 20299,
  title: 'de manière à ce que',
  start: dayjs('2023-11-21T11:41'),
  end: dayjs('2023-11-22T05:12'),
  link: 'siffler',
};

export const sampleWithFullData: IEvent = {
  id: 24191,
  title: 'dorénavant aimable rapide',
  start: dayjs('2023-11-21T12:48'),
  end: dayjs('2023-11-21T15:46'),
  link: 'après-demain adepte rédaction',
};

export const sampleWithNewData: NewEvent = {
  title: 'afin que boulanger fade',
  start: dayjs('2023-11-22T03:06'),
  end: dayjs('2023-11-21T08:54'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
