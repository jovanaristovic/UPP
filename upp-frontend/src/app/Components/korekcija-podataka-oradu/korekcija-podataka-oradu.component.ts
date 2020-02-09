import { Component, OnInit } from '@angular/core';
import {UserService} from '../../Services/users/user.service';
import {RepositoryService} from '../../Services/repository/repository.service';
import {ActivatedRoute, Router} from '@angular/router';
import {FormSubmissionWithFileDto} from '../model/FormSubmissionWithFileDto';

@Component({
  selector: 'app-korekcija-podataka-oradu',
  templateUrl: './korekcija-podataka-oradu.component.html',
  styleUrls: ['./korekcija-podataka-oradu.component.css']
})
export class KorekcijaPodatakaORaduComponent implements OnInit {

    private formFieldsDto = null;
    private formFields = [];
    private processInstanceId = '';

    taskId = '';
    ulogovan: boolean;
    prikaziPoruku: boolean;
    file: File;


    private fileField = null;
    private fileName = null;

  constructor(private userService: UserService, private repositoryService: RepositoryService, private router: Router, private route: ActivatedRoute) {
      this.ulogovan = false;
      this.prikaziPoruku = true;



      // let salji = '';
      // if (userTemp === null) {
      //     salji = 'nema';
      // } else {
      //     salji = userTemp.sub;
      // }
      const x = repositoryService.getNextIspravljanjePodataka(localStorage.getItem('processInstanceId'));

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

              // localStorage.setItem('processInstanceId', JSON.stringify(this.processInstanceId));
              // if (userTemp !== null) {
              //     this.repositoryService.getJournals(this.processInstanceId).subscribe(journals => {
              //         this.journals = journals;
              //
              //         this.journals.forEach((field) => {
              //             this.enumValues.push(field.title);
              //             // console.log(this.enumValues);
              //         });
              //
              //     });
              // } else {
              //     this.ulogovan = true;
              //     this.getAnotherTask();
              // }

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

            // if (property === 'title') {
            //     this.izborCasopisa = true;
            // } else if (property === 'uplata') {
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
        // if (this.izborCasopisa === true) {
        const y = new FormSubmissionWithFileDto(o, this.fileField.toString(), this.fileName.toString());

        const x = this.userService.postFileUpload(y, this.formFieldsDto.taskId);

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
        // }
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
