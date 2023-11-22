import { IAttachment, NewAttachment } from './attachment.model';

export const sampleWithRequiredData: IAttachment = {
  id: 20423,
  name: 'de sorte que pff',
  category: 'IMAGE',
};

export const sampleWithPartialData: IAttachment = {
  id: 28128,
  name: 'vorace hirsute',
  category: 'DOCUMENT',
  picture: '../fake-data/blob/hipster.png',
  pictureContentType: 'unknown',
  document: '../fake-data/blob/hipster.png',
  documentContentType: 'unknown',
};

export const sampleWithFullData: IAttachment = {
  id: 28767,
  name: 'entre',
  category: 'DOCUMENT',
  picture: '../fake-data/blob/hipster.png',
  pictureContentType: 'unknown',
  document: '../fake-data/blob/hipster.png',
  documentContentType: 'unknown',
};

export const sampleWithNewData: NewAttachment = {
  name: 'compliquer autour de sauf',
  category: 'IMAGE',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
