import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { WorkflowService } from '../service/workflow.service';

import { WorkflowComponent } from './workflow.component';

describe('Workflow Management Component', () => {
  let comp: WorkflowComponent;
  let fixture: ComponentFixture<WorkflowComponent>;
  let service: WorkflowService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'workflow', component: WorkflowComponent }]),
        HttpClientTestingModule,
        WorkflowComponent,
      ],
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
      .overrideTemplate(WorkflowComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(WorkflowComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(WorkflowService);

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
    expect(comp.workflows?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to workflowService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getWorkflowIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getWorkflowIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
