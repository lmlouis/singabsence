import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { LeaveService } from '../service/leave.service';

import { LeaveComponent } from './leave.component';

describe('Leave Management Component', () => {
  let comp: LeaveComponent;
  let fixture: ComponentFixture<LeaveComponent>;
  let service: LeaveService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'leave', component: LeaveComponent }]), HttpClientTestingModule, LeaveComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'id,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'id,desc',
              }),
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(LeaveComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LeaveComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(LeaveService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        }),
      ),
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.leaves?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to leaveService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getLeaveIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getLeaveIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
