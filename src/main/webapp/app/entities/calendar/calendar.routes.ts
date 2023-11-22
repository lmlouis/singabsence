import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { CalendarComponent } from './list/calendar.component';
import { CalendarDetailComponent } from './detail/calendar-detail.component';
import { CalendarUpdateComponent } from './update/calendar-update.component';
import CalendarResolve from './route/calendar-routing-resolve.service';

const calendarRoute: Routes = [
  {
    path: '',
    component: CalendarComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CalendarDetailComponent,
    resolve: {
      calendar: CalendarResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CalendarUpdateComponent,
    resolve: {
      calendar: CalendarResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CalendarUpdateComponent,
    resolve: {
      calendar: CalendarResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default calendarRoute;
