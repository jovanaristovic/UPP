import { Injectable } from '@angular/core';

import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable } from 'rxjs';
import {Router} from '@angular/router';
import {map} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private httpClient: HttpClient, private router: Router) { }

  // fetchUsers() {
  //   return this.httpClient.get('http://localhost:8080/user/fetch') as Observable<any>;
  // }
  //
  // registerUser(user, taskId) {
  //   return this.httpClient.post('http://localhost:8080/api/user/post/'.concat(taskId), user) as Observable<any>;
  // }

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
        return this.httpClient.put('http://localhost:8080/api/auth/login', user, {observe: 'response'}).pipe(map(response => response));
    }


    getUserByUsername(username): any {
        return this.httpClient.get('upp-backend/api/users/'.concat(username));
    }

    logout() {
        localStorage.clear();
        this.router.navigate(['/login']);
    }
}
