import { Component, OnInit } from '@angular/core';
import {ToastrManager} from 'ng6-toastr-notifications';
import {UserService} from '../../Services/users/user.service';
import {RepositoryService} from '../../Services/repository/repository.service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-recenziranje',
  templateUrl: './recenziranje.component.html',
  styleUrls: ['./recenziranje.component.css']
})
export class RecenziranjeComponent implements OnInit {

    private formFieldsDto = null;
    private formFields = [];
    private processInstanceId = '';
    private enumValues = [];

    taskId = '';
    journals: any;

    dodavanjeRecenzenta = false;
    file: File;

    vremeRec = false;
  constructor(public toastr: ToastrManager, private userService: UserService, private repositoryService: RepositoryService, private router: Router, private route: ActivatedRoute) {

      const x = repositoryService.getTasksRecenzent(localStorage.getItem('processInstanceId'));

      x.subscribe(
          res => {
              this.taskId = res.taskId;
              this.formFieldsDto = res;
              this.formFields = res.formFields;
              this.processInstanceId = res.processInstanceId;

              // localStorage.setItem('processInstanceId', JSON.stringify(this.processInstanceId));
              this.formFields.forEach( (field) => {

                  if (field.type.name === 'enum') {
                      this.enumValues = Object.keys(field.type.values);
                  }
              });

              // this.repositoryService.getRecenzenti(this.processInstanceId).subscribe(journals => {
              //     this.journals = journals;
              //
              //     this.journals.forEach((field) => {
              //         this.enumValues.push(field.email);
              //         // console.log(this.enumValues);
              //     });
              //
              //
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
            // if (property === 'relevantan') {
            //     console.log(value[property]);
            //     if (value[property] === '') {
            //         this.odbijenOdma = true;
            //     }
            // }
            // console.log(property);
            // console.log(value[property]);
            o.push({fieldId: property, fieldValue: value[property]});
        }

        const x = this.userService.postRecenziranRad(this.formFieldsDto.taskId, o);
        x.subscribe(
                res => {
                    // console.log(res);
                    // if (res !== 'true') {
                    //     this.router.navigate(['/home']);
                    // }

                        this.getAnotherTask();

                    // this.router.navigate(['/task',  this.processInstanceId]);
                    // this.getAnotherTask();
                    // this.router.navigate(['/home']);

                },
                err => {
                    console.log('Error occured');
                }
            );

    }

    getAnotherTask() {

        const x = this.repositoryService.getTasksRecenzent(localStorage.getItem('processInstanceId'));
        x.subscribe(
            res => {
                // if (res === null) {
                //     this.router.navigate(['/home']);
                // }
                this.taskId = res.taskId;

                this.formFieldsDto = res;
                this.formFields = res.formFields;
                console.log(this.formFields);
                // for (const field of this.formFields) {
                //     if (field.id === 'pdfDobroFormatiran') {
                //         this.pregledanPdf = true;
                //     }
                // }


                this.processInstanceId = res.processInstanceId;




            } ,
            err => {
                console.log('Error occured');
            }
        );
    }

}
