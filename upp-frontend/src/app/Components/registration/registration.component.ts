import {Component, Input, OnInit} from '@angular/core';
import {UserService} from '../../Services/users/user.service';
import {RepositoryService} from '../../Services/repository/repository.service';
import {Router} from '@angular/router';


@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

    private formFieldsDto = null;
    private formFields = [];
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

      },
      err => {
        console.log('Error occured');
      }
    );
   }

    getId() {
    return this.processInstanceId;

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
    const x = this.userService.registerUserDummy(o, this.formFieldsDto.taskId);

    x.subscribe(
      res => {
        // console.log(res);

        alert('You registered successfully!');
        this.router.navigate(['/task',  this.processInstanceId]);
      },
      err => {
        console.log('Error occured');
        alert('Value of field is empty or not valid!');

      }
    );
  }
}
