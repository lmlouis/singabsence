import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ILeave } from '../leave.model';
import { LeaveService } from '../service/leave.service';

@Component({
  standalone: true,
  templateUrl: './leave-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class LeaveDeleteDialogComponent {
  leave?: ILeave;

  constructor(
    protected leaveService: LeaveService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.leaveService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
