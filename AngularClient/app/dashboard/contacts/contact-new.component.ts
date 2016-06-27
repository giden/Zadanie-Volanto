import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
    moduleId: module.id,
    templateUrl: 'contact-new.component.html',
})
export class ContactNewComponent implements OnInit, OnDestroy {

    private contact: any;
    private subParam: any;

    constructor(private route: ActivatedRoute, private router: Router) { }

    ngOnInit() {
        this.subParam = this.route.params.subscribe(params => {
            let id = +params['id'];
            //this.service.getHero(id).subscribe(hercontacto => this.contact = contact);
        });
    }

    ngOnDestroy() {
        this.subParam.unsubscribe();
    }
}
