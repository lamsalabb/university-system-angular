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
import {ViewCourses} from './admin/courses/view-courses/view-courses';
import {RegisterCourse} from './admin/courses/register-course/register-course';
import {ViewFees} from './admin/fees/view-fees/view-fees';
import {RegisterFee} from './admin/fees/register-fee/register-fee';
import {ViewEnrollments} from './admin/enrollments/view-enrollments/view-enrollments';
import {ViewCoursesStudent} from './student/courses/view-courses-student/view-courses-student';
import {ViewFeesStudent} from './student/fees/view-fees-student/view-fees-student';
import {ViewProfileStudent} from './student/profile/view-profile-student/view-profile-student';
import {ViewProfileInstructor} from './instructor/profile/view-profile-instructor/view-profile-instructor';
import {ViewCoursesInstructor} from './instructor/courses/view-courses-instructor/view-courses-instructor';
import {ViewStudentsInstructor} from './instructor/students/view-students-instructor/view-students-instructor';
import {ViewAttendanceInstructor} from './instructor/attendance/view-attendance-instructor/view-attendance-instructor';
import {ViewAttendanceStudent} from './student/attendance/view-attendance-student/view-attendance-student';
import {MarkAttendanceInstructor} from './instructor/attendance/mark-attendance-instructor/mark-attendance-instructor';


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
      {path: 'admin/view-courses', component: ViewCourses, canActivate: [roleGuard(['ADMIN'])]},
      {path: 'admin/register-course', component: RegisterCourse, canActivate: [roleGuard(['ADMIN'])]},
      {path: 'admin/view-fees', component: ViewFees, canActivate: [roleGuard(['ADMIN'])]},
      {path: 'admin/register-fee', component: RegisterFee, canActivate: [roleGuard(['ADMIN'])]},
      {path: 'admin/view-enrollments', component: ViewEnrollments, canActivate: [roleGuard(['ADMIN'])]},

      {path: 'student/view-courses', component: ViewCoursesStudent, canActivate: [roleGuard(['STUDENT'])]},
      {path: 'student/view-fees', component: ViewFeesStudent, canActivate: [roleGuard(['STUDENT'])]},
      {path: 'student/view-profile', component: ViewProfileStudent, canActivate: [roleGuard(['STUDENT'])]},
      {path: 'student/view-attendance', component: ViewAttendanceStudent, canActivate: [roleGuard(['STUDENT'])]},


      {path: 'instructor/view-profile', component: ViewProfileInstructor, canActivate: [roleGuard(['INSTRUCTOR'])]},
      {path: 'instructor/view-courses', component: ViewCoursesInstructor, canActivate: [roleGuard(['INSTRUCTOR'])]},
      {path: 'instructor/view-students', component: ViewStudentsInstructor, canActivate: [roleGuard(['INSTRUCTOR'])]},
      {path: 'instructor/view-attendance', component: ViewAttendanceInstructor, canActivate: [roleGuard(['INSTRUCTOR'])]},
      {path: 'instructor/mark-attendance', component: MarkAttendanceInstructor, canActivate: [roleGuard(['INSTRUCTOR'])]},


      {
        path: 'unauthorized',
        component: Unauthorized
      }
    ]
  },

  {path: '**', component: NotFound}


];
