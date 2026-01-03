import {Routes} from '@angular/router';
import {LoginComponent} from './shared/login.component/login.component';
import {PublicLayout} from './layout/public-layout/public-layout';
import {Layout} from './layout/layout/layout';
import {AdminDashboard} from './admin/dashboard/dashboard';
import {InstructorDashboard} from './instructor/dashboard/dashboard';
import {StudentDashboard} from './student/dashboard/dashboard';
import {RegisterUser} from './admin/users/register-user/register-user';
import {roleGuard} from './guards/role.guard';
import {Unauthorized} from './shared/unauthorized/unauthorized';
import {NotFound} from './shared/not-found/not-found';
import {ViewUsers} from './admin/users/view-users/view-users';


export const routes: Routes = [
  {
    path: '',
    component: PublicLayout,
    children: [

      {path: 'login', component: LoginComponent},
      {path: '', redirectTo: 'login', pathMatch: 'full'},

    ]
  },

  {
    path: '',
    component: Layout,
    children: [
      {path: 'admin/dashboard', component: AdminDashboard, canActivate: [roleGuard(['ADMIN'])]},
      {path: 'student/dashboard', component: StudentDashboard, canActivate: [roleGuard(['STUDENT'])]},
      {path: 'instructor/dashboard', component: InstructorDashboard, canActivate: [roleGuard(['INSTRUCTOR'])]},

      {path: 'admin/view-users', component: ViewUsers, canActivate: [roleGuard(['ADMIN'])]},
      {path: 'admin/register-user', component: RegisterUser, canActivate: [roleGuard(['ADMIN'])]},
      {
        path: 'unauthorized',
        component: Unauthorized
      }
    ]
  },

  {path: '**', component: NotFound}


];
