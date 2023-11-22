import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IOrganization, NewOrganization } from '../organization.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IOrganization for edit and NewOrganizationFormGroupInput for create.
 */
type OrganizationFormGroupInput = IOrganization | PartialWithRequiredKeyOf<NewOrganization>;

type OrganizationFormDefaults = Pick<NewOrganization, 'id' | 'units'>;

type OrganizationFormGroupContent = {
  id: FormControl<IOrganization['id'] | NewOrganization['id']>;
  name: FormControl<IOrganization['name']>;
  logo: FormControl<IOrganization['logo']>;
  logoContentType: FormControl<IOrganization['logoContentType']>;
  website: FormControl<IOrganization['website']>;
  domain: FormControl<IOrganization['domain']>;
  phone: FormControl<IOrganization['phone']>;
  director: FormControl<IOrganization['director']>;
  units: FormControl<IOrganization['units']>;
};

export type OrganizationFormGroup = FormGroup<OrganizationFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class OrganizationFormService {
  createOrganizationFormGroup(organization: OrganizationFormGroupInput = { id: null }): OrganizationFormGroup {
    const organizationRawValue = {
      ...this.getFormDefaults(),
      ...organization,
    };
    return new FormGroup<OrganizationFormGroupContent>({
      id: new FormControl(
        { value: organizationRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      name: new FormControl(organizationRawValue.name, {
        validators: [Validators.required],
      }),
      logo: new FormControl(organizationRawValue.logo),
      logoContentType: new FormControl(organizationRawValue.logoContentType),
      website: new FormControl(organizationRawValue.website),
      domain: new FormControl(organizationRawValue.domain, {
        validators: [Validators.required],
      }),
      phone: new FormControl(organizationRawValue.phone),
      director: new FormControl(organizationRawValue.director),
      units: new FormControl(organizationRawValue.units ?? []),
    });
  }

  getOrganization(form: OrganizationFormGroup): IOrganization | NewOrganization {
    return form.getRawValue() as IOrganization | NewOrganization;
  }

  resetForm(form: OrganizationFormGroup, organization: OrganizationFormGroupInput): void {
    const organizationRawValue = { ...this.getFormDefaults(), ...organization };
    form.reset(
      {
        ...organizationRawValue,
        id: { value: organizationRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): OrganizationFormDefaults {
    return {
      id: null,
      units: [],
    };
  }
}
