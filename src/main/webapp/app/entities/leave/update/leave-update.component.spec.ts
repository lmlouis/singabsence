import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IWorkflow } from 'app/entities/workflow/workflow.model';
import { WorkflowService } from 'app/entities/workflow/service/workflow.service';
import { IAttachment } from 'app/entities/attachment/attachment.model';
import { AttachmentService } from 'app/entities/attachment/service/attachment.service';
import { IEmployee } from 'app/entities/employee/employee.model';
import { EmployeeService } from 'app/entities/employee/service/employee.service';
import { ILeave } from '../leave.model';
import { LeaveService } from '../service/leave.service';
import { LeaveFormService } from './leave-form.service';

import { LeaveUpdateComponent } from './leave-update.component';

describe('Leave Management Update Component', () => {
  let comp: LeaveUpdateComponent;
  let fixture: ComponentFixture<LeaveUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let leaveFormService: LeaveFormService;
  let leaveService: LeaveService;
  let workflowService: WorkflowService;
  let attachmentService: AttachmentService;
  let employeeService: EmployeeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), LeaveUpdateComponent],
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
      .overrideTemplate(LeaveUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LeaveUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    leaveFormService = TestBed.inject(LeaveFormService);
    leaveService = TestBed.inject(LeaveService);
    workflowService = TestBed.inject(WorkflowService);
    attachmentService = TestBed.inject(AttachmentService);
    employeeService = TestBed.inject(EmployeeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Workflow query and add missing value', () => {
      const leave: ILeave = { id: 456 };
      const workflow: IWorkflow = { id: 9494 };
      leave.workflow = workflow;

      const workflowCollection: IWorkflow[] = [{ id: 17719 }];
      jest.spyOn(workflowService, 'query').mockReturnValue(of(new HttpResponse({ body: workflowCollection })));
      const additionalWorkflows = [workflow];
      const expectedCollection: IWorkflow[] = [...additionalWorkflows, ...workflowCollection];
      jest.spyOn(workflowService, 'addWorkflowToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ leave });
      comp.ngOnInit();

      expect(workflowService.query).toHaveBeenCalled();
      expect(workflowService.addWorkflowToCollectionIfMissing).toHaveBeenCalledWith(
        workflowCollection,
        ...additionalWorkflows.map(expect.objectContaining),
      );
      expect(comp.workflowsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Attachment query and add missing value', () => {
      const leave: ILeave = { id: 456 };
      const attachment: IAttachment = { id: 5481 };
      leave.attachment = attachment;

      const attachmentCollection: IAttachment[] = [{ id: 19412 }];
      jest.spyOn(attachmentService, 'query').mockReturnValue(of(new HttpResponse({ body: attachmentCollection })));
      const additionalAttachments = [attachment];
      const expectedCollection: IAttachment[] = [...additionalAttachments, ...attachmentCollection];
      jest.spyOn(attachmentService, 'addAttachmentToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ leave });
      comp.ngOnInit();

      expect(attachmentService.query).toHaveBeenCalled();
      expect(attachmentService.addAttachmentToCollectionIfMissing).toHaveBeenCalledWith(
        attachmentCollection,
        ...additionalAttachments.map(expect.objectContaining),
      );
      expect(comp.attachmentsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Employee query and add missing value', () => {
      const leave: ILeave = { id: 456 };
      const sentto: IEmployee = { id: 9340 };
      leave.sentto = sentto;

      const employeeCollection: IEmployee[] = [{ id: 31167 }];
      jest.spyOn(employeeService, 'query').mockReturnValue(of(new HttpResponse({ body: employeeCollection })));
      const additionalEmployees = [sentto];
      const expectedCollection: IEmployee[] = [...additionalEmployees, ...employeeCollection];
      jest.spyOn(employeeService, 'addEmployeeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ leave });
      comp.ngOnInit();

      expect(employeeService.query).toHaveBeenCalled();
      expect(employeeService.addEmployeeToCollectionIfMissing).toHaveBeenCalledWith(
        employeeCollection,
        ...additionalEmployees.map(expect.objectContaining),
      );
      expect(comp.employeesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const leave: ILeave = { id: 456 };
      const workflow: IWorkflow = { id: 23419 };
      leave.workflow = workflow;
      const attachment: IAttachment = { id: 7686 };
      leave.attachment = attachment;
      const sentto: IEmployee = { id: 5919 };
      leave.sentto = sentto;

      activatedRoute.data = of({ leave });
      comp.ngOnInit();

      expect(comp.workflowsSharedCollection).toContain(workflow);
      expect(comp.attachmentsSharedCollection).toContain(attachment);
      expect(comp.employeesSharedCollection).toContain(sentto);
      expect(comp.leave).toEqual(leave);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILeave>>();
      const leave = { id: 123 };
      jest.spyOn(leaveFormService, 'getLeave').mockReturnValue(leave);
      jest.spyOn(leaveService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ leave });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: leave }));
      saveSubject.complete();

      // THEN
      expect(leaveFormService.getLeave).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(leaveService.update).toHaveBeenCalledWith(expect.objectContaining(leave));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILeave>>();
      const leave = { id: 123 };
      jest.spyOn(leaveFormService, 'getLeave').mockReturnValue({ id: null });
      jest.spyOn(leaveService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ leave: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: leave }));
      saveSubject.complete();

      // THEN
      expect(leaveFormService.getLeave).toHaveBeenCalled();
      expect(leaveService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILeave>>();
      const leave = { id: 123 };
      jest.spyOn(leaveService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ leave });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(leaveService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareWorkflow', () => {
      it('Should forward to workflowService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(workflowService, 'compareWorkflow');
        comp.compareWorkflow(entity, entity2);
        expect(workflowService.compareWorkflow).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareAttachment', () => {
      it('Should forward to attachmentService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(attachmentService, 'compareAttachment');
        comp.compareAttachment(entity, entity2);
        expect(attachmentService.compareAttachment).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareEmployee', () => {
      it('Should forward to employeeService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(employeeService, 'compareEmployee');
        comp.compareEmployee(entity, entity2);
        expect(employeeService.compareEmployee).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
