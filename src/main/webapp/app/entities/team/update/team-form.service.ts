import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ITeam, NewTeam } from '../team.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITeam for edit and NewTeamFormGroupInput for create.
 */
type TeamFormGroupInput = ITeam | PartialWithRequiredKeyOf<NewTeam>;

type TeamFormDefaults = Pick<NewTeam, 'id' | 'members' | 'organizations'>;

type TeamFormGroupContent = {
  id: FormControl<ITeam['id'] | NewTeam['id']>;
  name: FormControl<ITeam['name']>;
  lead: FormControl<ITeam['lead']>;
  planing: FormControl<ITeam['planing']>;
  members: FormControl<ITeam['members']>;
  organizations: FormControl<ITeam['organizations']>;
};

export type TeamFormGroup = FormGroup<TeamFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TeamFormService {
  createTeamFormGroup(team: TeamFormGroupInput = { id: null }): TeamFormGroup {
    const teamRawValue = {
      ...this.getFormDefaults(),
      ...team,
    };
    return new FormGroup<TeamFormGroupContent>({
      id: new FormControl(
        { value: teamRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      name: new FormControl(teamRawValue.name, {
        validators: [Validators.required],
      }),
      lead: new FormControl(teamRawValue.lead),
      planing: new FormControl(teamRawValue.planing),
      members: new FormControl(teamRawValue.members ?? []),
      organizations: new FormControl(teamRawValue.organizations ?? []),
    });
  }

  getTeam(form: TeamFormGroup): ITeam | NewTeam {
    return form.getRawValue() as ITeam | NewTeam;
  }

  resetForm(form: TeamFormGroup, team: TeamFormGroupInput): void {
    const teamRawValue = { ...this.getFormDefaults(), ...team };
    form.reset(
      {
        ...teamRawValue,
        id: { value: teamRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): TeamFormDefaults {
    return {
      id: null,
      members: [],
      organizations: [],
    };
  }
}
