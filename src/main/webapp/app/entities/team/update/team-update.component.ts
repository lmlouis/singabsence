import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IEmployee } from 'app/entities/employee/employee.model';
import { EmployeeService } from 'app/entities/employee/service/employee.service';
import { ICalendar } from 'app/entities/calendar/calendar.model';
import { CalendarService } from 'app/entities/calendar/service/calendar.service';
import { TeamService } from '../service/team.service';
import { ITeam } from '../team.model';
import { TeamFormService, TeamFormGroup } from './team-form.service';

@Component({
  standalone: true,
  selector: 'jhi-team-update',
  templateUrl: './team-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class TeamUpdateComponent implements OnInit {
  isSaving = false;
  team: ITeam | null = null;

  employeesSharedCollection: IEmployee[] = [];
  leadsCollection: IEmployee[] = [];
  planingsCollection: ICalendar[] = [];

  editForm: TeamFormGroup = this.teamFormService.createTeamFormGroup();

  constructor(
    protected teamService: TeamService,
    protected teamFormService: TeamFormService,
    protected employeeService: EmployeeService,
    protected calendarService: CalendarService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareEmployee = (o1: IEmployee | null, o2: IEmployee | null): boolean => this.employeeService.compareEmployee(o1, o2);

  compareCalendar = (o1: ICalendar | null, o2: ICalendar | null): boolean => this.calendarService.compareCalendar(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ team }) => {
      this.team = team;
      if (team) {
        this.updateForm(team);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const team = this.teamFormService.getTeam(this.editForm);
    if (team.id !== null) {
      this.subscribeToSaveResponse(this.teamService.update(team));
    } else {
      this.subscribeToSaveResponse(this.teamService.create(team));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITeam>>): void {
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

  protected updateForm(team: ITeam): void {
    this.team = team;
    this.teamFormService.resetForm(this.editForm, team);

    this.employeesSharedCollection = this.employeeService.addEmployeeToCollectionIfMissing<IEmployee>(
      this.employeesSharedCollection,
      ...(team.members ?? []),
    );
    this.leadsCollection = this.employeeService.addEmployeeToCollectionIfMissing<IEmployee>(this.leadsCollection, team.lead);
    this.planingsCollection = this.calendarService.addCalendarToCollectionIfMissing<ICalendar>(this.planingsCollection, team.planing);
  }

  protected loadRelationshipsOptions(): void {
    this.employeeService
      .query()
      .pipe(map((res: HttpResponse<IEmployee[]>) => res.body ?? []))
      .pipe(
        map((employees: IEmployee[]) =>
          this.employeeService.addEmployeeToCollectionIfMissing<IEmployee>(employees, ...(this.team?.members ?? [])),
        ),
      )
      .subscribe((employees: IEmployee[]) => (this.employeesSharedCollection = employees));

    this.employeeService
      .query({ filter: 'ownteam-is-null' })
      .pipe(map((res: HttpResponse<IEmployee[]>) => res.body ?? []))
      .pipe(map((employees: IEmployee[]) => this.employeeService.addEmployeeToCollectionIfMissing<IEmployee>(employees, this.team?.lead)))
      .subscribe((employees: IEmployee[]) => (this.leadsCollection = employees));

    this.calendarService
      .query({ filter: 'ownteam-is-null' })
      .pipe(map((res: HttpResponse<ICalendar[]>) => res.body ?? []))
      .pipe(
        map((calendars: ICalendar[]) => this.calendarService.addCalendarToCollectionIfMissing<ICalendar>(calendars, this.team?.planing)),
      )
      .subscribe((calendars: ICalendar[]) => (this.planingsCollection = calendars));
  }
}
