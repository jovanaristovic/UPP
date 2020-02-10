import {Component, EventEmitter, Input, OnInit} from '@angular/core';
import {UserService} from '../../Services/users/user.service';
import {RepositoryService} from '../../Services/repository/repository.service';
import {Router} from '@angular/router';
import {RegistrationComponent} from '../registration/registration.component';
import {ToastrManager} from 'ng6-toastr-notifications';

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

    constructor(public toastr: ToastrManager, private userService: UserService, private repositoryService: RepositoryService, private router: Router) {


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

                this.toastr.successToastr('Success!', 'Success');
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
