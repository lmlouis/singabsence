import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IEmployee } from 'app/entities/employee/employee.model';
import { EmployeeService } from 'app/entities/employee/service/employee.service';
import { ICalendar } from 'app/entities/calendar/calendar.model';
import { CalendarService } from 'app/entities/calendar/service/calendar.service';
import { ITeam } from '../team.model';
import { TeamService } from '../service/team.service';
import { TeamFormService } from './team-form.service';

import { TeamUpdateComponent } from './team-update.component';

describe('Team Management Update Component', () => {
  let comp: TeamUpdateComponent;
  let fixture: ComponentFixture<TeamUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let teamFormService: TeamFormService;
  let teamService: TeamService;
  let employeeService: EmployeeService;
  let calendarService: CalendarService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), TeamUpdateComponent],
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
      .overrideTemplate(TeamUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TeamUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    teamFormService = TestBed.inject(TeamFormService);
    teamService = TestBed.inject(TeamService);
    employeeService = TestBed.inject(EmployeeService);
    calendarService = TestBed.inject(CalendarService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Employee query and add missing value', () => {
      const team: ITeam = { id: 456 };
      const members: IEmployee[] = [{ id: 20713 }];
      team.members = members;

      const employeeCollection: IEmployee[] = [{ id: 15759 }];
      jest.spyOn(employeeService, 'query').mockReturnValue(of(new HttpResponse({ body: employeeCollection })));
      const additionalEmployees = [...members];
      const expectedCollection: IEmployee[] = [...additionalEmployees, ...employeeCollection];
      jest.spyOn(employeeService, 'addEmployeeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ team });
      comp.ngOnInit();

      expect(employeeService.query).toHaveBeenCalled();
      expect(employeeService.addEmployeeToCollectionIfMissing).toHaveBeenCalledWith(
        employeeCollection,
        ...additionalEmployees.map(expect.objectContaining),
      );
      expect(comp.employeesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call lead query and add missing value', () => {
      const team: ITeam = { id: 456 };
      const lead: IEmployee = { id: 9120 };
      team.lead = lead;

      const leadCollection: IEmployee[] = [{ id: 12143 }];
      jest.spyOn(employeeService, 'query').mockReturnValue(of(new HttpResponse({ body: leadCollection })));
      const expectedCollection: IEmployee[] = [lead, ...leadCollection];
      jest.spyOn(employeeService, 'addEmployeeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ team });
      comp.ngOnInit();

      expect(employeeService.query).toHaveBeenCalled();
      expect(employeeService.addEmployeeToCollectionIfMissing).toHaveBeenCalledWith(leadCollection, lead);
      expect(comp.leadsCollection).toEqual(expectedCollection);
    });

    it('Should call planing query and add missing value', () => {
      const team: ITeam = { id: 456 };
      const planing: ICalendar = { id: 24716 };
      team.planing = planing;

      const planingCollection: ICalendar[] = [{ id: 19158 }];
      jest.spyOn(calendarService, 'query').mockReturnValue(of(new HttpResponse({ body: planingCollection })));
      const expectedCollection: ICalendar[] = [planing, ...planingCollection];
      jest.spyOn(calendarService, 'addCalendarToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ team });
      comp.ngOnInit();

      expect(calendarService.query).toHaveBeenCalled();
      expect(calendarService.addCalendarToCollectionIfMissing).toHaveBeenCalledWith(planingCollection, planing);
      expect(comp.planingsCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const team: ITeam = { id: 456 };
      const lead: IEmployee = { id: 25487 };
      team.lead = lead;
      const members: IEmployee = { id: 32479 };
      team.members = [members];
      const planing: ICalendar = { id: 22867 };
      team.planing = planing;

      activatedRoute.data = of({ team });
      comp.ngOnInit();

      expect(comp.leadsCollection).toContain(lead);
      expect(comp.employeesSharedCollection).toContain(members);
      expect(comp.planingsCollection).toContain(planing);
      expect(comp.team).toEqual(team);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITeam>>();
      const team = { id: 123 };
      jest.spyOn(teamFormService, 'getTeam').mockReturnValue(team);
      jest.spyOn(teamService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ team });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: team }));
      saveSubject.complete();

      // THEN
      expect(teamFormService.getTeam).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(teamService.update).toHaveBeenCalledWith(expect.objectContaining(team));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITeam>>();
      const team = { id: 123 };
      jest.spyOn(teamFormService, 'getTeam').mockReturnValue({ id: null });
      jest.spyOn(teamService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ team: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: team }));
      saveSubject.complete();

      // THEN
      expect(teamFormService.getTeam).toHaveBeenCalled();
      expect(teamService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITeam>>();
      const team = { id: 123 };
      jest.spyOn(teamService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ team });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(teamService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareEmployee', () => {
      it('Should forward to employeeService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(employeeService, 'compareEmployee');
        comp.compareEmployee(entity, entity2);
        expect(employeeService.compareEmployee).toHaveBeenCalledWith(entity, entity2);
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
  });
});
