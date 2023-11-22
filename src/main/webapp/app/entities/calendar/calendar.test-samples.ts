import dayjs from 'dayjs/esm';

import { ICalendar, NewCalendar } from './calendar.model';

export const sampleWithRequiredData: ICalendar = {
  id: 20634,
};

export const sampleWithPartialData: ICalendar = {
  id: 30906,
  summury: 'insolite',
  createdat: dayjs('2023-11-21T07:54'),
};

export const sampleWithFullData: ICalendar = {
  id: 523,
  title: 'ensemble patientèle',
  summury: 'bof secouriste',
  createdat: dayjs('2023-11-21T07:53'),
};

export const sampleWithNewData: NewCalendar = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
