import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {ToastrManager} from 'ng6-toastr-notifications';
import {JwtHelperService} from '@auth0/angular-jwt';
import {UserService} from '../../Services/users/user.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  user: any;
  token: any;
  wrongUsernameOrPass: boolean;
  jwtHelper: any;

  constructor( private router: Router,  public toastr: ToastrManager, private userService: UserService) {
    this.jwtHelper = new JwtHelperService();
  }


  ngOnInit() {
    this.user = {username: '', password: ''};
    this.token = {accessToken: '', expiresIn: ''};

  }

  login() {
      this.wrongUsernameOrPass = false;
      const headers = new Headers();
      this.userService.login(this.user).subscribe(value => {
          headers.append('Authorization', value.headers.get('Authorization'));
          const userFromToken = this.jwtHelper.decodeToken(headers.get('Authorization'));
          userFromToken.token = headers.get('Authorization');
          // console.log(userFromToken.roles[0]['authority']);

          localStorage.setItem('loggedUser', JSON.stringify(userFromToken));
          /*if (userFromToken.roles[0]['authorization'] === 'TENANT') {
            this.router.navigate(['/login-after']);
            return;
          }*/
          location.reload();
          this.router.navigate(['/home']);
      }, error2 => {
          this.wrongUsernameOrPass = true;
          this.toastr.errorToastr('Invalid username or password!', 'Error');
      });

  }


}
