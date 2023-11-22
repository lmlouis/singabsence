import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IMessage } from 'app/entities/message/message.model';
import { MessageService } from 'app/entities/message/service/message.service';
import { ILeave } from 'app/entities/leave/leave.model';
import { LeaveService } from 'app/entities/leave/service/leave.service';
import { IWorkflow } from '../workflow.model';
import { WorkflowService } from '../service/workflow.service';
import { WorkflowFormService } from './workflow-form.service';

import { WorkflowUpdateComponent } from './workflow-update.component';

describe('Workflow Management Update Component', () => {
  let comp: WorkflowUpdateComponent;
  let fixture: ComponentFixture<WorkflowUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let workflowFormService: WorkflowFormService;
  let workflowService: WorkflowService;
  let messageService: MessageService;
  let leaveService: LeaveService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), WorkflowUpdateComponent],
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
      .overrideTemplate(WorkflowUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(WorkflowUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    workflowFormService = TestBed.inject(WorkflowFormService);
    workflowService = TestBed.inject(WorkflowService);
    messageService = TestBed.inject(MessageService);
    leaveService = TestBed.inject(LeaveService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Message query and add missing value', () => {
      const workflow: IWorkflow = { id: 456 };
      const validation: IMessage = { id: 19193 };
      workflow.validation = validation;

      const messageCollection: IMessage[] = [{ id: 27162 }];
      jest.spyOn(messageService, 'query').mockReturnValue(of(new HttpResponse({ body: messageCollection })));
      const additionalMessages = [validation];
      const expectedCollection: IMessage[] = [...additionalMessages, ...messageCollection];
      jest.spyOn(messageService, 'addMessageToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ workflow });
      comp.ngOnInit();

      expect(messageService.query).toHaveBeenCalled();
      expect(messageService.addMessageToCollectionIfMissing).toHaveBeenCalledWith(
        messageCollection,
        ...additionalMessages.map(expect.objectContaining),
      );
      expect(comp.messagesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Leave query and add missing value', () => {
      const workflow: IWorkflow = { id: 456 };
      const request: ILeave = { id: 3867 };
      workflow.request = request;

      const leaveCollection: ILeave[] = [{ id: 6140 }];
      jest.spyOn(leaveService, 'query').mockReturnValue(of(new HttpResponse({ body: leaveCollection })));
      const additionalLeaves = [request];
      const expectedCollection: ILeave[] = [...additionalLeaves, ...leaveCollection];
      jest.spyOn(leaveService, 'addLeaveToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ workflow });
      comp.ngOnInit();

      expect(leaveService.query).toHaveBeenCalled();
      expect(leaveService.addLeaveToCollectionIfMissing).toHaveBeenCalledWith(
        leaveCollection,
        ...additionalLeaves.map(expect.objectContaining),
      );
      expect(comp.leavesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const workflow: IWorkflow = { id: 456 };
      const validation: IMessage = { id: 20927 };
      workflow.validation = validation;
      const request: ILeave = { id: 14951 };
      workflow.request = request;

      activatedRoute.data = of({ workflow });
      comp.ngOnInit();

      expect(comp.messagesSharedCollection).toContain(validation);
      expect(comp.leavesSharedCollection).toContain(request);
      expect(comp.workflow).toEqual(workflow);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IWorkflow>>();
      const workflow = { id: 123 };
      jest.spyOn(workflowFormService, 'getWorkflow').mockReturnValue(workflow);
      jest.spyOn(workflowService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ workflow });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: workflow }));
      saveSubject.complete();

      // THEN
      expect(workflowFormService.getWorkflow).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(workflowService.update).toHaveBeenCalledWith(expect.objectContaining(workflow));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IWorkflow>>();
      const workflow = { id: 123 };
      jest.spyOn(workflowFormService, 'getWorkflow').mockReturnValue({ id: null });
      jest.spyOn(workflowService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ workflow: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: workflow }));
      saveSubject.complete();

      // THEN
      expect(workflowFormService.getWorkflow).toHaveBeenCalled();
      expect(workflowService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IWorkflow>>();
      const workflow = { id: 123 };
      jest.spyOn(workflowService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ workflow });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(workflowService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareMessage', () => {
      it('Should forward to messageService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(messageService, 'compareMessage');
        comp.compareMessage(entity, entity2);
        expect(messageService.compareMessage).toHaveBeenCalledWith(entity, entity2);
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
  });
});
