import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  
  isLoggedUser: boolean = false;
  loggedUser: any;
  isUserJobSeeker: boolean = false;

  ngOnInit(): void {
    this.loggedDetails();
  }
  constructor(private router: Router) { }

  loggedDetails() {
    const localData = localStorage.getItem('token');
    if (localData == null) {
      this.isLoggedUser = false;
    } else {
      this.isLoggedUser = true;
    }

    this.isUserJobSeeker = localStorage.getItem('user') === 'USER';
    this.loggedUser = localStorage.getItem('user');
  }
  logOff() {
    localStorage.removeItem('token');
    alert("You have been logged out");
    this.isLoggedUser = false;
    this.router.navigateByUrl('/login').then(() => window.location.reload());
  }
  showProfile(){
    this.router.navigateByUrl('/profile').then(() => window.location.reload());
  }
}
