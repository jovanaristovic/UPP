import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {UserService} from '../../Services/users/user.service';



@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  userRole: any;
  user: any;
  constructor( private router: Router, private userService: UserService) { }

  ngOnInit() {

    this.userRole = this.userService.getLoggedUserType();
    console.log(this.userRole);
 //   const userTemp = JSON.parse(localStorage.getItem('loggedUser'));
    // if (userTemp !== null) {
    //   this.userService.getUserByUsername(userTemp.sub).subscribe(user => {
    //     this.user = user;
    //   });
    // }
  }


  logout() {
    localStorage.removeItem('loggedUser');
    this.userRole = '';
    this.router.navigate(['/login']);
  }

}
