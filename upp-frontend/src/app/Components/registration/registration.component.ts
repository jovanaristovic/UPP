import { Component, OnInit } from '@angular/core';
import {UserService} from '../../Services/users/user.service';
import {RepositoryService} from '../../Services/repository/repository.service';


@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  // private repeated_password = '';
  private categories = [];
  private formFieldsDto = null;
  private formFields = [];
  // private choosen_category = -1;
  private processInstance = '';
  private enumValues = [];
  private tasks = [];

  constructor(private userService: UserService, private repositoryService: RepositoryService) {

    const x = repositoryService.startProcess();

    x.subscribe(
      res => {
        console.log(res);
        // this.categories = res;
        this.formFieldsDto = res;
        this.formFields = res.formFields;
        this.processInstance = res.processInstanceId;
        // this.formFields.forEach( (field) => {
        //
        //   if ( field.type.name === 'enum') {
        //     this.enumValues = Object.keys(field.type.values);
        //   }
        // });
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
      console.log(property);
      console.log(value[property]);
      o.push({fieldId : property, fieldValue : value[property]});
    }

    console.log(o);
    const x = this.userService.registerUser(o, this.formFieldsDto.taskId);

    x.subscribe(
      res => {
        console.log(res);

        alert('You registered successfully!');
        alert('You registered successfully!');
      },
      err => {
        console.log('Error occured');
      }
    );
  }

  getTasks() {
    const x = this.repositoryService.getTasks(this.processInstance);

    x.subscribe(
      res => {
        console.log(res);
        this.tasks = res;
      },
      err => {
        console.log('Error occured');
      }
    );
   }

   claim(taskId) {
    const x = this.repositoryService.claimTask(taskId);

    x.subscribe(
      res => {
        console.log(res);
      },
      err => {
        console.log('Error occured');
      }
    );
   }

   complete(taskId) {
    const x = this.repositoryService.completeTask(taskId);

    x.subscribe(
      res => {
        console.log(res);
        this.tasks = res;
      },
      err => {
        console.log('Error occured');
      }
    );
   }

}
