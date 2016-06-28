import { provideRouter, RouterConfig } from '@angular/router';

import { UserListComponent } from './user-list.component'
import { AuthAdminGuard } from '../../authAdmin.guard'

export const UsersRoutes: RouterConfig = [
    {
        path: 'users',
        canActivate: [AuthAdminGuard],
        component: UserListComponent
    }
];