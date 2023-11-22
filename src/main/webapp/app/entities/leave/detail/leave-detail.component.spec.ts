import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { LeaveDetailComponent } from './leave-detail.component';

describe('Leave Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LeaveDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: LeaveDetailComponent,
              resolve: { leave: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(LeaveDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load leave on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', LeaveDetailComponent);

      // THEN
      expect(instance.leave).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
