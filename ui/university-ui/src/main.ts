import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { ApiTestComponent} from './app/api-test/api-test'

bootstrapApplication(ApiTestComponent, appConfig)
  .catch(err => console.error(err));
