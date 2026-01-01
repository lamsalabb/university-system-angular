import { Routes } from '@angular/router';
import {LoginComponent} from './modules/auth/pages/login.component/login.component';
import {AdminDashboardComponent} from './modules/admin/pages/admin.dashboard.component/admin.dashboard.component';
import {StudentDashboardComponent} from './modules/student/pages/student.dashboard.component/student.dashboard.component';
import {
  InstructorDashboardComponent
} from './modules/instructor/pages/instructor.dashboard.component/instructor.dashboard.component';


export const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path:'admin', component:AdminDashboardComponent},
  {path:'student', component:StudentDashboardComponent},
  {path:'instructor', component:InstructorDashboardComponent},

  {path: '', redirectTo:'login', pathMatch:'full'}
];
