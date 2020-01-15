import { Component, OnInit } from '@angular/core';
import {UserService} from '../../Services/users/user.service';
import {RepositoryService} from '../../Services/repository/repository.service';
import {Router} from '@angular/router';


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
  private processInstanceId = '';
  private enumValues = [];
  private tasks = [];
  private nextTask = '';
  taskId = '';

  constructor(private userService: UserService, private repositoryService: RepositoryService, private router: Router) {

    const x = repositoryService.startProcess();

    x.subscribe(
      res => {
        // console.log(res);
        // this.categories = res;
        this.taskId = res.taskId;
        // console.log('dobijeno je ' + res);
        // console.log('id taska je: ' + this.taskId);
        this.formFieldsDto = res;
        this.formFields = res.formFields;
        this.processInstanceId = res.processInstanceId;
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
      // console.log(property);
      // console.log(value[property]);
      o.push({fieldId : property, fieldValue : value[property]});
    }

    // console.log(o);
    const x = this.userService.registerUserDammy(o, this.formFieldsDto.taskId);

    x.subscribe(
      res => {
        // console.log(res);

        alert('You registered successfully!');
        alert('You registered successfully!');
        // this.getTasks();
        this.router.navigate(['/task',  this.processInstanceId]);
      },
      err => {
        console.log('Error occured');
      }
    );
  }

  // getTasks() {
  //   const x = this.repositoryService.getTasks(this.processInstanceId);
  //
  //   x.subscribe(
  //     res => {
  //       // console.log(res);
  //       this.tasks = res;
  //       // console.log(this.tasks[0].taskId);
  //       this.nextTask = this.tasks[0].taskId;
  //       // this.claim(this.taskId);
  //       // this.complete(this.taskId);
  //       // console.log(this.nextTask);
  //       this.router.navigate(['/task', this.nextTask]);
  //     },
  //     err => {
  //       console.log('Error occured');
  //     }
  //   );
  //  }

   // claim(taskId) {
   //  const x = this.repositoryService.claimTask(taskId);
   //
   //  x.subscribe(
   //    res => {
   //      console.log(res);
   //    },
   //    err => {
   //      console.log('Error occured');
   //    }
   //  );
   // }

   // complete(taskId) {
   //  const x = this.repositoryService.completeTask(taskId);
   //
   //  x.subscribe(
   //    res => {
   //      // console.log(res);
   //      this.tasks = res;
   //    },
   //    err => {
   //      console.log('Error occured');
   //    }
   //  );
   // }

}
