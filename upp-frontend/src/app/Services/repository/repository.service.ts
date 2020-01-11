import { Injectable } from '@angular/core';

import { HttpClient } from '@angular/common/http';


import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class RepositoryService {

  categories = [];
  languages = [];
  books = [];

  constructor(private httpClient: HttpClient) {


  }


  startProcess() {
    return this.httpClient.get('http://localhost:8080/welcome/get') as Observable<any>;
  }

  // getTasks(processInstance: string) {
  //
  //   return this.httpClient.get('http://localhost:8080/welcome/get/tasks/'.concat(processInstance)) as Observable<any>;
  // }
  //
  // claimTask(taskId) {
  //   return this.httpClient.post('http://localhost:8080/welcome/tasks/claim/'.concat(taskId), null) as Observable<any>;
  // }
  //
  // completeTask(taskId) {
  //   return this.httpClient.post('http://localhost:8080/welcome/tasks/complete/'.concat(taskId), null) as Observable<any>;
  // }

}
