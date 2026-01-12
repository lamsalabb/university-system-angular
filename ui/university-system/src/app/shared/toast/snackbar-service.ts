import {Injectable} from '@angular/core';
import {MatSnackBar} from '@angular/material/snack-bar';

export type SnackbarType = 'success' | 'error' | 'info';

@Injectable({
  providedIn: 'root',
})
export class SnackbarService {
  constructor(private snackBar: MatSnackBar) {
  }

  show(
    message: string,
    type: SnackbarType = 'info',
    duration = 3000
  ) {
    this.snackBar.open(message, 'Close', {
      duration,
      horizontalPosition: 'center',
      verticalPosition: 'top',
      panelClass: [`snackbar-${type}`, 'snackbar-center']
    });
  }
}
