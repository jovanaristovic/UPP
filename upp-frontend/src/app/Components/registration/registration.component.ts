import { Component, OnInit } from '@angular/core';
import {RepositoryService} from '../../Services/repository/repository.service';
import {UserService} from '../../Services/users/user.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

    private formFieldsDto = null;
    private formFields = [];
    private processInstance = '';
    private enumValues = [];
    private tasks = [];

  constructor(private userService: UserService, private repositoryService: RepositoryService) {

      // const x = repositoryService.startProcess();
      //
      // x.subscribe(
      //     res => {
      //         console.log(res);
      //         // this.categories = res;
      //         // this.formFieldsDto = res;
      //         // this.formFields = res.formFields;
      //         // this.processInstance = res.processInstanceId;
      //
      //     },
      //     err => {
      //         console.log('Error occured');
      //     }
      // );
  }

  ngOnInit() {
  }

}
