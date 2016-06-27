import { provideRouter, RouterConfig } from '@angular/router';

import { DashboardRoutes } from './dashboard/dashboard.routes'
import { LoginComponent } from './login.component'
import { AuthenticationService } from './service/auth.service';
import { AuthGuard } from './auth.guard'

export const routes: RouterConfig = [
    ...DashboardRoutes,
    {
        path: 'login',
        component: LoginComponent
    }
];

export const APP_ROUTER_PROVIDERS = [
    provideRouter(routes),
    AuthGuard,
    AuthenticationService
];