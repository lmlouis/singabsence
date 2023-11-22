import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { ICalendar } from 'app/entities/calendar/calendar.model';
import { CalendarService } from 'app/entities/calendar/service/calendar.service';
import { ILeave } from 'app/entities/leave/leave.model';
import { LeaveService } from 'app/entities/leave/service/leave.service';
import { ITeam } from 'app/entities/team/team.model';
import { TeamService } from 'app/entities/team/service/team.service';
import { EmployeeService } from '../service/employee.service';
import { IEmployee } from '../employee.model';
import { EmployeeFormService, EmployeeFormGroup } from './employee-form.service';

@Component({
  standalone: true,
  selector: 'jhi-employee-update',
  templateUrl: './employee-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class EmployeeUpdateComponent implements OnInit {
  isSaving = false;
  employee: IEmployee | null = null;

  usersSharedCollection: IUser[] = [];
  calendarsCollection: ICalendar[] = [];
  leavesSharedCollection: ILeave[] = [];
  teamsSharedCollection: ITeam[] = [];

  editForm: EmployeeFormGroup = this.employeeFormService.createEmployeeFormGroup();

  constructor(
    protected employeeService: EmployeeService,
    protected employeeFormService: EmployeeFormService,
    protected userService: UserService,
    protected calendarService: CalendarService,
    protected leaveService: LeaveService,
    protected teamService: TeamService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareUser = (o1: IUser | null, o2: IUser | null): boolean => this.userService.compareUser(o1, o2);

  compareCalendar = (o1: ICalendar | null, o2: ICalendar | null): boolean => this.calendarService.compareCalendar(o1, o2);

  compareLeave = (o1: ILeave | null, o2: ILeave | null): boolean => this.leaveService.compareLeave(o1, o2);

  compareTeam = (o1: ITeam | null, o2: ITeam | null): boolean => this.teamService.compareTeam(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ employee }) => {
      this.employee = employee;
      if (employee) {
        this.updateForm(employee);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const employee = this.employeeFormService.getEmployee(this.editForm);
    if (employee.id !== null) {
      this.subscribeToSaveResponse(this.employeeService.update(employee));
    } else {
      this.subscribeToSaveResponse(this.employeeService.create(employee));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmployee>>): void {
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

  protected updateForm(employee: IEmployee): void {
    this.employee = employee;
    this.employeeFormService.resetForm(this.editForm, employee);

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing<IUser>(this.usersSharedCollection, employee.authenticateby);
    this.calendarsCollection = this.calendarService.addCalendarToCollectionIfMissing<ICalendar>(
      this.calendarsCollection,
      employee.calendar,
    );
    this.leavesSharedCollection = this.leaveService.addLeaveToCollectionIfMissing<ILeave>(
      this.leavesSharedCollection,
      ...(employee.leaves ?? []),
    );
    this.teamsSharedCollection = this.teamService.addTeamToCollectionIfMissing<ITeam>(this.teamsSharedCollection, employee.service);
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing<IUser>(users, this.employee?.authenticateby)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));

    this.calendarService
      .query({ filter: 'owner-is-null' })
      .pipe(map((res: HttpResponse<ICalendar[]>) => res.body ?? []))
      .pipe(
        map((calendars: ICalendar[]) =>
          this.calendarService.addCalendarToCollectionIfMissing<ICalendar>(calendars, this.employee?.calendar),
        ),
      )
      .subscribe((calendars: ICalendar[]) => (this.calendarsCollection = calendars));

    this.leaveService
      .query()
      .pipe(map((res: HttpResponse<ILeave[]>) => res.body ?? []))
      .pipe(map((leaves: ILeave[]) => this.leaveService.addLeaveToCollectionIfMissing<ILeave>(leaves, ...(this.employee?.leaves ?? []))))
      .subscribe((leaves: ILeave[]) => (this.leavesSharedCollection = leaves));

    this.teamService
      .query()
      .pipe(map((res: HttpResponse<ITeam[]>) => res.body ?? []))
      .pipe(map((teams: ITeam[]) => this.teamService.addTeamToCollectionIfMissing<ITeam>(teams, this.employee?.service)))
      .subscribe((teams: ITeam[]) => (this.teamsSharedCollection = teams));
  }
}
