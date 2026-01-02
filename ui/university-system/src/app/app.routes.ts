import { Routes } from '@angular/router';
import {LoginComponent} from './modules/auth/pages/login.component/login.component';
import {PublicLayout} from './layout/public-layout/public-layout';
import {Layout} from './layout/layout/layout';
import {AdminDashboard} from './admin/dashboard/dashboard';
import {InstructorDashboard} from './instructor/dashboard/dashboard';
import {StudentDashboard} from './student/dashboard/dashboard';



export const routes: Routes = [
  {
    path: '',
    component: PublicLayout,
    children: [

      { path: 'login', component: LoginComponent },
      { path: '', redirectTo: 'login', pathMatch: 'full' }

    ]
  },

  {
    path: '',
    component: Layout,
    children: [
      { path: 'admin/dashboard', component: AdminDashboard },
      { path: 'student/dashboard', component: StudentDashboard },
      { path: 'instructor/dashboard', component: InstructorDashboard }
    ]
  },

  { path: '**', redirectTo: 'login' }
];
