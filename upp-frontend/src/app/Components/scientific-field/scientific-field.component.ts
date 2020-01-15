import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {RepositoryService} from '../../Services/repository/repository.service';
import {sha1} from '@angular/compiler/src/i18n/digest';
import {UserService} from '../../Services/users/user.service';

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
  constructor(private repoService: RepositoryService, private router: Router,  private userService: UserService, private repositoryService: RepositoryService ) {
      this.href = this.router.url;

      this.procesInstanceId = this.href.split('/')[2];
      const x = repoService.getNext(this.procesInstanceId);

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

        // this.href = this.router.url;
        // // @ts-ignore
        // this.taskId = this.href.split('/')[2];
        // console.log(this.taskId);
        // this.repoService.getForm(this.taskId).subscribe(
        //     (response: any) => {
        //         this.formFieldsDto = response;
        //         this.procesInstanceId = response.processInstanceId;
        //         this.formFields = response.formFields;
        //         this.formFields.forEach( (field) => {
        //             if (field.type.name === 'enum') {
        //                 this.enumValues = Object.keys(field.type.values);
        //             }
        //         });
        //
        //     },
        //     (error) => {alert(error); }
        // );
    }

    onSubmit(value, form) {
        const o = new Array();
        for (const property in value) {
            // console.log(property);
            // console.log(value[property]);
            o.push({fieldId : property, fieldValue : value[property]});
        }

        // console.log(o);
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
                console.log('Error occured');
            }
        );
    }

    getAnotherTask() {
        this.href = this.router.url;

        this.procesInstanceId = this.href.split('/')[2];
        const x = this.repoService.getNext(this.procesInstanceId);

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

    // getTasks() {
    //     const x = this.repoService.getTasks(this.procesInstanceId);
    //
    //     x.subscribe(
    //         res => {
    //             console.log(res);
    //             this.tasks = res;
    //             console.log(this.tasks);
    //             // this.nextTask = this.tasks[0].taskId;
    //             // console.log(this.nextTask);
    //             // this.router.navigate(['/task', this.nextTask]);
    //         },
    //         err => {
    //             console.log('Error occured');
    //         }
    //     );
    // }

}
