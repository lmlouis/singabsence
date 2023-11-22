import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IWorkflow, NewWorkflow } from '../workflow.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IWorkflow for edit and NewWorkflowFormGroupInput for create.
 */
type WorkflowFormGroupInput = IWorkflow | PartialWithRequiredKeyOf<NewWorkflow>;

type WorkflowFormDefaults = Pick<NewWorkflow, 'id'>;

type WorkflowFormGroupContent = {
  id: FormControl<IWorkflow['id'] | NewWorkflow['id']>;
  status: FormControl<IWorkflow['status']>;
  description: FormControl<IWorkflow['description']>;
  validation: FormControl<IWorkflow['validation']>;
  request: FormControl<IWorkflow['request']>;
};

export type WorkflowFormGroup = FormGroup<WorkflowFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class WorkflowFormService {
  createWorkflowFormGroup(workflow: WorkflowFormGroupInput = { id: null }): WorkflowFormGroup {
    const workflowRawValue = {
      ...this.getFormDefaults(),
      ...workflow,
    };
    return new FormGroup<WorkflowFormGroupContent>({
      id: new FormControl(
        { value: workflowRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      status: new FormControl(workflowRawValue.status, {
        validators: [Validators.required],
      }),
      description: new FormControl(workflowRawValue.description, {
        validators: [Validators.required],
      }),
      validation: new FormControl(workflowRawValue.validation),
      request: new FormControl(workflowRawValue.request),
    });
  }

  getWorkflow(form: WorkflowFormGroup): IWorkflow | NewWorkflow {
    return form.getRawValue() as IWorkflow | NewWorkflow;
  }

  resetForm(form: WorkflowFormGroup, workflow: WorkflowFormGroupInput): void {
    const workflowRawValue = { ...this.getFormDefaults(), ...workflow };
    form.reset(
      {
        ...workflowRawValue,
        id: { value: workflowRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): WorkflowFormDefaults {
    return {
      id: null,
    };
  }
}
