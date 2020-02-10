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

    getTaskForCorrection(processInstanceId) {
        return this.httpClient.get('api/welcome/get/formForCorrection/'.concat(processInstanceId)) as Observable<any>;

    }


    startProcessTextEdit(username) {
        return this.httpClient.get('api/get/start/process/'.concat(username)) as Observable<any>;

    }

    getJournals(processInstanceId) {
        return this.httpClient.get('api/get/journals/'.concat(processInstanceId)) as Observable<any>;

    }
    getNextTaskJournal(processInstanceId) {
        return this.httpClient.get('api/get/next/'.concat(processInstanceId)) as Observable<any>;

    }
    getPodproces(processInstanceId) {
        return this.httpClient.get('api/get/next/podproces/'.concat(processInstanceId)) as Observable<any>;

    }

    getScientificFields(processInstanceId) {
        return this.httpClient.get('api/get/scientificFields/'.concat(processInstanceId)) as Observable<any>;

    }

    getTaskForRedactor(procesInstanceId) {
        return this.httpClient.get('api/get/main/redactor/'.concat(procesInstanceId)) as Observable<any>;

    }

    getNextIspravljanjePodataka(processInstanceId) {
        return this.httpClient.get('api/get/next/ispravljenje/podataka/'.concat(processInstanceId)) as Observable<any>;

    }

    getUrednikNaucneTasks(processInstanceId) {
        return this.httpClient.get('api/get/next/urednik/naucne/'.concat(processInstanceId)) as Observable<any>;

    }

    getRecenzenti(processInstanceId) {
        return this.httpClient.get('api/get/recenzenti/'.concat(processInstanceId)) as Observable<any>;

    }

    getTasksRecenzent(processInstanceId) {
        return this.httpClient.get('api/get/recenzent/'.concat(processInstanceId)) as Observable<any>;

    }
}
