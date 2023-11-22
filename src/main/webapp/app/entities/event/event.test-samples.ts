import dayjs from 'dayjs/esm';

import { IEvent, NewEvent } from './event.model';

export const sampleWithRequiredData: IEvent = {
  id: 4652,
  title: 'égoïste',
  start: dayjs('2023-11-21T14:33'),
  end: dayjs('2023-11-21T19:25'),
};

export const sampleWithPartialData: IEvent = {
  id: 15009,
  title: 'personnel professionnel combien totalement',
  start: dayjs('2023-11-21T23:44'),
  end: dayjs('2023-11-21T02:27'),
  link: 'prou tant que',
};

export const sampleWithFullData: IEvent = {
  id: 18922,
  title: 'entièrement minuscule équipe',
  start: dayjs('2023-11-21T03:17'),
  end: dayjs('2023-11-21T20:19'),
  link: 'pff tranquille décoller',
};

export const sampleWithNewData: NewEvent = {
  title: 'euh',
  start: dayjs('2023-11-21T00:18'),
  end: dayjs('2023-11-21T05:05'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
