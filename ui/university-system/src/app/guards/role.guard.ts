import {inject} from '@angular/core';
import {CanActivateFn, Router} from '@angular/router';

export const roleGuard = (allowedRoles: string[]): CanActivateFn => {
  return () => {
    const router = inject(Router);
    const role = localStorage.getItem('role');
    const token = localStorage.getItem('token');

    // Not logged in
    if (!token || !role) {
      router.navigate(['/login']);
      return false;
    }

    // Logged in but wrong role
    if (!allowedRoles.includes(role)) {
      router.navigate(['/unauthorized']);
      return false;
    }

    return true;
  };
};
