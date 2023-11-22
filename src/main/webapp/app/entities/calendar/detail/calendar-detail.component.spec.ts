import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { CalendarDetailComponent } from './calendar-detail.component';

describe('Calendar Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CalendarDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: CalendarDetailComponent,
              resolve: { calendar: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(CalendarDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load calendar on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', CalendarDetailComponent);

      // THEN
      expect(instance.calendar).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
