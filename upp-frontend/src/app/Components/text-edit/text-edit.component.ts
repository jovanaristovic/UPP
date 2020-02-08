import { Component, OnInit } from '@angular/core';
import {UserService} from '../../Services/users/user.service';
import {RepositoryService} from '../../Services/repository/repository.service';
import {Router} from '@angular/router';
import {ɵAnimationRendererFactory} from '@angular/platform-browser/animations';

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
    choosenMagazine: any;
    idProcess: any;
    uplataClanarine = false;
    noviCasopis = false;
    izborCasopisa = false;
    prviEnum = true;
    drugiEnum = false;
    dodavanjeKoautora = false;


  constructor(private userService: UserService, private repositoryService: RepositoryService, private router: Router) {
      this.ulogovan = false;
      this.prikaziPoruku = true;

      const userTemp = JSON.parse(localStorage.getItem('loggedUser'));
      if (userTemp !== null) {
          this.ulogovan = true;
          this.prikaziPoruku = false;
      }

      const x = repositoryService.startProcessTextEdit(userTemp.sub);

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

              this.repositoryService.getJournals(this.processInstanceId).subscribe(journals => {
                  this.journals = journals;

                  this.journals.forEach( (field) => {
                      this.enumValues.push(field.title);
                      // console.log(this.enumValues);
                  } );

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
      this.izborCasopisa = false;
      this.uplataClanarine = false;
      this.dodavanjeKoautora = false;
      this.noviCasopis = false;
      for (const property in value) {
          console.log(property);
            o.push({fieldId : property, fieldValue : value[property]});

            if (property === 'title') {
                this.izborCasopisa = true;
            } else if (property === 'uplata') {
                this.uplataClanarine = true;
            } else if (property === 'apstrakt') {

                this.noviCasopis = true;

            } else if (property === 'imeKoautora') {
                this.dodavanjeKoautora = true;
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
                    // this.getAnotherTask();
                },
                err => {
                    console.log('Error occured');
                    alert('Value of field is empty or not valid!');

                }
            );
        } else if (this.noviCasopis === true) {
            const x = this.userService.postNewWork(this.formFieldsDto.taskId, o);

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
      }
    }

  registracija() {
      this.router.navigate(['/register']);

  }

    getAnotherTask() {
        this.prviEnum = false;
        this.drugiEnum = true;
        const x = this.repositoryService.getNextTaskJournal(this.processInstanceId);

        x.subscribe(
            res => {
                this.taskId = res.taskId;
                this.formFieldsDto = res;
                this.formFields = res.formFields;
                this.processInstanceId = res.processInstanceId;

                this.repositoryService.getScientificFields(this.processInstanceId).subscribe(scientificFields => {
                    this.scientificFields = scientificFields;

                    this.scientificFields.forEach( (field) => {
                        this.enumValuesScientFields.push(field.name);
                        // console.log(this.enumValuesScientFields);
                    } );

                });

            },
            err => {
                console.log('Error occured');
            }
        );
    }


}
