import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'employee',
        data: { pageTitle: 'singabsenceApp.employee.home.title' },
        loadChildren: () => import('./employee/employee.routes'),
      },
      {
        path: 'team',
        data: { pageTitle: 'singabsenceApp.team.home.title' },
        loadChildren: () => import('./team/team.routes'),
      },
      {
        path: 'leave',
        data: { pageTitle: 'singabsenceApp.leave.home.title' },
        loadChildren: () => import('./leave/leave.routes'),
      },
      {
        path: 'message',
        data: { pageTitle: 'singabsenceApp.message.home.title' },
        loadChildren: () => import('./message/message.routes'),
      },
      {
        path: 'attachment',
        data: { pageTitle: 'singabsenceApp.attachment.home.title' },
        loadChildren: () => import('./attachment/attachment.routes'),
      },
      {
        path: 'calendar',
        data: { pageTitle: 'singabsenceApp.calendar.home.title' },
        loadChildren: () => import('./calendar/calendar.routes'),
      },
      {
        path: 'workflow',
        data: { pageTitle: 'singabsenceApp.workflow.home.title' },
        loadChildren: () => import('./workflow/workflow.routes'),
      },
      {
        path: 'event',
        data: { pageTitle: 'singabsenceApp.event.home.title' },
        loadChildren: () => import('./event/event.routes'),
      },
      {
        path: 'organization',
        data: { pageTitle: 'singabsenceApp.organization.home.title' },
        loadChildren: () => import('./organization/organization.routes'),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
