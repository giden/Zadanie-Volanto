import { Component } from '@angular/core';
import { ROUTER_DIRECTIVES } from '@angular/router';

import { isLogged } from './service/auth.service';
import { LoginComponent } from './login.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import 'rxjs/Rx';

@Component({
	selector: 'my-app',
	directives: [ LoginComponent, DashboardComponent, ROUTER_DIRECTIVES ],
	template: `
		<router-outlet></router-outlet>
  `
})
export class AppComponent {
	isLogged() {
		return isLogged();
	}
}

