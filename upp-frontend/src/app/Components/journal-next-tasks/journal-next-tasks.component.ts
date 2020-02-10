import { Component, OnInit } from '@angular/core';
import {RepositoryService} from '../../Services/repository/repository.service';
import {Router} from '@angular/router';
import {UserService} from '../../Services/users/user.service';
import {ToastrManager} from 'ng6-toastr-notifications';

@Component({
  selector: 'app-journal-next-tasks',
  templateUrl: './journal-next-tasks.component.html',
  styleUrls: ['./journal-next-tasks.component.css']
})
export class JournalNextTasksComponent implements OnInit {
    taskId = '';
    href = '';
    formFieldsDto = null;
    formFields = [];
    enumValues = [];
    procesInstanceId = '';
    tasks = [];
    processInstanceId = '';
    dodavanjeNaucnih = false;
    dodavanjeUrednikaNaucne = false;
    dodavanjeRecenzenta = false;

  constructor(public toastr: ToastrManager, private repoService: RepositoryService, private router: Router,  private userService: UserService) {
      this.getAnotherTask();
  }

  ngOnInit() {
  }
    onSubmit(value, form) {
        const userTemp = JSON.parse(localStorage.getItem('loggedUser'));
        const o = new Array();
        for (const property in value) {

            o.push({fieldId : property, fieldValue : value[property]});

            if (property === 'naucnaOblast') {
                this.dodavanjeNaucnih = true;
            } else if (property === 'urednik') {
                this.dodavanjeUrednikaNaucne = true;

            } else if ( property === 'recenzent') {
                this.dodavanjeRecenzenta = true;
            }

        }

        if (this.dodavanjeNaucnih === true) {
            const x = this.userService.postScientificFieldJournal(o, this.formFieldsDto.taskId);

            x.subscribe(
                res => {
                    // console.log(res);

                    this.toastr.successToastr('Success!', 'Success');
                    // this.getTasks();
                    // this.router.navigate(['/task',  this.processInstanceId]);
                    this.getAnotherTask();
                    location.reload();


                },
                err => {
                    console.log('Value of field is empty or not valid!');
                    alert('Value of field is empty or not valid!');
                }
            );
        } else if (this.dodavanjeUrednikaNaucne === true) {

            const x = this.userService.postAddRedactor(o, this.formFieldsDto.taskId);

            x.subscribe(
                res => {
                    // console.log(res);

                    this.toastr.successToastr('Success!', 'Success');
                    // this.getTasks();
                    // this.router.navigate(['/task',  this.processInstanceId]);

                    this.getAnotherTask();
                    location.reload();

                },
                err => {
                    console.log('Value of field is empty or not valid!');
                    alert('Value of field is empty or not valid!');
                }
            );

        } else if (this.dodavanjeRecenzenta === true) {
            const x = this.userService.postAddReviewer(o, this.formFieldsDto.taskId);

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
                    console.log('Value of field is empty or not valid!');
                    alert('Value of field is empty or not valid!');
                }
            );
        }

    }

    getAnotherTask() {

        this.href = this.router.url;

        this.procesInstanceId = this.href.split('/')[2];
        const x = this.repoService.getNext(this.procesInstanceId);

        x.subscribe(
            res => {
                this.taskId = res.taskId;
                this.formFieldsDto = res;
                this.formFields = res.formFields;
                this.processInstanceId = res.processInstanceId;
                this.formFields.forEach( (field) => {

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

}
