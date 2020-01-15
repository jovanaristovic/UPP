import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {RepositoryService} from '../../Services/repository/repository.service';
import {sha1} from '@angular/compiler/src/i18n/digest';
import {UserService} from '../../Services/users/user.service';

@Component({
  selector: 'app-scientific-field',
  templateUrl: './scientific-field.component.html',
  styleUrls: ['./scientific-field.component.css']
})
export class ScientificFieldComponent implements OnInit {


   taskId = '';
   href = '';
   formFieldsDto = null;
   formFields = [];
   enumValues = [];
   procesInstanceId = '';
   tasks = [];
  constructor(private repoService: RepositoryService, private router: Router,  private userService: UserService, private repositoryService: RepositoryService ) {

  }

    ngOnInit() {

        this.href = this.router.url;
        // @ts-ignore
        this.taskId = this.href.split('/')[2];
        console.log(this.taskId);
        this.repoService.getForm(this.taskId).subscribe(
            (response: any) => {
                this.formFieldsDto = response;
                this.procesInstanceId = response.processInstanceId;
                this.formFields = response.formFields;
                this.formFields.forEach( (field) => {
                    if (field.type.name === 'enum') {
                        this.enumValues = Object.keys(field.type.values);
                    }
                });

            },
            (error) => {alert(error); }
        );
    }

    claimTask(taskId) {
        // this.adminService.claimTask(taskId).subscribe(
        //     (success) => {
        //         this.router.navigate(['/admin/' + taskId]);
        //     },
        //     (error) => {alert(error); }
        // );


    }
    onSubmit(value, form) {

    }

    getTasks() {
        const x = this.repoService.getTasks(this.procesInstanceId);

        x.subscribe(
            res => {
                console.log(res);
                this.tasks = res;
                console.log(this.tasks);
                // this.nextTask = this.tasks[0].taskId;
                // console.log(this.nextTask);
                // this.router.navigate(['/task', this.nextTask]);
            },
            err => {
                console.log('Error occured');
            }
        );
    }

}
