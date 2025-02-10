import { Component, OnInit } from '@angular/core';
import { JobService } from 'src/app/Services/job.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  userId: any;
  userProfile: any;
  isUserJobSeeker: boolean = false;
  editMode: boolean = false;

  constructor(private jobService: JobService, private router: Router) {
    this.isUserJobSeeker = localStorage.getItem('user') === 'USER';
    this.userId = localStorage.getItem('userid');
    this.loadUserProfile(); }

  ngOnInit(): void {
    
  }

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

  loadUserProfile() {
    if (this.isUserJobSeeker) {
      this.jobService.GetUserProfile(this.userId).subscribe(
        (response: any) => {
          // Get first profile from the list
          if (response.profiles && response.profiles.length > 0) {
            this.userProfile = response.profiles[0];
            console.log('User Profile:', this.userProfile);
          }
        }
      );
    } else {
      this.jobService.GetCompanyProfile(this.userId).subscribe(
        (response: any) => {
          // Get first profile from the list
          if (response.companyProfiles && response.companyProfiles.length > 0) {
            this.userProfile = response.companyProfiles[0];
            console.log('Company Profile:', this.userProfile);
          }
        }
      );
    }
  }

  toggleEditMode() {
    this.editMode = !this.editMode;
  }

  saveChanges() {
    // Implement save logic here, e.g., call a service to update the profile
    this.editMode = false;
    if (this.isUserJobSeeker) {
      if (this.validateUserProfile()) {
        this.UpdateJobSeeker();
      } else {
        alert("Please fill all required fields for Job Seeker.");
      }
    } else {
      if (this.validateCompanyProfile()) {
        this.UpdateEmployer();
      } else {
        alert("Please fill all required fields for Employer.");
      }
    }
  }

  UpdateEmployer() {

    this.jobService.UpdateCompanyProfile(this.userProfile.companyId, this.userProfile.id, this.userProfile).subscribe((response: any) => {
      console.log(response);
      window.location.reload();
    });
  } 

  UpdateJobSeeker() {
    this.jobService.UpdateUserProfile(this.userProfile.applicationId, this.userProfile.id, this.userProfile).subscribe((response: any) => {
      console.log(response);
      window.location.reload();
    });
  }

  validateUserProfile(): boolean {
    return this.userProfile.firstname && 
           this.userProfile.lastname && 
           this.userProfile.contact && 
           this.validatePhoneNumber(this.userProfile.contact);
  }

  validateCompanyProfile(): boolean {
    return this.userProfile.companyName && 
           this.userProfile.domain && 
           this.userProfile.description && 
           this.userProfile.location;// && 
           //this.validatePhoneNumber(this.companyProfile.contact);
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