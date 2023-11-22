import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../workflow.test-samples';

import { WorkflowFormService } from './workflow-form.service';

describe('Workflow Form Service', () => {
  let service: WorkflowFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(WorkflowFormService);
  });

  describe('Service methods', () => {
    describe('createWorkflowFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createWorkflowFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            status: expect.any(Object),
            description: expect.any(Object),
            validation: expect.any(Object),
            request: expect.any(Object),
          }),
        );
      });

      it('passing IWorkflow should create a new form with FormGroup', () => {
        const formGroup = service.createWorkflowFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            status: expect.any(Object),
            description: expect.any(Object),
            validation: expect.any(Object),
            request: expect.any(Object),
          }),
        );
      });
    });

    describe('getWorkflow', () => {
      it('should return NewWorkflow for default Workflow initial value', () => {
        const formGroup = service.createWorkflowFormGroup(sampleWithNewData);

        const workflow = service.getWorkflow(formGroup) as any;

        expect(workflow).toMatchObject(sampleWithNewData);
      });

      it('should return NewWorkflow for empty Workflow initial value', () => {
        const formGroup = service.createWorkflowFormGroup();

        const workflow = service.getWorkflow(formGroup) as any;

        expect(workflow).toMatchObject({});
      });

      it('should return IWorkflow', () => {
        const formGroup = service.createWorkflowFormGroup(sampleWithRequiredData);

        const workflow = service.getWorkflow(formGroup) as any;

        expect(workflow).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IWorkflow should not enable id FormControl', () => {
        const formGroup = service.createWorkflowFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewWorkflow should disable id FormControl', () => {
        const formGroup = service.createWorkflowFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
