import { IAttachment, NewAttachment } from './attachment.model';

export const sampleWithRequiredData: IAttachment = {
  id: 29995,
  name: 'chut exprès innombrable',
  category: 'DOCUMENT',
};

export const sampleWithPartialData: IAttachment = {
  id: 25877,
  name: 'aigre entre de crainte que',
  category: 'IMAGE',
};

export const sampleWithFullData: IAttachment = {
  id: 28611,
  name: 'de peur que à côté de ci',
  category: 'IMAGE',
  picture: '../fake-data/blob/hipster.png',
  pictureContentType: 'unknown',
  document: '../fake-data/blob/hipster.png',
  documentContentType: 'unknown',
};

export const sampleWithNewData: NewAttachment = {
  name: 'à la faveur de bien que',
  category: 'DOCUMENT',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
