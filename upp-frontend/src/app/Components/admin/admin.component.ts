import {Component, EventEmitter, Input, OnInit} from '@angular/core';
import {UserService} from '../../Services/users/user.service';
import {RepositoryService} from '../../Services/repository/repository.service';
import {Router} from '@angular/router';
import {RegistrationComponent} from '../registration/registration.component';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

    private formFieldsDto = null;
    private formFields = [];
    private processInstanceId = '';
    private enumValues = [];
    private tasks = [];
    private nextTask = '';
    taskId = '';
    // @Input() processInstanceId: string;

    constructor(private userService: UserService, private repositoryService: RepositoryService, private router: Router) {



        // console.log(this.processInstanceId + ' prenetoooooo');
        const x = repositoryService.getTaskForAdmin();

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

        const x = this.userService.postAcceptReviewer(o, this.formFieldsDto.taskId);

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
