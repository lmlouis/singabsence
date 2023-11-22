import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILeave, NewLeave } from '../leave.model';

export type PartialUpdateLeave = Partial<ILeave> & Pick<ILeave, 'id'>;

export type EntityResponseType = HttpResponse<ILeave>;
export type EntityArrayResponseType = HttpResponse<ILeave[]>;

@Injectable({ providedIn: 'root' })
export class LeaveService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/leaves');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(leave: NewLeave): Observable<EntityResponseType> {
    return this.http.post<ILeave>(this.resourceUrl, leave, { observe: 'response' });
  }

  update(leave: ILeave): Observable<EntityResponseType> {
    return this.http.put<ILeave>(`${this.resourceUrl}/${this.getLeaveIdentifier(leave)}`, leave, { observe: 'response' });
  }

  partialUpdate(leave: PartialUpdateLeave): Observable<EntityResponseType> {
    return this.http.patch<ILeave>(`${this.resourceUrl}/${this.getLeaveIdentifier(leave)}`, leave, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILeave>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILeave[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getLeaveIdentifier(leave: Pick<ILeave, 'id'>): number {
    return leave.id;
  }

  compareLeave(o1: Pick<ILeave, 'id'> | null, o2: Pick<ILeave, 'id'> | null): boolean {
    return o1 && o2 ? this.getLeaveIdentifier(o1) === this.getLeaveIdentifier(o2) : o1 === o2;
  }

  addLeaveToCollectionIfMissing<Type extends Pick<ILeave, 'id'>>(
    leaveCollection: Type[],
    ...leavesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const leaves: Type[] = leavesToCheck.filter(isPresent);
    if (leaves.length > 0) {
      const leaveCollectionIdentifiers = leaveCollection.map(leaveItem => this.getLeaveIdentifier(leaveItem)!);
      const leavesToAdd = leaves.filter(leaveItem => {
        const leaveIdentifier = this.getLeaveIdentifier(leaveItem);
        if (leaveCollectionIdentifiers.includes(leaveIdentifier)) {
          return false;
        }
        leaveCollectionIdentifiers.push(leaveIdentifier);
        return true;
      });
      return [...leavesToAdd, ...leaveCollection];
    }
    return leaveCollection;
  }
}
