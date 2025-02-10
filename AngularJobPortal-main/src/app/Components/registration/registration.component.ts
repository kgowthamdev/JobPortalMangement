import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { JobService } from 'src/app/Services/job.service';


@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {
  isJobSeeker!: boolean;
  ngOnInit(): void {

  }
  constructor(
    private Service: JobService,
    private router: Router
  ) { }

  // List of Indian cities
  indianCities: string[] = [
    'Mumbai',
    'Delhi',
    'Bangalore',
    'Hyderabad',
    'Ahmedabad',
    'Chennai',
    'Kolkata',
    'Pune',
    'Jaipur',
    'Surat',
    'Lucknow',
    'Kanpur',
    'Nagpur',
    'Patna',
    'Indore'
  ];

  registrationObj : any = {
    "username": '',
    "password": '',
    "userType": '',
    "email": ''
  }

  userProfile : any = {
    "JobSeekerId": 0,
    "firstname": '',
    "lastname": '',
    "email": '',
    "contact": '',
    "location": '',
    "dob": '',
    "description": '',
    "skills": [],
    "resume": ''
  }

  companyProfile: any = {
    "companyId": 0,
    "companyName": "",
    "domain": "",
    "contact": "",
    "location": "",
    "description": ''
  }

  AddNewEmployer() {
    if (this.validateRegistration() && this.validateCompanyProfile()) {
      this.isJobSeeker ? this.registrationObj.userType = 'USER' : this.registrationObj.userType = 'EMPLOYER';
      this.Service.RegisterUser(this.registrationObj).subscribe((response) => {
        console.log(response);
        localStorage.setItem('token', response.accessToken);
        if (response !== null) {
          this.Service.getUser(this.registrationObj.username).subscribe((response: any) => {
            console.log(response);
            this.companyProfile.CompanyId = response.applicantId;

            localStorage.setItem('userid', this.companyProfile.CompanyId);
            localStorage.setItem('user', 'EMPLOYER');

            this.Service.AddNewEmployer(this.companyProfile.CompanyId, this.companyProfile).subscribe((response: any) => {
            console.log(response);
              this.router.navigateByUrl('/jobs').then(() => window.location.reload());
            });
          });
        } else {
          alert("Error AddNewEmployer");
        }
      })
    } else {
      alert("Please fill all required fields for Employer.");
    }
  }

  AddNewJobSeeker() {
    if (this.validateRegistration() && this.validateUserProfile()) {
      this.isJobSeeker ? this.registrationObj.userType = 'USER' : this.registrationObj.userType = 'EMPLOYER';      
      this.Service.RegisterUser(this.registrationObj).subscribe((response: any) => {
        console.log(response);
        localStorage.setItem('token', response.accessToken);
        if (response !== null) {
          this.Service.getUser(this.registrationObj.username).subscribe((response: any) => {
            console.log(response);
            this.userProfile.JobSeekerId = response.applicantId;

            localStorage.setItem('userid', this.userProfile.JobSeekerId);
            localStorage.setItem('user', 'USER');

            this.userProfile.email = this.registrationObj.email;

            this.Service.AddNewJobSeeker(this.userProfile.JobSeekerId, this.userProfile).subscribe((response: any) => {
              console.log(response);
                this.router.navigateByUrl('/jobs').then(() => window.location.reload());
            });
          });
        } else {
          alert("Error AddNewJobSeeker");
        }
      });
    } else {
      alert("Please fill all required fields for Job Seeker.");
    }
  }

  validateRegistration(): boolean {
    return this.registrationObj.username && this.registrationObj.password && this.registrationObj.email;
  }

  validateUserProfile(): boolean {
    return this.userProfile.firstname && 
           this.userProfile.lastname && 
           this.userProfile.contact && 
           this.validatePhoneNumber(this.userProfile.contact);
  }

  validateCompanyProfile(): boolean {
    return this.companyProfile.companyName && 
           this.companyProfile.domain && 
           this.companyProfile.description && 
           this.companyProfile.location && 
           this.validatePhoneNumber(this.companyProfile.contact);
  }

  validatePhoneNumber(phone: string): boolean {
    const phoneRegex = /^\d{10}$/;
    if (!phoneRegex.test(phone)) {
      alert("Contact number must be exactly 10 digits");
      return false;
    }
    return true;
  }

  handleSkillsInput(event: any): void {
    const skillsString = event.target.value;
    this.userProfile.skills = skillsString
      .split(',')
      .map((skill: string) => skill.trim())
      .filter((skill: string) => skill !== '');
  }
}