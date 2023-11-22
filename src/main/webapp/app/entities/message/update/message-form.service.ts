import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IMessage, NewMessage } from '../message.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IMessage for edit and NewMessageFormGroupInput for create.
 */
type MessageFormGroupInput = IMessage | PartialWithRequiredKeyOf<NewMessage>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IMessage | NewMessage> = Omit<T, 'sentat'> & {
  sentat?: string | null;
};

type MessageFormRawValue = FormValueOf<IMessage>;

type NewMessageFormRawValue = FormValueOf<NewMessage>;

type MessageFormDefaults = Pick<NewMessage, 'id' | 'sentat' | 'isread' | 'attachements' | 'tos'>;

type MessageFormGroupContent = {
  id: FormControl<MessageFormRawValue['id'] | NewMessage['id']>;
  purpose: FormControl<MessageFormRawValue['purpose']>;
  content: FormControl<MessageFormRawValue['content']>;
  sentat: FormControl<MessageFormRawValue['sentat']>;
  isread: FormControl<MessageFormRawValue['isread']>;
  from: FormControl<MessageFormRawValue['from']>;
  attachements: FormControl<MessageFormRawValue['attachements']>;
  tos: FormControl<MessageFormRawValue['tos']>;
};

export type MessageFormGroup = FormGroup<MessageFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MessageFormService {
  createMessageFormGroup(message: MessageFormGroupInput = { id: null }): MessageFormGroup {
    const messageRawValue = this.convertMessageToMessageRawValue({
      ...this.getFormDefaults(),
      ...message,
    });
    return new FormGroup<MessageFormGroupContent>({
      id: new FormControl(
        { value: messageRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      purpose: new FormControl(messageRawValue.purpose, {
        validators: [Validators.required],
      }),
      content: new FormControl(messageRawValue.content, {
        validators: [Validators.required],
      }),
      sentat: new FormControl(messageRawValue.sentat),
      isread: new FormControl(messageRawValue.isread),
      from: new FormControl(messageRawValue.from),
      attachements: new FormControl(messageRawValue.attachements ?? []),
      tos: new FormControl(messageRawValue.tos ?? []),
    });
  }

  getMessage(form: MessageFormGroup): IMessage | NewMessage {
    return this.convertMessageRawValueToMessage(form.getRawValue() as MessageFormRawValue | NewMessageFormRawValue);
  }

  resetForm(form: MessageFormGroup, message: MessageFormGroupInput): void {
    const messageRawValue = this.convertMessageToMessageRawValue({ ...this.getFormDefaults(), ...message });
    form.reset(
      {
        ...messageRawValue,
        id: { value: messageRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): MessageFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      sentat: currentTime,
      isread: false,
      attachements: [],
      tos: [],
    };
  }

  private convertMessageRawValueToMessage(rawMessage: MessageFormRawValue | NewMessageFormRawValue): IMessage | NewMessage {
    return {
      ...rawMessage,
      sentat: dayjs(rawMessage.sentat, DATE_TIME_FORMAT),
    };
  }

  private convertMessageToMessageRawValue(
    message: IMessage | (Partial<NewMessage> & MessageFormDefaults),
  ): MessageFormRawValue | PartialWithRequiredKeyOf<NewMessageFormRawValue> {
    return {
      ...message,
      sentat: message.sentat ? message.sentat.format(DATE_TIME_FORMAT) : undefined,
      attachements: message.attachements ?? [],
      tos: message.tos ?? [],
    };
  }
}
