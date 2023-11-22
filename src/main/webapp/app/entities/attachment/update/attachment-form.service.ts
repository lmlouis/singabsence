import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IAttachment, NewAttachment } from '../attachment.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAttachment for edit and NewAttachmentFormGroupInput for create.
 */
type AttachmentFormGroupInput = IAttachment | PartialWithRequiredKeyOf<NewAttachment>;

type AttachmentFormDefaults = Pick<NewAttachment, 'id' | 'msgs'>;

type AttachmentFormGroupContent = {
  id: FormControl<IAttachment['id'] | NewAttachment['id']>;
  name: FormControl<IAttachment['name']>;
  category: FormControl<IAttachment['category']>;
  picture: FormControl<IAttachment['picture']>;
  pictureContentType: FormControl<IAttachment['pictureContentType']>;
  document: FormControl<IAttachment['document']>;
  documentContentType: FormControl<IAttachment['documentContentType']>;
  owner: FormControl<IAttachment['owner']>;
  msgs: FormControl<IAttachment['msgs']>;
};

export type AttachmentFormGroup = FormGroup<AttachmentFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AttachmentFormService {
  createAttachmentFormGroup(attachment: AttachmentFormGroupInput = { id: null }): AttachmentFormGroup {
    const attachmentRawValue = {
      ...this.getFormDefaults(),
      ...attachment,
    };
    return new FormGroup<AttachmentFormGroupContent>({
      id: new FormControl(
        { value: attachmentRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      name: new FormControl(attachmentRawValue.name, {
        validators: [Validators.required],
      }),
      category: new FormControl(attachmentRawValue.category, {
        validators: [Validators.required],
      }),
      picture: new FormControl(attachmentRawValue.picture),
      pictureContentType: new FormControl(attachmentRawValue.pictureContentType),
      document: new FormControl(attachmentRawValue.document),
      documentContentType: new FormControl(attachmentRawValue.documentContentType),
      owner: new FormControl(attachmentRawValue.owner),
      msgs: new FormControl(attachmentRawValue.msgs ?? []),
    });
  }

  getAttachment(form: AttachmentFormGroup): IAttachment | NewAttachment {
    return form.getRawValue() as IAttachment | NewAttachment;
  }

  resetForm(form: AttachmentFormGroup, attachment: AttachmentFormGroupInput): void {
    const attachmentRawValue = { ...this.getFormDefaults(), ...attachment };
    form.reset(
      {
        ...attachmentRawValue,
        id: { value: attachmentRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): AttachmentFormDefaults {
    return {
      id: null,
      msgs: [],
    };
  }
}
