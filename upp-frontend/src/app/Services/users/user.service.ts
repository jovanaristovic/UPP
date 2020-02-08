import { Injectable } from '@angular/core';

import { Headers, RequestOptions, ResponseContentType } from '@angular/http';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Http, Response } from '@angular/http';

import { Observable } from 'rxjs';
import {map} from 'rxjs/operators';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private httpClient: HttpClient, private router: Router) { }

  fetchUsers() {
    return this.httpClient.get('api/user/fetch') as Observable<any>;
  }

  // registerUser(user, taskId) {
  //   return this.httpClient.post('api/users/register/'.concat(taskId), user) as Observable<any>;
  // }

    registerUserDummy(user, taskId) {
        return this.httpClient.post('api/welcome/post/register/'.concat(taskId), user) as Observable<any>;
    }

    postScientificField(scientificField, taskId) {
        return this.httpClient.post('api/welcome/post/scientificField/'.concat(taskId), scientificField) as Observable<any>;

    }
    postScientificFieldJournal(scientificField, taskId) {
        console.log('udje u naucno polje');

        return this.httpClient.post('api/welcome/post/scientificField/journal/'.concat(taskId), scientificField) as Observable<any>;

    }

    postActivateUser(activateUser, taskId) {
        return this.httpClient.post('api/welcome/post/activateUser/'.concat(taskId), activateUser) as Observable<any>;

    }

    postAcceptReviewer(acceptReviewer, taskId){
        return this.httpClient.post('api/welcome/post/acceptReviewer/'.concat(taskId), acceptReviewer) as Observable<any>;

    }
    postJournalReview(review, taskId){
        return this.httpClient.post('api/welcome/post/journalReview/'.concat(taskId), review) as Observable<any>;

    }

    postCorrectedJournal(journal, taskId){
        return this.httpClient.post('api/welcome/post/correctedJournal/'.concat(taskId), journal) as Observable<any>;

    }


    postCreateJournal(journal, taskId, redactor) {
        return this.httpClient.post('api/welcome/post/createJournal/'.concat(taskId).concat('/').concat(redactor), journal) as Observable<any>;

    }

    postAddRedactor(redactor, taskId) {
        return this.httpClient.post('api/welcome/post/redactor/'.concat(taskId), redactor) as Observable<any>;

    }
    postAddReviewer(reviewer, taskId) {
        return this.httpClient.post('api/welcome/post/reviewer/'.concat(taskId), reviewer) as Observable<any>;

    }

    chooseJournal(journal, taskId) {
        return this.httpClient.post('api/post/choose/journal/'.concat(taskId), journal) as Observable<any>;

    }

    pay(user, taskId) {
        return this.httpClient.post('api/post/pay/'.concat(taskId), user ) as Observable<any>;

    }

    // postNewWork(taskId, work) {
    //     return this.httpClient.post('api/post/new/work/'.concat(taskId), work) as Observable<any>;
    //
    // }

    postKoautor(taskId, koautor) {
        return this.httpClient.post('api/post/add/koautor/'.concat(taskId), koautor) as Observable<any>;

    }
    postFileUpload(y, taskId) {
        return this.httpClient.post('api/post/upload/file/'.concat(taskId), y) as Observable<any>;

    }


    getToken(): string {
        const currentUser = JSON.parse(localStorage.getItem('loggedUser'));
        if (currentUser !== null) {
            currentUser.token = currentUser.token.replace(':', '');
        }
        const token = currentUser && currentUser.token;
        return token ? token : '';
    }

    getLoggedUserType() {
        const user = JSON.parse(localStorage.getItem('loggedUser'));
        let userRole;
        if (user === null) {
            userRole = '';
        } else {
            for (const role of user.roles) {
                if (role === 'ADMIN') {
                    userRole = 'ADMIN';
                } else if (role === 'ROLE_USER') {
                    userRole = 'ROLE_USER';
                } else if (role === 'REDACTOR') {
                    userRole = 'REDACTOR';
                }
            }
        }
        return userRole;
    }

    isLoggedIn() {
        const user = JSON.parse(localStorage.getItem('loggedUser'));

        if (user === null) {
            return false;
        }
        const expiredDate = new Date(new Date(parseInt(user.exp, 10) * 1000));
        const nowDate = new Date();
        if ((expiredDate.getDate() <= nowDate.getDate()) &&
            (expiredDate.getTime() <= nowDate.getTime())) {
            this.logout();
            return false;
        }
        return true;

    }

    login(user): any {
        return this.httpClient.post('api/auth/login', user, {observe: 'response'}).pipe(map(response => response));
    }


    getUserByUsername(username): any {
        return this.httpClient.get('api/users/'.concat(username));
    }

    logout() {
        localStorage.clear();
        this.router.navigate(['/login']);
    }
}
