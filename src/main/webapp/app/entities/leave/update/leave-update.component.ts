import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IEmployee } from 'app/entities/employee/employee.model';
import { EmployeeService } from 'app/entities/employee/service/employee.service';
import { IWorkflow } from 'app/entities/workflow/workflow.model';
import { WorkflowService } from 'app/entities/workflow/service/workflow.service';
import { IMessage } from 'app/entities/message/message.model';
import { MessageService } from 'app/entities/message/service/message.service';
import { IEvent } from 'app/entities/event/event.model';
import { EventService } from 'app/entities/event/service/event.service';
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

  employeesSharedCollection: IEmployee[] = [];
  ownersCollection: IEmployee[] = [];
  workflowsSharedCollection: IWorkflow[] = [];
  messagesSharedCollection: IMessage[] = [];
  eventsSharedCollection: IEvent[] = [];

  editForm: LeaveFormGroup = this.leaveFormService.createLeaveFormGroup();

  constructor(
    protected leaveService: LeaveService,
    protected leaveFormService: LeaveFormService,
    protected employeeService: EmployeeService,
    protected workflowService: WorkflowService,
    protected messageService: MessageService,
    protected eventService: EventService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareEmployee = (o1: IEmployee | null, o2: IEmployee | null): boolean => this.employeeService.compareEmployee(o1, o2);

  compareWorkflow = (o1: IWorkflow | null, o2: IWorkflow | null): boolean => this.workflowService.compareWorkflow(o1, o2);

  compareMessage = (o1: IMessage | null, o2: IMessage | null): boolean => this.messageService.compareMessage(o1, o2);

  compareEvent = (o1: IEvent | null, o2: IEvent | null): boolean => this.eventService.compareEvent(o1, o2);

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

    this.employeesSharedCollection = this.employeeService.addEmployeeToCollectionIfMissing<IEmployee>(
      this.employeesSharedCollection,
      ...(leave.senttos ?? []),
    );
    this.ownersCollection = this.employeeService.addEmployeeToCollectionIfMissing<IEmployee>(this.ownersCollection, leave.owner);
    this.workflowsSharedCollection = this.workflowService.addWorkflowToCollectionIfMissing<IWorkflow>(
      this.workflowsSharedCollection,
      leave.workflow,
    );
    this.messagesSharedCollection = this.messageService.addMessageToCollectionIfMissing<IMessage>(
      this.messagesSharedCollection,
      leave.reason,
    );
    this.eventsSharedCollection = this.eventService.addEventToCollectionIfMissing<IEvent>(this.eventsSharedCollection, leave.period);
  }

  protected loadRelationshipsOptions(): void {
    this.employeeService
      .query()
      .pipe(map((res: HttpResponse<IEmployee[]>) => res.body ?? []))
      .pipe(
        map((employees: IEmployee[]) =>
          this.employeeService.addEmployeeToCollectionIfMissing<IEmployee>(employees, ...(this.leave?.senttos ?? [])),
        ),
      )
      .subscribe((employees: IEmployee[]) => (this.employeesSharedCollection = employees));

    this.employeeService
      .query({ filter: 'request-is-null' })
      .pipe(map((res: HttpResponse<IEmployee[]>) => res.body ?? []))
      .pipe(map((employees: IEmployee[]) => this.employeeService.addEmployeeToCollectionIfMissing<IEmployee>(employees, this.leave?.owner)))
      .subscribe((employees: IEmployee[]) => (this.ownersCollection = employees));

    this.workflowService
      .query()
      .pipe(map((res: HttpResponse<IWorkflow[]>) => res.body ?? []))
      .pipe(
        map((workflows: IWorkflow[]) => this.workflowService.addWorkflowToCollectionIfMissing<IWorkflow>(workflows, this.leave?.workflow)),
      )
      .subscribe((workflows: IWorkflow[]) => (this.workflowsSharedCollection = workflows));

    this.messageService
      .query()
      .pipe(map((res: HttpResponse<IMessage[]>) => res.body ?? []))
      .pipe(map((messages: IMessage[]) => this.messageService.addMessageToCollectionIfMissing<IMessage>(messages, this.leave?.reason)))
      .subscribe((messages: IMessage[]) => (this.messagesSharedCollection = messages));

    this.eventService
      .query()
      .pipe(map((res: HttpResponse<IEvent[]>) => res.body ?? []))
      .pipe(map((events: IEvent[]) => this.eventService.addEventToCollectionIfMissing<IEvent>(events, this.leave?.period)))
      .subscribe((events: IEvent[]) => (this.eventsSharedCollection = events));
  }
}
