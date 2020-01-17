import { Component, OnInit } from '@angular/core';
import {UserService} from '../../Services/users/user.service';
import {RepositoryService} from '../../Services/repository/repository.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-form-for-data-correction',
  templateUrl: './form-for-data-correction.component.html',
  styleUrls: ['./form-for-data-correction.component.css']
})
export class FormForDataCorrectionComponent implements OnInit {

    private formFieldsDto = null;
    private formFields = [];
    private processInstanceId = '';
    private enumValues = [];
    private tasks = [];
    private nextTask = '';
    taskId = '';
    href = '';

  constructor(private userService: UserService, private repositoryService: RepositoryService, private router: Router) {

      this.href = this.router.url;
      this.processInstanceId = this.href.split('/')[2];
      const x = repositoryService.getTaskForCorrection(this.processInstanceId);
      console.log(this.processInstanceId);

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

        const x = this.userService.postCorrectedJournal(o, this.formFieldsDto.taskId);

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


    getAnotherTask() {

        this.href = this.router.url;

        this.processInstanceId = this.href.split('/')[2];
        const x = this.repositoryService.getNext(this.processInstanceId);

        x.subscribe(
            res => {
                this.taskId = res.taskId;
                this.formFieldsDto = res;
                this.formFields = res.formFields;
                this.processInstanceId = res.processInstanceId;
                this.formFields.forEach( (field) => {

                    if (field.type.name === 'enum') {
                        this.enumValues = Object.keys(field.type.values);
                    }
                });

            },
            err => {
                console.log('Error occured');
            }
        );
    }

}
