import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ICalendar, NewCalendar } from '../calendar.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICalendar for edit and NewCalendarFormGroupInput for create.
 */
type CalendarFormGroupInput = ICalendar | PartialWithRequiredKeyOf<NewCalendar>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ICalendar | NewCalendar> = Omit<T, 'createdat'> & {
  createdat?: string | null;
};

type CalendarFormRawValue = FormValueOf<ICalendar>;

type NewCalendarFormRawValue = FormValueOf<NewCalendar>;

type CalendarFormDefaults = Pick<NewCalendar, 'id' | 'createdat' | 'events'>;

type CalendarFormGroupContent = {
  id: FormControl<CalendarFormRawValue['id'] | NewCalendar['id']>;
  title: FormControl<CalendarFormRawValue['title']>;
  summury: FormControl<CalendarFormRawValue['summury']>;
  createdat: FormControl<CalendarFormRawValue['createdat']>;
  events: FormControl<CalendarFormRawValue['events']>;
};

export type CalendarFormGroup = FormGroup<CalendarFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CalendarFormService {
  createCalendarFormGroup(calendar: CalendarFormGroupInput = { id: null }): CalendarFormGroup {
    const calendarRawValue = this.convertCalendarToCalendarRawValue({
      ...this.getFormDefaults(),
      ...calendar,
    });
    return new FormGroup<CalendarFormGroupContent>({
      id: new FormControl(
        { value: calendarRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      title: new FormControl(calendarRawValue.title),
      summury: new FormControl(calendarRawValue.summury),
      createdat: new FormControl(calendarRawValue.createdat),
      events: new FormControl(calendarRawValue.events ?? []),
    });
  }

  getCalendar(form: CalendarFormGroup): ICalendar | NewCalendar {
    return this.convertCalendarRawValueToCalendar(form.getRawValue() as CalendarFormRawValue | NewCalendarFormRawValue);
  }

  resetForm(form: CalendarFormGroup, calendar: CalendarFormGroupInput): void {
    const calendarRawValue = this.convertCalendarToCalendarRawValue({ ...this.getFormDefaults(), ...calendar });
    form.reset(
      {
        ...calendarRawValue,
        id: { value: calendarRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CalendarFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      createdat: currentTime,
      events: [],
    };
  }

  private convertCalendarRawValueToCalendar(rawCalendar: CalendarFormRawValue | NewCalendarFormRawValue): ICalendar | NewCalendar {
    return {
      ...rawCalendar,
      createdat: dayjs(rawCalendar.createdat, DATE_TIME_FORMAT),
    };
  }

  private convertCalendarToCalendarRawValue(
    calendar: ICalendar | (Partial<NewCalendar> & CalendarFormDefaults),
  ): CalendarFormRawValue | PartialWithRequiredKeyOf<NewCalendarFormRawValue> {
    return {
      ...calendar,
      createdat: calendar.createdat ? calendar.createdat.format(DATE_TIME_FORMAT) : undefined,
      events: calendar.events ?? [],
    };
  }
}
