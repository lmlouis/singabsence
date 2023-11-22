import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IEmployee } from 'app/entities/employee/employee.model';
import { EmployeeService } from 'app/entities/employee/service/employee.service';
import { ITeam } from 'app/entities/team/team.model';
import { TeamService } from 'app/entities/team/service/team.service';
import { OrganizationService } from '../service/organization.service';
import { IOrganization } from '../organization.model';
import { OrganizationFormService, OrganizationFormGroup } from './organization-form.service';

@Component({
  standalone: true,
  selector: 'jhi-organization-update',
  templateUrl: './organization-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class OrganizationUpdateComponent implements OnInit {
  isSaving = false;
  organization: IOrganization | null = null;

  directorsCollection: IEmployee[] = [];
  teamsSharedCollection: ITeam[] = [];

  editForm: OrganizationFormGroup = this.organizationFormService.createOrganizationFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected organizationService: OrganizationService,
    protected organizationFormService: OrganizationFormService,
    protected employeeService: EmployeeService,
    protected teamService: TeamService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareEmployee = (o1: IEmployee | null, o2: IEmployee | null): boolean => this.employeeService.compareEmployee(o1, o2);

  compareTeam = (o1: ITeam | null, o2: ITeam | null): boolean => this.teamService.compareTeam(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ organization }) => {
      this.organization = organization;
      if (organization) {
        this.updateForm(organization);
      }

      this.loadRelationshipsOptions();
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('singabsenceApp.error', { ...err, key: 'error.file.' + err.key })),
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const organization = this.organizationFormService.getOrganization(this.editForm);
    if (organization.id !== null) {
      this.subscribeToSaveResponse(this.organizationService.update(organization));
    } else {
      this.subscribeToSaveResponse(this.organizationService.create(organization));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrganization>>): void {
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

  protected updateForm(organization: IOrganization): void {
    this.organization = organization;
    this.organizationFormService.resetForm(this.editForm, organization);

    this.directorsCollection = this.employeeService.addEmployeeToCollectionIfMissing<IEmployee>(
      this.directorsCollection,
      organization.director,
    );
    this.teamsSharedCollection = this.teamService.addTeamToCollectionIfMissing<ITeam>(
      this.teamsSharedCollection,
      ...(organization.units ?? []),
    );
  }

  protected loadRelationshipsOptions(): void {
    this.employeeService
      .query({ filter: 'ownenterprise-is-null' })
      .pipe(map((res: HttpResponse<IEmployee[]>) => res.body ?? []))
      .pipe(
        map((employees: IEmployee[]) =>
          this.employeeService.addEmployeeToCollectionIfMissing<IEmployee>(employees, this.organization?.director),
        ),
      )
      .subscribe((employees: IEmployee[]) => (this.directorsCollection = employees));

    this.teamService
      .query()
      .pipe(map((res: HttpResponse<ITeam[]>) => res.body ?? []))
      .pipe(map((teams: ITeam[]) => this.teamService.addTeamToCollectionIfMissing<ITeam>(teams, ...(this.organization?.units ?? []))))
      .subscribe((teams: ITeam[]) => (this.teamsSharedCollection = teams));
  }
}
