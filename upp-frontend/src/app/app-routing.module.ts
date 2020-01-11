import {RouterModule, Routes} from '@angular/router';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {HomeComponent} from './Components/home/home.component';
import {RegistrationComponent} from './Components/registration/registration.component';
import {LoginComponent} from './Components/login/login.component';

const appRoutes: Routes = [
    {path: '', redirectTo: '/home', pathMatch: 'full'},
    {path: 'home', component: HomeComponent},
    {path: 'register', component : RegistrationComponent},
    {path: 'login', component: LoginComponent}

];

@NgModule({
    declarations: [],
    imports: [RouterModule.forRoot(appRoutes, { useHash: true }), FormsModule],
    exports: [RouterModule]
})
export class AppRoutingModule { }
