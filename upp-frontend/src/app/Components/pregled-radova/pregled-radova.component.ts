import { Component, OnInit } from '@angular/core';
import {UserService} from '../../Services/users/user.service';
import {RepositoryService} from '../../Services/repository/repository.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-pregled-radova',
  templateUrl: './pregled-radova.component.html',
  styleUrls: ['./pregled-radova.component.css']
})
export class PregledRadovaComponent implements OnInit {


    private formFieldsDto = null;
    private formFields = [];
    private processInstanceId = '';
    private enumValues = [];
    private tasks = [];
    private nextTask = '';
    taskId = '';
    pregledanPdf: boolean;
    odbijenOdma = false;


  constructor(private userService: UserService, private repositoryService: RepositoryService, private router: Router) {
      this.pregledanPdf = false;
      const x = repositoryService.getTaskForRedactor(localStorage.getItem('processInstanceId'));
      // console.log(localStorage.getItem('processInstanceId'));
      x.subscribe(
          res => {
              // console.log(res);
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
      console.log(this.pregledanPdf);
      const o = new Array();
      for (const property in value) {
          console.log(property);
            if (property === 'relevantan') {
                console.log(value[property]);
                if (value[property] === '') {
                    this.odbijenOdma = true;
                }
            }
            // console.log(property);
            // console.log(value[property]);
            o.push({fieldId: property, fieldValue: value[property]});
        }

      if (this.pregledanPdf === true) {
            const x = this.userService.postPregledanPdf(this.formFieldsDto.taskId, o);

            x.subscribe(
                res => {
                    // console.log(res);
                    // if (res !== 'true') {
                    //     this.router.navigate(['/home']);
                    // }

                    // this.getTasks();
                    // this.router.navigate(['/task',  this.processInstanceId]);
                    // this.getAnotherTask();
                    this.router.navigate(['/home']);

                },
                err => {
                    console.log('Error occured');
                }
            );
        } else  {
            const x = this.userService.postWorkReview(this.formFieldsDto.taskId, o);
            x.subscribe(
                res => {
                    // console.log(res);
                    // if (res !== 'true') {
                    //     this.router.navigate(['/home']);
                    // }
                    if (this.odbijenOdma === false) {
                        this.getAnotherTask();
                    }
                    // this.router.navigate(['/task',  this.processInstanceId]);
                    // this.getAnotherTask();
                    // this.router.navigate(['/home']);

                },
                err => {
                    console.log('Error occured');
                }
            );
        }
    }


    getAnotherTask() {

        const x = this.repositoryService.getTaskForRedactor(localStorage.getItem('processInstanceId'));
        x.subscribe(
            res => {
                // if (res === null) {
                //     this.router.navigate(['/home']);
                // }
                    this.taskId = res.taskId;

                    this.formFieldsDto = res;
                    this.formFields = res.formFields;
                    console.log(this.formFields);
                    for (const field of this.formFields) {
                        if (field.id === 'pdfDobroFormatiran') {
                            this.pregledanPdf = true;
                        }
                    }


                    this.processInstanceId = res.processInstanceId;
                    const userTemp = JSON.parse(localStorage.getItem('loggedUser'));




                } ,
            err => {
                console.log('Error occured');
            }
        );
    }

}
