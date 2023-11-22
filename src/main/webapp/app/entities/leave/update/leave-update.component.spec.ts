import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IEmployee } from 'app/entities/employee/employee.model';
import { EmployeeService } from 'app/entities/employee/service/employee.service';
import { IWorkflow } from 'app/entities/workflow/workflow.model';
import { WorkflowService } from 'app/entities/workflow/service/workflow.service';
import { IMessage } from 'app/entities/message/message.model';
import { MessageService } from 'app/entities/message/service/message.service';
import { IEvent } from 'app/entities/event/event.model';
import { EventService } from 'app/entities/event/service/event.service';
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
  let employeeService: EmployeeService;
  let workflowService: WorkflowService;
  let messageService: MessageService;
  let eventService: EventService;

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
    employeeService = TestBed.inject(EmployeeService);
    workflowService = TestBed.inject(WorkflowService);
    messageService = TestBed.inject(MessageService);
    eventService = TestBed.inject(EventService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Employee query and add missing value', () => {
      const leave: ILeave = { id: 456 };
      const senttos: IEmployee[] = [{ id: 9340 }];
      leave.senttos = senttos;

      const employeeCollection: IEmployee[] = [{ id: 31167 }];
      jest.spyOn(employeeService, 'query').mockReturnValue(of(new HttpResponse({ body: employeeCollection })));
      const additionalEmployees = [...senttos];
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

    it('Should call owner query and add missing value', () => {
      const leave: ILeave = { id: 456 };
      const owner: IEmployee = { id: 5919 };
      leave.owner = owner;

      const ownerCollection: IEmployee[] = [{ id: 12118 }];
      jest.spyOn(employeeService, 'query').mockReturnValue(of(new HttpResponse({ body: ownerCollection })));
      const expectedCollection: IEmployee[] = [owner, ...ownerCollection];
      jest.spyOn(employeeService, 'addEmployeeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ leave });
      comp.ngOnInit();

      expect(employeeService.query).toHaveBeenCalled();
      expect(employeeService.addEmployeeToCollectionIfMissing).toHaveBeenCalledWith(ownerCollection, owner);
      expect(comp.ownersCollection).toEqual(expectedCollection);
    });

    it('Should call Workflow query and add missing value', () => {
      const leave: ILeave = { id: 456 };
      const workflow: IWorkflow = { id: 4139 };
      leave.workflow = workflow;

      const workflowCollection: IWorkflow[] = [{ id: 21064 }];
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

    it('Should call Message query and add missing value', () => {
      const leave: ILeave = { id: 456 };
      const reason: IMessage = { id: 19649 };
      leave.reason = reason;

      const messageCollection: IMessage[] = [{ id: 12456 }];
      jest.spyOn(messageService, 'query').mockReturnValue(of(new HttpResponse({ body: messageCollection })));
      const additionalMessages = [reason];
      const expectedCollection: IMessage[] = [...additionalMessages, ...messageCollection];
      jest.spyOn(messageService, 'addMessageToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ leave });
      comp.ngOnInit();

      expect(messageService.query).toHaveBeenCalled();
      expect(messageService.addMessageToCollectionIfMissing).toHaveBeenCalledWith(
        messageCollection,
        ...additionalMessages.map(expect.objectContaining),
      );
      expect(comp.messagesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Event query and add missing value', () => {
      const leave: ILeave = { id: 456 };
      const period: IEvent = { id: 9316 };
      leave.period = period;

      const eventCollection: IEvent[] = [{ id: 9131 }];
      jest.spyOn(eventService, 'query').mockReturnValue(of(new HttpResponse({ body: eventCollection })));
      const additionalEvents = [period];
      const expectedCollection: IEvent[] = [...additionalEvents, ...eventCollection];
      jest.spyOn(eventService, 'addEventToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ leave });
      comp.ngOnInit();

      expect(eventService.query).toHaveBeenCalled();
      expect(eventService.addEventToCollectionIfMissing).toHaveBeenCalledWith(
        eventCollection,
        ...additionalEvents.map(expect.objectContaining),
      );
      expect(comp.eventsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const leave: ILeave = { id: 456 };
      const owner: IEmployee = { id: 11082 };
      leave.owner = owner;
      const sentto: IEmployee = { id: 10105 };
      leave.senttos = [sentto];
      const workflow: IWorkflow = { id: 25884 };
      leave.workflow = workflow;
      const reason: IMessage = { id: 14935 };
      leave.reason = reason;
      const period: IEvent = { id: 3396 };
      leave.period = period;

      activatedRoute.data = of({ leave });
      comp.ngOnInit();

      expect(comp.ownersCollection).toContain(owner);
      expect(comp.employeesSharedCollection).toContain(sentto);
      expect(comp.workflowsSharedCollection).toContain(workflow);
      expect(comp.messagesSharedCollection).toContain(reason);
      expect(comp.eventsSharedCollection).toContain(period);
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
    describe('compareEmployee', () => {
      it('Should forward to employeeService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(employeeService, 'compareEmployee');
        comp.compareEmployee(entity, entity2);
        expect(employeeService.compareEmployee).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareWorkflow', () => {
      it('Should forward to workflowService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(workflowService, 'compareWorkflow');
        comp.compareWorkflow(entity, entity2);
        expect(workflowService.compareWorkflow).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareMessage', () => {
      it('Should forward to messageService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(messageService, 'compareMessage');
        comp.compareMessage(entity, entity2);
        expect(messageService.compareMessage).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareEvent', () => {
      it('Should forward to eventService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(eventService, 'compareEvent');
        comp.compareEvent(entity, entity2);
        expect(eventService.compareEvent).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
