import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { LeaveStatus } from 'app/entities/enumerations/leave-status.model';
import { IWorkflow } from '../workflow.model';
import { WorkflowService } from '../service/workflow.service';
import { WorkflowFormService, WorkflowFormGroup } from './workflow-form.service';

@Component({
  standalone: true,
  selector: 'jhi-workflow-update',
  templateUrl: './workflow-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class WorkflowUpdateComponent implements OnInit {
  isSaving = false;
  workflow: IWorkflow | null = null;
  leaveStatusValues = Object.keys(LeaveStatus);

  editForm: WorkflowFormGroup = this.workflowFormService.createWorkflowFormGroup();

  constructor(
    protected workflowService: WorkflowService,
    protected workflowFormService: WorkflowFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ workflow }) => {
      this.workflow = workflow;
      if (workflow) {
        this.updateForm(workflow);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const workflow = this.workflowFormService.getWorkflow(this.editForm);
    if (workflow.id !== null) {
      this.subscribeToSaveResponse(this.workflowService.update(workflow));
    } else {
      this.subscribeToSaveResponse(this.workflowService.create(workflow));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWorkflow>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(workflow: IWorkflow): void {
    this.workflow = workflow;
    this.workflowFormService.resetForm(this.editForm, workflow);
  }
}
