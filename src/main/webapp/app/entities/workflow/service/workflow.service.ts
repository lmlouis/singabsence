import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IWorkflow, NewWorkflow } from '../workflow.model';

export type PartialUpdateWorkflow = Partial<IWorkflow> & Pick<IWorkflow, 'id'>;

export type EntityResponseType = HttpResponse<IWorkflow>;
export type EntityArrayResponseType = HttpResponse<IWorkflow[]>;

@Injectable({ providedIn: 'root' })
export class WorkflowService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/workflows');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(workflow: NewWorkflow): Observable<EntityResponseType> {
    return this.http.post<IWorkflow>(this.resourceUrl, workflow, { observe: 'response' });
  }

  update(workflow: IWorkflow): Observable<EntityResponseType> {
    return this.http.put<IWorkflow>(`${this.resourceUrl}/${this.getWorkflowIdentifier(workflow)}`, workflow, { observe: 'response' });
  }

  partialUpdate(workflow: PartialUpdateWorkflow): Observable<EntityResponseType> {
    return this.http.patch<IWorkflow>(`${this.resourceUrl}/${this.getWorkflowIdentifier(workflow)}`, workflow, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IWorkflow>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IWorkflow[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getWorkflowIdentifier(workflow: Pick<IWorkflow, 'id'>): number {
    return workflow.id;
  }

  compareWorkflow(o1: Pick<IWorkflow, 'id'> | null, o2: Pick<IWorkflow, 'id'> | null): boolean {
    return o1 && o2 ? this.getWorkflowIdentifier(o1) === this.getWorkflowIdentifier(o2) : o1 === o2;
  }

  addWorkflowToCollectionIfMissing<Type extends Pick<IWorkflow, 'id'>>(
    workflowCollection: Type[],
    ...workflowsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const workflows: Type[] = workflowsToCheck.filter(isPresent);
    if (workflows.length > 0) {
      const workflowCollectionIdentifiers = workflowCollection.map(workflowItem => this.getWorkflowIdentifier(workflowItem)!);
      const workflowsToAdd = workflows.filter(workflowItem => {
        const workflowIdentifier = this.getWorkflowIdentifier(workflowItem);
        if (workflowCollectionIdentifiers.includes(workflowIdentifier)) {
          return false;
        }
        workflowCollectionIdentifiers.push(workflowIdentifier);
        return true;
      });
      return [...workflowsToAdd, ...workflowCollection];
    }
    return workflowCollection;
  }
}
