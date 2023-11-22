import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IEmployee } from 'app/entities/employee/employee.model';
import { EmployeeService } from 'app/entities/employee/service/employee.service';
import { ITeam } from 'app/entities/team/team.model';
import { TeamService } from 'app/entities/team/service/team.service';
import { IOrganization } from '../organization.model';
import { OrganizationService } from '../service/organization.service';
import { OrganizationFormService } from './organization-form.service';

import { OrganizationUpdateComponent } from './organization-update.component';

describe('Organization Management Update Component', () => {
  let comp: OrganizationUpdateComponent;
  let fixture: ComponentFixture<OrganizationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let organizationFormService: OrganizationFormService;
  let organizationService: OrganizationService;
  let employeeService: EmployeeService;
  let teamService: TeamService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), OrganizationUpdateComponent],
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
      .overrideTemplate(OrganizationUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(OrganizationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    organizationFormService = TestBed.inject(OrganizationFormService);
    organizationService = TestBed.inject(OrganizationService);
    employeeService = TestBed.inject(EmployeeService);
    teamService = TestBed.inject(TeamService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call director query and add missing value', () => {
      const organization: IOrganization = { id: 456 };
      const director: IEmployee = { id: 22873 };
      organization.director = director;

      const directorCollection: IEmployee[] = [{ id: 28469 }];
      jest.spyOn(employeeService, 'query').mockReturnValue(of(new HttpResponse({ body: directorCollection })));
      const expectedCollection: IEmployee[] = [director, ...directorCollection];
      jest.spyOn(employeeService, 'addEmployeeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ organization });
      comp.ngOnInit();

      expect(employeeService.query).toHaveBeenCalled();
      expect(employeeService.addEmployeeToCollectionIfMissing).toHaveBeenCalledWith(directorCollection, director);
      expect(comp.directorsCollection).toEqual(expectedCollection);
    });

    it('Should call Team query and add missing value', () => {
      const organization: IOrganization = { id: 456 };
      const units: ITeam[] = [{ id: 6064 }];
      organization.units = units;

      const teamCollection: ITeam[] = [{ id: 15595 }];
      jest.spyOn(teamService, 'query').mockReturnValue(of(new HttpResponse({ body: teamCollection })));
      const additionalTeams = [...units];
      const expectedCollection: ITeam[] = [...additionalTeams, ...teamCollection];
      jest.spyOn(teamService, 'addTeamToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ organization });
      comp.ngOnInit();

      expect(teamService.query).toHaveBeenCalled();
      expect(teamService.addTeamToCollectionIfMissing).toHaveBeenCalledWith(
        teamCollection,
        ...additionalTeams.map(expect.objectContaining),
      );
      expect(comp.teamsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const organization: IOrganization = { id: 456 };
      const director: IEmployee = { id: 25842 };
      organization.director = director;
      const units: ITeam = { id: 22599 };
      organization.units = [units];

      activatedRoute.data = of({ organization });
      comp.ngOnInit();

      expect(comp.directorsCollection).toContain(director);
      expect(comp.teamsSharedCollection).toContain(units);
      expect(comp.organization).toEqual(organization);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrganization>>();
      const organization = { id: 123 };
      jest.spyOn(organizationFormService, 'getOrganization').mockReturnValue(organization);
      jest.spyOn(organizationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ organization });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: organization }));
      saveSubject.complete();

      // THEN
      expect(organizationFormService.getOrganization).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(organizationService.update).toHaveBeenCalledWith(expect.objectContaining(organization));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrganization>>();
      const organization = { id: 123 };
      jest.spyOn(organizationFormService, 'getOrganization').mockReturnValue({ id: null });
      jest.spyOn(organizationService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ organization: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: organization }));
      saveSubject.complete();

      // THEN
      expect(organizationFormService.getOrganization).toHaveBeenCalled();
      expect(organizationService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrganization>>();
      const organization = { id: 123 };
      jest.spyOn(organizationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ organization });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(organizationService.update).toHaveBeenCalled();
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
