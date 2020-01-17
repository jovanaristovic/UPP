import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {RepositoryService} from '../../Services/repository/repository.service';
import {UserService} from '../../Services/users/user.service';
import {prepareProfile} from 'selenium-webdriver/firefox';

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
   processInstanceId = '';
   potvrdaReg = false;

  constructor(private repoService: RepositoryService, private router: Router,  private userService: UserService) {
      this.getAnotherTask();
  }

    ngOnInit() {

    }

    onSubmit(value, form) {
        const o = new Array();
        for (const property in value) {

            if (property === 'potvrdaRegistracije') {
                this.potvrdaReg = true;
            }
            // console.log(property);
            // console.log(value[property]);
            o.push({fieldId : property, fieldValue : value[property]});
        }

        if (this.potvrdaReg === false) {
            const x = this.userService.postScientificField(o, this.formFieldsDto.taskId);

            x.subscribe(
                res => {
                    // console.log(res);

                    alert('Success!');
                    // this.getTasks();
                    // this.router.navigate(['/task',  this.processInstanceId]);
                    this.getAnotherTask();

                },
                err => {
                    console.log('Value of field is empty or not valid!');
                    alert('Value of field is empty or not valid!');
                }
            );
        } else {
            const x = this.userService.postActivateUser(o, this.formFieldsDto.taskId);

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
                    alert('Value of field is empty or not valid!');

                }
            );
        }
    }

    getAnotherTask() {
        this.href = this.router.url;

        this.procesInstanceId = this.href.split('/')[2];
        const x = this.repoService.getNext(this.procesInstanceId);

        x.subscribe(
            res => {
                this.taskId = res.taskId;
                this.formFieldsDto = res;
                this.formFields = res.formFields;
                this.processInstanceId = res.processInstanceId;

            },
            err => {
                console.log('Error occured');
            }
        );
    }


}
