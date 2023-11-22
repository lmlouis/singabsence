import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IEvent } from 'app/entities/event/event.model';
import { EventService } from 'app/entities/event/service/event.service';
import { ICalendar } from '../calendar.model';
import { CalendarService } from '../service/calendar.service';
import { CalendarFormService, CalendarFormGroup } from './calendar-form.service';

@Component({
  standalone: true,
  selector: 'jhi-calendar-update',
  templateUrl: './calendar-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CalendarUpdateComponent implements OnInit {
  isSaving = false;
  calendar: ICalendar | null = null;

  eventsSharedCollection: IEvent[] = [];

  editForm: CalendarFormGroup = this.calendarFormService.createCalendarFormGroup();

  constructor(
    protected calendarService: CalendarService,
    protected calendarFormService: CalendarFormService,
    protected eventService: EventService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareEvent = (o1: IEvent | null, o2: IEvent | null): boolean => this.eventService.compareEvent(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ calendar }) => {
      this.calendar = calendar;
      if (calendar) {
        this.updateForm(calendar);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const calendar = this.calendarFormService.getCalendar(this.editForm);
    if (calendar.id !== null) {
      this.subscribeToSaveResponse(this.calendarService.update(calendar));
    } else {
      this.subscribeToSaveResponse(this.calendarService.create(calendar));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICalendar>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(calendar: ICalendar): void {
    this.calendar = calendar;
    this.calendarFormService.resetForm(this.editForm, calendar);

    this.eventsSharedCollection = this.eventService.addEventToCollectionIfMissing<IEvent>(
      this.eventsSharedCollection,
      ...(calendar.events ?? []),
    );
  }

  protected loadRelationshipsOptions(): void {
    this.eventService
      .query()
      .pipe(map((res: HttpResponse<IEvent[]>) => res.body ?? []))
      .pipe(map((events: IEvent[]) => this.eventService.addEventToCollectionIfMissing<IEvent>(events, ...(this.calendar?.events ?? []))))
      .subscribe((events: IEvent[]) => (this.eventsSharedCollection = events));
  }
}
