import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IEmployee } from 'app/entities/employee/employee.model';
import { EmployeeService } from 'app/entities/employee/service/employee.service';
import { IAttachment } from 'app/entities/attachment/attachment.model';
import { AttachmentService } from 'app/entities/attachment/service/attachment.service';
import { MessageService } from '../service/message.service';
import { IMessage } from '../message.model';
import { MessageFormService, MessageFormGroup } from './message-form.service';

@Component({
  standalone: true,
  selector: 'jhi-message-update',
  templateUrl: './message-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class MessageUpdateComponent implements OnInit {
  isSaving = false;
  message: IMessage | null = null;

  employeesSharedCollection: IEmployee[] = [];
  fromsCollection: IEmployee[] = [];
  attachmentsSharedCollection: IAttachment[] = [];

  editForm: MessageFormGroup = this.messageFormService.createMessageFormGroup();

  constructor(
    protected messageService: MessageService,
    protected messageFormService: MessageFormService,
    protected employeeService: EmployeeService,
    protected attachmentService: AttachmentService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareEmployee = (o1: IEmployee | null, o2: IEmployee | null): boolean => this.employeeService.compareEmployee(o1, o2);

  compareAttachment = (o1: IAttachment | null, o2: IAttachment | null): boolean => this.attachmentService.compareAttachment(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ message }) => {
      this.message = message;
      if (message) {
        this.updateForm(message);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const message = this.messageFormService.getMessage(this.editForm);
    if (message.id !== null) {
      this.subscribeToSaveResponse(this.messageService.update(message));
    } else {
      this.subscribeToSaveResponse(this.messageService.create(message));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMessage>>): void {
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

  protected updateForm(message: IMessage): void {
    this.message = message;
    this.messageFormService.resetForm(this.editForm, message);

    this.employeesSharedCollection = this.employeeService.addEmployeeToCollectionIfMissing<IEmployee>(
      this.employeesSharedCollection,
      ...(message.tos ?? []),
    );
    this.fromsCollection = this.employeeService.addEmployeeToCollectionIfMissing<IEmployee>(this.fromsCollection, message.from);
    this.attachmentsSharedCollection = this.attachmentService.addAttachmentToCollectionIfMissing<IAttachment>(
      this.attachmentsSharedCollection,
      ...(message.attachements ?? []),
    );
  }

  protected loadRelationshipsOptions(): void {
    this.employeeService
      .query()
      .pipe(map((res: HttpResponse<IEmployee[]>) => res.body ?? []))
      .pipe(
        map((employees: IEmployee[]) =>
          this.employeeService.addEmployeeToCollectionIfMissing<IEmployee>(employees, ...(this.message?.tos ?? [])),
        ),
      )
      .subscribe((employees: IEmployee[]) => (this.employeesSharedCollection = employees));

    this.employeeService
      .query({ filter: 'ownmessage-is-null' })
      .pipe(map((res: HttpResponse<IEmployee[]>) => res.body ?? []))
      .pipe(
        map((employees: IEmployee[]) => this.employeeService.addEmployeeToCollectionIfMissing<IEmployee>(employees, this.message?.from)),
      )
      .subscribe((employees: IEmployee[]) => (this.fromsCollection = employees));

    this.attachmentService
      .query()
      .pipe(map((res: HttpResponse<IAttachment[]>) => res.body ?? []))
      .pipe(
        map((attachments: IAttachment[]) =>
          this.attachmentService.addAttachmentToCollectionIfMissing<IAttachment>(attachments, ...(this.message?.attachements ?? [])),
        ),
      )
      .subscribe((attachments: IAttachment[]) => (this.attachmentsSharedCollection = attachments));
  }
}
