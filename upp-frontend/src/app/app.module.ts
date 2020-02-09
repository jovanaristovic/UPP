import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { RegistrationComponent } from './Components/registration/registration.component';
import {NavbarComponent} from './Components/navbar/navbar.component';
import {HomeComponent} from './Components/home/home.component';
import {AppRoutingModule} from './app-routing.module';
import {FormsModule} from '@angular/forms';
import {HttpClientModule, HTTP_INTERCEPTORS} from '@angular/common/http';
import {LoginComponent} from './Components/login/login.component';
import {CanActivateService} from './Services/security/can-activate.service';
import {TokenInterceptorService} from './Services/security/token-interceptor';
import {ToastrModule} from 'ng6-toastr-notifications';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {RepositoryComponent} from './Components/repository/repository.component';
import {Notauthorized} from './Components/guard/notauthorized.guard';
import {HttpModule} from '@angular/http';
import {RouterModule} from '@angular/router';
import { ScientificFieldComponent } from './Components/scientific-field/scientific-field.component';
import { ActivateUserComponent } from './Components/activate-user/activate-user.component';
import { AdminComponent } from './Components/admin/admin.component';
import { JournalComponent } from './Components/journal/journal.component';
import { JournalNextTasksComponent } from './Components/journal-next-tasks/journal-next-tasks.component';
import { AdminJournalsComponent } from './Components/admin-journals/admin-journals.component';
import { FormForDataCorrectionComponent } from './Components/form-for-data-correction/form-for-data-correction.component';
import { TextEditComponent } from './Components/text-edit/text-edit.component';
import { PregledRadovaComponent } from './Components/pregled-radova/pregled-radova.component';
import { KorekcijaPodatakaORaduComponent } from './Components/korekcija-podataka-oradu/korekcija-podataka-oradu.component';


const Routes = [
    {
        path: 'registrate',
        component: RegistrationComponent,
        canActivate: [Notauthorized]
    }
];

@NgModule({
  declarations: [
    AppComponent,
    RegistrationComponent,
    NavbarComponent,
    HomeComponent,
    LoginComponent,
      RepositoryComponent,
      ScientificFieldComponent,
      ActivateUserComponent,
      AdminComponent,
      JournalComponent,
      JournalNextTasksComponent,
      AdminJournalsComponent,
      FormForDataCorrectionComponent,
      TextEditComponent,
      PregledRadovaComponent,
      KorekcijaPodatakaORaduComponent
  ],
  imports: [
      BrowserModule,
      AppRoutingModule,
      HttpClientModule,
      FormsModule,
      ToastrModule.forRoot(),
      BrowserAnimationsModule,
      RouterModule.forRoot(Routes),
      HttpClientModule,
      HttpModule


  ],
  providers: [
      CanActivateService,
      {
          provide: HTTP_INTERCEPTORS,
          useClass: TokenInterceptorService,
          multi: true
      }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
