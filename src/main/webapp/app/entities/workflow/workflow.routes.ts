import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { WorkflowComponent } from './list/workflow.component';
import { WorkflowDetailComponent } from './detail/workflow-detail.component';
import { WorkflowUpdateComponent } from './update/workflow-update.component';
import WorkflowResolve from './route/workflow-routing-resolve.service';

const workflowRoute: Routes = [
  {
    path: '',
    component: WorkflowComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: WorkflowDetailComponent,
    resolve: {
      workflow: WorkflowResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: WorkflowUpdateComponent,
    resolve: {
      workflow: WorkflowResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: WorkflowUpdateComponent,
    resolve: {
      workflow: WorkflowResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default workflowRoute;
