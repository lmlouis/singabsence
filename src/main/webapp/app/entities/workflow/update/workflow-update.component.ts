import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IMessage } from 'app/entities/message/message.model';
import { MessageService } from 'app/entities/message/service/message.service';
import { ILeave } from 'app/entities/leave/leave.model';
import { LeaveService } from 'app/entities/leave/service/leave.service';
import { LeaveStatus } from 'app/entities/enumerations/leave-status.model';
import { WorkflowService } from '../service/workflow.service';
import { IWorkflow } from '../workflow.model';
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

  messagesSharedCollection: IMessage[] = [];
  leavesSharedCollection: ILeave[] = [];

  editForm: WorkflowFormGroup = this.workflowFormService.createWorkflowFormGroup();

  constructor(
    protected workflowService: WorkflowService,
    protected workflowFormService: WorkflowFormService,
    protected messageService: MessageService,
    protected leaveService: LeaveService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareMessage = (o1: IMessage | null, o2: IMessage | null): boolean => this.messageService.compareMessage(o1, o2);

  compareLeave = (o1: ILeave | null, o2: ILeave | null): boolean => this.leaveService.compareLeave(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ workflow }) => {
      this.workflow = workflow;
      if (workflow) {
        this.updateForm(workflow);
      }

      this.loadRelationshipsOptions();
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

    this.messagesSharedCollection = this.messageService.addMessageToCollectionIfMissing<IMessage>(
      this.messagesSharedCollection,
      workflow.validation,
    );
    this.leavesSharedCollection = this.leaveService.addLeaveToCollectionIfMissing<ILeave>(this.leavesSharedCollection, workflow.request);
  }

  protected loadRelationshipsOptions(): void {
    this.messageService
      .query()
      .pipe(map((res: HttpResponse<IMessage[]>) => res.body ?? []))
      .pipe(
        map((messages: IMessage[]) => this.messageService.addMessageToCollectionIfMissing<IMessage>(messages, this.workflow?.validation)),
      )
      .subscribe((messages: IMessage[]) => (this.messagesSharedCollection = messages));

    this.leaveService
      .query()
      .pipe(map((res: HttpResponse<ILeave[]>) => res.body ?? []))
      .pipe(map((leaves: ILeave[]) => this.leaveService.addLeaveToCollectionIfMissing<ILeave>(leaves, this.workflow?.request)))
      .subscribe((leaves: ILeave[]) => (this.leavesSharedCollection = leaves));
  }
}
