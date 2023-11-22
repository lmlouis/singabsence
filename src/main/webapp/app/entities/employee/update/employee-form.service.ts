import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IEmployee, NewEmployee } from '../employee.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEmployee for edit and NewEmployeeFormGroupInput for create.
 */
type EmployeeFormGroupInput = IEmployee | PartialWithRequiredKeyOf<NewEmployee>;

type EmployeeFormDefaults = Pick<NewEmployee, 'id' | 'leaves' | 'inboxes' | 'teams'>;

type EmployeeFormGroupContent = {
  id: FormControl<IEmployee['id'] | NewEmployee['id']>;
  fullname: FormControl<IEmployee['fullname']>;
  phone: FormControl<IEmployee['phone']>;
  position: FormControl<IEmployee['position']>;
  location: FormControl<IEmployee['location']>;
  birthday: FormControl<IEmployee['birthday']>;
  authenticateby: FormControl<IEmployee['authenticateby']>;
  calendar: FormControl<IEmployee['calendar']>;
  leaves: FormControl<IEmployee['leaves']>;
  service: FormControl<IEmployee['service']>;
  inboxes: FormControl<IEmployee['inboxes']>;
  teams: FormControl<IEmployee['teams']>;
};

export type EmployeeFormGroup = FormGroup<EmployeeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EmployeeFormService {
  createEmployeeFormGroup(employee: EmployeeFormGroupInput = { id: null }): EmployeeFormGroup {
    const employeeRawValue = {
      ...this.getFormDefaults(),
      ...employee,
    };
    return new FormGroup<EmployeeFormGroupContent>({
      id: new FormControl(
        { value: employeeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      fullname: new FormControl(employeeRawValue.fullname, {
        validators: [Validators.required],
      }),
      phone: new FormControl(employeeRawValue.phone, {
        validators: [Validators.required],
      }),
      position: new FormControl(employeeRawValue.position, {
        validators: [Validators.required],
      }),
      location: new FormControl(employeeRawValue.location, {
        validators: [Validators.required],
      }),
      birthday: new FormControl(employeeRawValue.birthday, {
        validators: [Validators.required],
      }),
      authenticateby: new FormControl(employeeRawValue.authenticateby),
      calendar: new FormControl(employeeRawValue.calendar),
      leaves: new FormControl(employeeRawValue.leaves ?? []),
      service: new FormControl(employeeRawValue.service),
      inboxes: new FormControl(employeeRawValue.inboxes ?? []),
      teams: new FormControl(employeeRawValue.teams ?? []),
    });
  }

  getEmployee(form: EmployeeFormGroup): IEmployee | NewEmployee {
    return form.getRawValue() as IEmployee | NewEmployee;
  }

  resetForm(form: EmployeeFormGroup, employee: EmployeeFormGroupInput): void {
    const employeeRawValue = { ...this.getFormDefaults(), ...employee };
    form.reset(
      {
        ...employeeRawValue,
        id: { value: employeeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): EmployeeFormDefaults {
    return {
      id: null,
      leaves: [],
      inboxes: [],
      teams: [],
    };
  }
}
