import { Component} from '@angular/core';
import { ROUTER_DIRECTIVES, ActivatedRoute } from '@angular/router';

import { AuthenticationService } from '../service/auth.service';

@Component({
    moduleId: module.id,
    directives: [ROUTER_DIRECTIVES],
    templateUrl: 'dashboard.component.html',
})
export class DashboardComponent {
    constructor(private _auth: AuthenticationService, private route: ActivatedRoute) { }

    isActive (path: string) {
        return window.location.pathname === path;
    }

    logOut() {
        this._auth.logout().subscribe();
    }
}
