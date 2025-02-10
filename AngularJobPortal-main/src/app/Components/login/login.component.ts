import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { JobService } from 'src/app/Services/job.service';
import { Token } from 'src/app/models/Token';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  ngOnInit(): void {
    if (localStorage.getItem('token')) {
      this.router.navigateByUrl('/jobs')
  }
  }
  constructor(
    private Service: JobService,
    private router: Router
  ) {}
  loginObj: any = {
    "username": "",
    "password": ""
  }

  login() {
    this.Service.Login(this.loginObj).subscribe((token : Token) => {
      console.log("TOKEN : ",token);

      localStorage.setItem('token', token.accessToken);

      this.Service.getUser(this.loginObj.username).subscribe((response: any) => {
        console.log(response);

        localStorage.setItem('userid', response.applicantId);
        localStorage.setItem('user', response.userType);

        this.router.navigateByUrl('/jobs').then(() => window.location.reload());
      });
      
    }, (error : ErrorEvent) => {
      console.log(error);
      alert("Invalid login credentials");
    })
  }


}
