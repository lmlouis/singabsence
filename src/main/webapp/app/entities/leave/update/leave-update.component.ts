import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IWorkflow } from 'app/entities/workflow/workflow.model';
import { WorkflowService } from 'app/entities/workflow/service/workflow.service';
import { IAttachment } from 'app/entities/attachment/attachment.model';
import { AttachmentService } from 'app/entities/attachment/service/attachment.service';
import { IEmployee } from 'app/entities/employee/employee.model';
import { EmployeeService } from 'app/entities/employee/service/employee.service';
import { LeaveType } from 'app/entities/enumerations/leave-type.model';
import { LeaveService } from '../service/leave.service';
import { ILeave } from '../leave.model';
import { LeaveFormService, LeaveFormGroup } from './leave-form.service';

@Component({
  standalone: true,
  selector: 'jhi-leave-update',
  templateUrl: './leave-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class LeaveUpdateComponent implements OnInit {
  isSaving = false;
  leave: ILeave | null = null;
  leaveTypeValues = Object.keys(LeaveType);

  workflowsSharedCollection: IWorkflow[] = [];
  attachmentsSharedCollection: IAttachment[] = [];
  employeesSharedCollection: IEmployee[] = [];

  editForm: LeaveFormGroup = this.leaveFormService.createLeaveFormGroup();

  constructor(
    protected leaveService: LeaveService,
    protected leaveFormService: LeaveFormService,
    protected workflowService: WorkflowService,
    protected attachmentService: AttachmentService,
    protected employeeService: EmployeeService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareWorkflow = (o1: IWorkflow | null, o2: IWorkflow | null): boolean => this.workflowService.compareWorkflow(o1, o2);

  compareAttachment = (o1: IAttachment | null, o2: IAttachment | null): boolean => this.attachmentService.compareAttachment(o1, o2);

  compareEmployee = (o1: IEmployee | null, o2: IEmployee | null): boolean => this.employeeService.compareEmployee(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ leave }) => {
      this.leave = leave;
      if (leave) {
        this.updateForm(leave);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const leave = this.leaveFormService.getLeave(this.editForm);
    if (leave.id !== null) {
      this.subscribeToSaveResponse(this.leaveService.update(leave));
    } else {
      this.subscribeToSaveResponse(this.leaveService.create(leave));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILeave>>): void {
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

  protected updateForm(leave: ILeave): void {
    this.leave = leave;
    this.leaveFormService.resetForm(this.editForm, leave);

    this.workflowsSharedCollection = this.workflowService.addWorkflowToCollectionIfMissing<IWorkflow>(
      this.workflowsSharedCollection,
      leave.workflow,
    );
    this.attachmentsSharedCollection = this.attachmentService.addAttachmentToCollectionIfMissing<IAttachment>(
      this.attachmentsSharedCollection,
      leave.attachment,
    );
    this.employeesSharedCollection = this.employeeService.addEmployeeToCollectionIfMissing<IEmployee>(
      this.employeesSharedCollection,
      leave.sentto,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.workflowService
      .query()
      .pipe(map((res: HttpResponse<IWorkflow[]>) => res.body ?? []))
      .pipe(
        map((workflows: IWorkflow[]) => this.workflowService.addWorkflowToCollectionIfMissing<IWorkflow>(workflows, this.leave?.workflow)),
      )
      .subscribe((workflows: IWorkflow[]) => (this.workflowsSharedCollection = workflows));

    this.attachmentService
      .query()
      .pipe(map((res: HttpResponse<IAttachment[]>) => res.body ?? []))
      .pipe(
        map((attachments: IAttachment[]) =>
          this.attachmentService.addAttachmentToCollectionIfMissing<IAttachment>(attachments, this.leave?.attachment),
        ),
      )
      .subscribe((attachments: IAttachment[]) => (this.attachmentsSharedCollection = attachments));

    this.employeeService
      .query()
      .pipe(map((res: HttpResponse<IEmployee[]>) => res.body ?? []))
      .pipe(
        map((employees: IEmployee[]) => this.employeeService.addEmployeeToCollectionIfMissing<IEmployee>(employees, this.leave?.sentto)),
      )
      .subscribe((employees: IEmployee[]) => (this.employeesSharedCollection = employees));
  }
}
