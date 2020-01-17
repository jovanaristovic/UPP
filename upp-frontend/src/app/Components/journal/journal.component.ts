import { Component, OnInit } from '@angular/core';
import {UserService} from '../../Services/users/user.service';
import {RepositoryService} from '../../Services/repository/repository.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-journal',
  templateUrl: './journal.component.html',
  styleUrls: ['./journal.component.css']
})
export class JournalComponent implements OnInit {


    private formFieldsDto = null;
    private formFields = [];
    private processInstanceId = '';
    private enumValues = [];
    private tasks = [];
    private nextTask = '';
    taskId = '';

  constructor(private userService: UserService, private repositoryService: RepositoryService, private router: Router) {

      const x = repositoryService.startProcessJournal();

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
              this.formFields.forEach( (field) =>{

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

  ngOnInit() {
  }

    onSubmit(value, form) {
        const userTemp = JSON.parse(localStorage.getItem('loggedUser'));

        const o = new Array();
        for (const property in value) {
            // console.log(property);
            // console.log(value[property]);
            o.push({fieldId : property, fieldValue : value[property]});
        }

        console.log(o);
        const x = this.userService.postCreateJournal(o, this.formFieldsDto.taskId, userTemp.sub);

        x.subscribe(
            res => {
                // console.log(res);

                alert('You created new journal successfully!');
                this.router.navigate(['/nextTaskJournal',  this.processInstanceId]);
            },
            err => {
                console.log('Error occured');
                alert('Value of field is empty or not valid!');

            }
        );
    }

}
