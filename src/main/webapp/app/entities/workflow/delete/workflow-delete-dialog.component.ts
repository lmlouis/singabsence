import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IWorkflow } from '../workflow.model';
import { WorkflowService } from '../service/workflow.service';

@Component({
  standalone: true,
  templateUrl: './workflow-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class WorkflowDeleteDialogComponent {
  workflow?: IWorkflow;

  constructor(
    protected workflowService: WorkflowService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.workflowService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
