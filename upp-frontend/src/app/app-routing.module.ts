import {RouterModule, Routes} from '@angular/router';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {HomeComponent} from './Components/home/home.component';
import {RegistrationComponent} from './Components/registration/registration.component';
import {LoginComponent} from './Components/login/login.component';
import {ScientificFieldComponent} from './Components/scientific-field/scientific-field.component';
import {ActivateUserComponent} from './Components/activate-user/activate-user.component';
import {AdminComponent} from './Components/admin/admin.component';

const appRoutes: Routes = [
    {path: '', redirectTo: '/home', pathMatch: 'full'},
    {path: 'home', component: HomeComponent},
    {path: 'register', component : RegistrationComponent},
    {path: 'login', component: LoginComponent},
    {path: 'task/:procesInstanceId', component : ScientificFieldComponent},
    {path: 'activate/:email', component: ActivateUserComponent},
    {path: 'admin', component: AdminComponent}

];

@NgModule({
    declarations: [],
    imports: [RouterModule.forRoot(appRoutes, { useHash: true }), FormsModule],
    exports: [RouterModule]
})
export class AppRoutingModule { }
