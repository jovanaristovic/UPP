import { Component, OnInit } from '@angular/core';
import {UserService} from '../../Services/users/user.service';
import {RepositoryService} from '../../Services/repository/repository.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-admin-journals',
  templateUrl: './admin-journals.component.html',
  styleUrls: ['./admin-journals.component.css']
})
export class AdminJournalsComponent implements OnInit {

    private formFieldsDto = null;
    private formFields = [];
    private processInstanceId = '';
    private enumValues = [];
    private tasks = [];
    private nextTask = '';
    taskId = '';

  constructor(private userService: UserService, private repositoryService: RepositoryService, private router: Router) {

     const x = repositoryService.getTaskForAdminJournal();

     x.subscribe(
          res => {
              console.log(res);
              this.taskId = res.taskId;
              this.formFieldsDto = res;
              this.formFields = res.formFields;

              // this.processInstanceId = res.processInstanceId;

          },
          err => {
              console.log('Error occured');
          }
      );
  }

  ngOnInit() {
  }


    onSubmit(value, form) {
        const o = new Array();
        for (const property in value) {

            // console.log(property);
            // console.log(value[property]);
            o.push({fieldId: property, fieldValue: value[property]});
        }

        const x = this.userService.postJournalReview(o, this.formFieldsDto.taskId);

        x.subscribe(
            res => {
                // console.log(res);

                alert('Success!');
                // this.getTasks();
                // this.router.navigate(['/task',  this.processInstanceId]);
                // this.getAnotherTask();
                this.router.navigate(['/home']);

            },
            err => {
                console.log('Error occured');
            }
        );
    }

}
