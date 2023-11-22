import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ILeave, NewLeave } from '../leave.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ILeave for edit and NewLeaveFormGroupInput for create.
 */
type LeaveFormGroupInput = ILeave | PartialWithRequiredKeyOf<NewLeave>;

type LeaveFormDefaults = Pick<NewLeave, 'id' | 'senttos' | 'requestedbies'>;

type LeaveFormGroupContent = {
  id: FormControl<ILeave['id'] | NewLeave['id']>;
  type: FormControl<ILeave['type']>;
  owner: FormControl<ILeave['owner']>;
  workflow: FormControl<ILeave['workflow']>;
  reason: FormControl<ILeave['reason']>;
  period: FormControl<ILeave['period']>;
  senttos: FormControl<ILeave['senttos']>;
  requestedbies: FormControl<ILeave['requestedbies']>;
};

export type LeaveFormGroup = FormGroup<LeaveFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class LeaveFormService {
  createLeaveFormGroup(leave: LeaveFormGroupInput = { id: null }): LeaveFormGroup {
    const leaveRawValue = {
      ...this.getFormDefaults(),
      ...leave,
    };
    return new FormGroup<LeaveFormGroupContent>({
      id: new FormControl(
        { value: leaveRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      type: new FormControl(leaveRawValue.type, {
        validators: [Validators.required],
      }),
      owner: new FormControl(leaveRawValue.owner),
      workflow: new FormControl(leaveRawValue.workflow),
      reason: new FormControl(leaveRawValue.reason),
      period: new FormControl(leaveRawValue.period),
      senttos: new FormControl(leaveRawValue.senttos ?? []),
      requestedbies: new FormControl(leaveRawValue.requestedbies ?? []),
    });
  }

  getLeave(form: LeaveFormGroup): ILeave | NewLeave {
    return form.getRawValue() as ILeave | NewLeave;
  }

  resetForm(form: LeaveFormGroup, leave: LeaveFormGroupInput): void {
    const leaveRawValue = { ...this.getFormDefaults(), ...leave };
    form.reset(
      {
        ...leaveRawValue,
        id: { value: leaveRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): LeaveFormDefaults {
    return {
      id: null,
      senttos: [],
      requestedbies: [],
    };
  }
}
