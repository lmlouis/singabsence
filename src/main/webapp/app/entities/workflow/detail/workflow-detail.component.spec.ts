import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { WorkflowDetailComponent } from './workflow-detail.component';

describe('Workflow Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [WorkflowDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: WorkflowDetailComponent,
              resolve: { workflow: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(WorkflowDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load workflow on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', WorkflowDetailComponent);

      // THEN
      expect(instance.workflow).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
