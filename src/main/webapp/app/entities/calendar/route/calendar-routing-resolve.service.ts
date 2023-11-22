import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICalendar } from '../calendar.model';
import { CalendarService } from '../service/calendar.service';

export const calendarResolve = (route: ActivatedRouteSnapshot): Observable<null | ICalendar> => {
  const id = route.params['id'];
  if (id) {
    return inject(CalendarService)
      .find(id)
      .pipe(
        mergeMap((calendar: HttpResponse<ICalendar>) => {
          if (calendar.body) {
            return of(calendar.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default calendarResolve;
