import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { ICalendar } from 'app/entities/calendar/calendar.model';
import { CalendarService } from 'app/entities/calendar/service/calendar.service';
import { ILeave } from 'app/entities/leave/leave.model';
import { LeaveService } from 'app/entities/leave/service/leave.service';
import { ITeam } from 'app/entities/team/team.model';
import { TeamService } from 'app/entities/team/service/team.service';
import { IEmployee } from '../employee.model';
import { EmployeeService } from '../service/employee.service';
import { EmployeeFormService } from './employee-form.service';

import { EmployeeUpdateComponent } from './employee-update.component';

describe('Employee Management Update Component', () => {
  let comp: EmployeeUpdateComponent;
  let fixture: ComponentFixture<EmployeeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let employeeFormService: EmployeeFormService;
  let employeeService: EmployeeService;
  let userService: UserService;
  let calendarService: CalendarService;
  let leaveService: LeaveService;
  let teamService: TeamService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), EmployeeUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(EmployeeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EmployeeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    employeeFormService = TestBed.inject(EmployeeFormService);
    employeeService = TestBed.inject(EmployeeService);
    userService = TestBed.inject(UserService);
    calendarService = TestBed.inject(CalendarService);
    leaveService = TestBed.inject(LeaveService);
    teamService = TestBed.inject(TeamService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call User query and add missing value', () => {
      const employee: IEmployee = { id: 456 };
      const authenticateby: IUser = { id: 30026 };
      employee.authenticateby = authenticateby;

      const userCollection: IUser[] = [{ id: 1742 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [authenticateby];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ employee });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(
        userCollection,
        ...additionalUsers.map(expect.objectContaining),
      );
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call calendar query and add missing value', () => {
      const employee: IEmployee = { id: 456 };
      const calendar: ICalendar = { id: 17532 };
      employee.calendar = calendar;

      const calendarCollection: ICalendar[] = [{ id: 13253 }];
      jest.spyOn(calendarService, 'query').mockReturnValue(of(new HttpResponse({ body: calendarCollection })));
      const expectedCollection: ICalendar[] = [calendar, ...calendarCollection];
      jest.spyOn(calendarService, 'addCalendarToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ employee });
      comp.ngOnInit();

      expect(calendarService.query).toHaveBeenCalled();
      expect(calendarService.addCalendarToCollectionIfMissing).toHaveBeenCalledWith(calendarCollection, calendar);
      expect(comp.calendarsCollection).toEqual(expectedCollection);
    });

    it('Should call Leave query and add missing value', () => {
      const employee: IEmployee = { id: 456 };
      const leaves: ILeave[] = [{ id: 12218 }];
      employee.leaves = leaves;

      const leaveCollection: ILeave[] = [{ id: 9370 }];
      jest.spyOn(leaveService, 'query').mockReturnValue(of(new HttpResponse({ body: leaveCollection })));
      const additionalLeaves = [...leaves];
      const expectedCollection: ILeave[] = [...additionalLeaves, ...leaveCollection];
      jest.spyOn(leaveService, 'addLeaveToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ employee });
      comp.ngOnInit();

      expect(leaveService.query).toHaveBeenCalled();
      expect(leaveService.addLeaveToCollectionIfMissing).toHaveBeenCalledWith(
        leaveCollection,
        ...additionalLeaves.map(expect.objectContaining),
      );
      expect(comp.leavesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Team query and add missing value', () => {
      const employee: IEmployee = { id: 456 };
      const service: ITeam = { id: 25217 };
      employee.service = service;

      const teamCollection: ITeam[] = [{ id: 19067 }];
      jest.spyOn(teamService, 'query').mockReturnValue(of(new HttpResponse({ body: teamCollection })));
      const additionalTeams = [service];
      const expectedCollection: ITeam[] = [...additionalTeams, ...teamCollection];
      jest.spyOn(teamService, 'addTeamToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ employee });
      comp.ngOnInit();

      expect(teamService.query).toHaveBeenCalled();
      expect(teamService.addTeamToCollectionIfMissing).toHaveBeenCalledWith(
        teamCollection,
        ...additionalTeams.map(expect.objectContaining),
      );
      expect(comp.teamsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const employee: IEmployee = { id: 456 };
      const authenticateby: IUser = { id: 15479 };
      employee.authenticateby = authenticateby;
      const calendar: ICalendar = { id: 30580 };
      employee.calendar = calendar;
      const leaves: ILeave = { id: 26944 };
      employee.leaves = [leaves];
      const service: ITeam = { id: 925 };
      employee.service = service;

      activatedRoute.data = of({ employee });
      comp.ngOnInit();

      expect(comp.usersSharedCollection).toContain(authenticateby);
      expect(comp.calendarsCollection).toContain(calendar);
      expect(comp.leavesSharedCollection).toContain(leaves);
      expect(comp.teamsSharedCollection).toContain(service);
      expect(comp.employee).toEqual(employee);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmployee>>();
      const employee = { id: 123 };
      jest.spyOn(employeeFormService, 'getEmployee').mockReturnValue(employee);
      jest.spyOn(employeeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ employee });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: employee }));
      saveSubject.complete();

      // THEN
      expect(employeeFormService.getEmployee).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(employeeService.update).toHaveBeenCalledWith(expect.objectContaining(employee));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmployee>>();
      const employee = { id: 123 };
      jest.spyOn(employeeFormService, 'getEmployee').mockReturnValue({ id: null });
      jest.spyOn(employeeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ employee: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: employee }));
      saveSubject.complete();

      // THEN
      expect(employeeFormService.getEmployee).toHaveBeenCalled();
      expect(employeeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmployee>>();
      const employee = { id: 123 };
      jest.spyOn(employeeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ employee });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(employeeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareUser', () => {
      it('Should forward to userService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(userService, 'compareUser');
        comp.compareUser(entity, entity2);
        expect(userService.compareUser).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareCalendar', () => {
      it('Should forward to calendarService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(calendarService, 'compareCalendar');
        comp.compareCalendar(entity, entity2);
        expect(calendarService.compareCalendar).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareLeave', () => {
      it('Should forward to leaveService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(leaveService, 'compareLeave');
        comp.compareLeave(entity, entity2);
        expect(leaveService.compareLeave).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareTeam', () => {
      it('Should forward to teamService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(teamService, 'compareTeam');
        comp.compareTeam(entity, entity2);
        expect(teamService.compareTeam).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
