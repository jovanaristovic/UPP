import {RouterModule, Routes} from '@angular/router';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {HomeComponent} from './Components/home/home.component';
import {RegistrationComponent} from './Components/registration/registration.component';
import {LoginComponent} from './Components/login/login.component';
import {ScientificFieldComponent} from './Components/scientific-field/scientific-field.component';
import {ActivateUserComponent} from './Components/activate-user/activate-user.component';
import {AdminComponent} from './Components/admin/admin.component';
import {JournalComponent} from './Components/journal/journal.component';
import {JournalNextTasksComponent} from './Components/journal-next-tasks/journal-next-tasks.component';
import {AdminJournalsComponent} from './Components/admin-journals/admin-journals.component';
import {FormForDataCorrectionComponent} from './Components/form-for-data-correction/form-for-data-correction.component';
import {TextEditComponent} from './Components/text-edit/text-edit.component';
import {PregledRadovaComponent} from './Components/pregled-radova/pregled-radova.component';

const appRoutes: Routes = [
    {path: '', redirectTo: '/home', pathMatch: 'full'},
    {path: 'home', component: HomeComponent},
    {path: 'register', component : RegistrationComponent},
    {path: 'login', component: LoginComponent},
    {path: 'task/:procesInstanceId', component : ScientificFieldComponent},
    {path: 'activate/:email', component: ActivateUserComponent},
    {path: 'admin', component: AdminComponent},
    {path: 'createJournal', component: JournalComponent},
    {path: 'nextTaskJournal/:procesInstanceId', component: JournalNextTasksComponent},
    {path: 'journalsAdmin', component: AdminJournalsComponent },
    {path: 'corectData/:procesInstanceId', component: FormForDataCorrectionComponent },
    {path: 'textEdit', component: TextEditComponent },
    {path: 'pregledRadova', component: PregledRadovaComponent}


];

@NgModule({
    declarations: [],
    imports: [RouterModule.forRoot(appRoutes, { useHash: true }), FormsModule],
    exports: [RouterModule]
})
export class AppRoutingModule { }
