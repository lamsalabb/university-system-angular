import {ApplicationConfig, importProvidersFrom, provideBrowserGlobalErrorListeners} from '@angular/core';
import {provideRouter} from '@angular/router';

import {routes} from './app.routes';
import {provideHttpClient, withInterceptors} from '@angular/common/http';
import {jwtInterceptor} from './core/interceptors/jwt-interceptor';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {provideCharts, withDefaultRegisterables} from 'ng2-charts';
import {MatSnackBarModule} from '@angular/material/snack-bar';

export const appConfig: ApplicationConfig = {
  providers: [
    provideHttpClient(withInterceptors([jwtInterceptor]),),
    provideBrowserGlobalErrorListeners(),
    provideRouter(routes),
    importProvidersFrom(NgbModule),
    provideCharts(withDefaultRegisterables()),
    importProvidersFrom(MatSnackBarModule)

  ]
};
