import { Component, OnInit } from '@angular/core';
import {UserService} from '../../Services/users/user.service';
import {RepositoryService} from '../../Services/repository/repository.service';
import {ActivatedRoute, Router} from '@angular/router';
import {FormSubmissionWithFileDto} from '../model/FormSubmissionWithFileDto';

@Component({
  selector: 'app-urednik-naucne',
  templateUrl: './urednik-naucne.component.html',
  styleUrls: ['./urednik-naucne.component.css']
})
export class UrednikNaucneComponent implements OnInit {
    private formFieldsDto = null;
    private formFields = [];
    private processInstanceId = '';
    private enumValues = [];

    taskId = '';
    journals: any;

    dodavanjeRecenzenta = false;
    file: File;

    vremeRec = false;



  constructor(private userService: UserService, private repositoryService: RepositoryService, private router: Router, private route: ActivatedRoute) {

      const x = repositoryService.getUrednikNaucneTasks(localStorage.getItem('processInstanceId'));

      x.subscribe(
          res => {
              this.taskId = res.taskId;
              this.formFieldsDto = res;
              this.formFields = res.formFields;
              this.processInstanceId = res.processInstanceId;

              localStorage.setItem('processInstanceId', JSON.stringify(this.processInstanceId));


              this.repositoryService.getRecenzenti(this.processInstanceId).subscribe(journals => {
                      this.journals = journals;

                      this.journals.forEach((field) => {
                          this.enumValues.push(field.email);
                          // console.log(this.enumValues);
                      });


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
        const o = new Array();

        for (const property in value) {
            // console.log(property);
            o.push({fieldId : property, fieldValue : value[property]});

            if (property === 'vremeRecenziranja') {
                this.vremeRec = true;
            } else if (property === 'recenzent') {
                this.dodavanjeRecenzenta = true;
            }
            // else if (property === 'uplata') {
            //     this.uplataClanarine = true;
            // } else if (property === 'apstrakt') {
            //
            //     this.noviCasopis = true;
            //
            // } else if (property === 'imeKoautora') {
            //     this.dodavanjeKoautora = true;
            // } else if (property === 'potrebnaRegistracija') {
            //     this.potrebnaReg = value[property];
            // }

        }
        if (this.dodavanjeRecenzenta === true) {
            const x = this.userService.postDodavanjeRecenzenata(this.formFieldsDto.taskId, o);

            x.subscribe(
                res => {
                    // console.log(res);

                    alert('Success');
                    // this.router.navigate(['/task',  this.processInstanceId]);
                    this.getAnotherTask();
                },
                err => {
                    console.log('Error occured');
                    alert('Value of field is empty or not valid!');

                }
            );
        } else if (this.vremeRec === true) {
            const x = this.userService.postRokRecenzije(this.formFieldsDto.taskId, o);

            x.subscribe(
                res => {
                    // console.log(res);

                    alert('Success');
                    // this.router.navigate(['/task',  this.processInstanceId]);
                    // this.getAnotherTask();
                },
                err => {
                    console.log('Error occured');
                    alert('Value of field is empty or not valid!');

                }
            );
        } else {
            const x = this.userService.postBrojRecenzenata(this.formFieldsDto.taskId, o);

            x.subscribe(
                res => {
                    // console.log(res);

                    alert('Success');
                    // this.router.navigate(['/task',  this.processInstanceId]);
                    this.getAnotherTask();
                },
                err => {
                    console.log('Error occured');
                    alert('Value of field is empty or not valid!');

                }
            );
        }
        }


    getAnotherTask() {

        const x = this.repositoryService.getUrednikNaucneTasks(localStorage.getItem('processInstanceId'));
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
                //     if (field.id === 'recenzent') {
                //         this.dodavanjeRecenzenta = true;
                //     }
                // }


                this.processInstanceId = res.processInstanceId;
                const userTemp = JSON.parse(localStorage.getItem('loggedUser'));




            } ,
            err => {
                console.log('Error occured');
            }
        );
    }
    }



