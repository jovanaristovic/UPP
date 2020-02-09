import { Component, OnInit } from '@angular/core';
import {UserService} from '../../Services/users/user.service';
import {RepositoryService} from '../../Services/repository/repository.service';
import {ActivatedRoute, Router} from '@angular/router';
import {ɵAnimationRendererFactory} from '@angular/platform-browser/animations';
import {Observable} from 'rxjs';
import {RequestOptions} from '@angular/http';
import {FormSubmissionWithFileDto} from '../model/FormSubmissionWithFileDto';

@Component({
  selector: 'app-text-edit',
  templateUrl: './text-edit.component.html',
  styleUrls: ['./text-edit.component.css']
})
export class TextEditComponent implements OnInit {

    private formFieldsDto = null;
    private formFields = [];
    private processInstanceId = '';
    private enumValues = [];
    private enumValuesScientFields = [];

    private tasks = [];
    private nextTask = '';
    taskId = '';
    journals: any;
    scientificFields: any;
    ulogovan: boolean;
    prikaziPoruku: boolean;
    uplataClanarine = false;
    noviCasopis = false;
    izborCasopisa = false;
    prviEnum = true;
    drugiEnum = false;
    dodavanjeKoautora = false;
    file: File;
    trebajuNaucne = false;
    proveraDaLiTrebaReg = false;
    potrebnaReg = false;


    private fileField = null;
    private fileName = null;


    constructor(private userService: UserService, private repositoryService: RepositoryService, private router: Router, private route: ActivatedRoute) {
      this.ulogovan = false;
      this.prikaziPoruku = true;

      const userTemp = JSON.parse(localStorage.getItem('loggedUser'));
      if (userTemp !== null) {
          this.ulogovan = true;
          this.prikaziPoruku = false;
      }

      let salji = '';
      if (userTemp === null) {
          salji = 'nema';
      } else {
          salji = userTemp.sub;
      }
      const x = repositoryService.startProcessTextEdit(salji);

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

              localStorage.setItem('processInstanceId', JSON.stringify(this.processInstanceId));
              if (userTemp !== null) {
              this.repositoryService.getJournals(this.processInstanceId).subscribe(journals => {
                  this.journals = journals;

                  this.journals.forEach((field) => {
                      this.enumValues.push(field.title);
                      // console.log(this.enumValues);
                  });

              });
             } else {
                  this.ulogovan = true;
                  this.getAnotherTask();
              }

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
      this.izborCasopisa = false;
      this.uplataClanarine = false;
      this.dodavanjeKoautora = false;
      this.noviCasopis = false;
      for (const property in value) {
          // console.log(property);
          o.push({fieldId : property, fieldValue : value[property]});

          if (property === 'title') {
                this.izborCasopisa = true;
            } else if (property === 'uplata') {
                this.uplataClanarine = true;
            } else if (property === 'apstrakt') {

                this.noviCasopis = true;

            } else if (property === 'imeKoautora') {
                this.dodavanjeKoautora = true;
            } else if (property === 'potrebnaRegistracija') {
              this.potrebnaReg = value[property];
          }

        }
      if (this.izborCasopisa === true) {
            const x = this.userService.chooseJournal(o, this.formFieldsDto.taskId);

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
        } else if (this.uplataClanarine === true) {
            const x = this.userService.pay(o, this.formFieldsDto.taskId);

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
        } else if (this.noviCasopis === true) {

          const y = new FormSubmissionWithFileDto(o, this.fileField.toString(), this.fileName.toString());

          const xf = this.userService.postFileUpload(y, this.formFieldsDto.taskId);

          xf.subscribe(
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
        } else if (this.dodavanjeKoautora === true) {
          const x = this.userService.postKoautor(this.formFieldsDto.taskId, o);

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
      } else if (this.proveraDaLiTrebaReg === true) {

          if (this.potrebnaReg !== true) {
              this.router.navigate(['/login']);

          } else {
          const x = this.userService.postReg(this.formFieldsDto.taskId, o);


          x.subscribe(
              res => {
                  alert('Success');
                  this.router.navigate(['/register']);

              },
              err => {
                  console.log('Error occured');
                  alert('Value of field is empty or not valid!');

              }
          );
      }
      }
    }

  registracija() {
      this.router.navigate(['/register']);

  }

    getAnotherTask() {
        this.prviEnum = false;
        this.drugiEnum = true;
        const x = this.repositoryService.getNextTaskJournal(this.processInstanceId);
        this.noviCasopis = true;

        x.subscribe(
            res => {
                if (res === null) {
                    this.router.navigate(['/home']);
                } else {
                this.taskId = res.taskId;

                this.formFieldsDto = res;
                this.formFields = res.formFields;

                for (const field of this.formFields) {
                    if (field.id === 'apstrakt') {
                        this.trebajuNaucne = true;
                    } else if (field.id === 'potrebnaRegistracija') {
                        this.proveraDaLiTrebaReg = true;
                    }
                }


                this.processInstanceId = res.processInstanceId;
                const userTemp = JSON.parse(localStorage.getItem('loggedUser'));


                if (userTemp !== null && this.trebajuNaucne === true) {
                    this.repositoryService.getScientificFields(this.processInstanceId).subscribe(scientificFields => {
                        this.scientificFields = scientificFields;

                        this.scientificFields.forEach((field) => {
                            this.enumValuesScientFields.push(field.name);
                        });

                    });
                }

            } },
            err => {
                console.log('Error occured');
            }
        );
    }

    getPodproces() {
        this.prviEnum = false;
        this.drugiEnum = true;
        const x = this.repositoryService.getPodproces(this.processInstanceId);
        this.noviCasopis = true;

        x.subscribe(
            res => {
                if (res === null) {
                    this.router.navigate(['/home']);
                } else {
                    this.taskId = res.taskId;

                    this.formFieldsDto = res;
                    this.formFields = res.formFields;


                    this.processInstanceId = res.processInstanceId;



                } },
            err => {
                console.log('Error occured');
            }
        );



    }


    fileChoserListener(files: FileList, field) {
        const fileToUpload = files.item(0);
        field.fileName = files.item(0).name;
        this.fileName = files.item(0).name;

        const fileReader = new FileReader();

        fileReader.onload = (e) => {

            field.value = fileReader.result;
            this.fileField = fileReader.result;
            console.log(fileReader.result);
        };

        fileReader.readAsDataURL(files.item(0));
    }


}
