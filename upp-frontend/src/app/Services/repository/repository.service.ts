import { Injectable } from '@angular/core';

import { Headers, RequestOptions, ResponseContentType } from '@angular/http';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Http, Response } from '@angular/http';

import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RepositoryService {

  categories = [];
  languages = [];
  books = [];

  constructor(private httpClient: HttpClient, private http: Http) {


  }


  startProcess() {
    return this.httpClient.get('api/welcome/get') as Observable<any>;
  }

    startProcessJournal() {
        return this.httpClient.get('api/welcome/get/createJournal') as Observable<any>;
    }

  getNext(procesInstanceId) {
      return this.httpClient.get('api/welcome/get/next/'.concat(procesInstanceId)) as Observable<any>;

  }

  getTaskForAdmin() {
      return this.httpClient.get('api/welcome/get/admin') as Observable<any>;

  }
    getTaskForAdminJournal() {
        return this.httpClient.get('api/welcome/get/admin/journal') as Observable<any>;

    }
  getTasks(processInstance: string) {

    return this.httpClient.get('api/welcome/get/tasks/'.concat(processInstance)) as Observable<any>;
  }

  claimTask(taskId) {
    return this.httpClient.post('api/welcome/tasks/claim/'.concat(taskId), null) as Observable<any>;
  }

  completeTask(taskId) {
    return this.httpClient.post('api/welcome/tasks/complete/'.concat(taskId), null) as Observable<any>;
  }

  getForm(taskId) {
    return this.httpClient.get('api/scientificField/get/form/'.concat(taskId)) as Observable<any>;
  }

}
