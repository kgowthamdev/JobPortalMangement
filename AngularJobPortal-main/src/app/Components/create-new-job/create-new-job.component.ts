import { Component, OnInit } from '@angular/core';
import { JobService } from 'src/app/Services/job.service';

@Component({
  selector: 'app-create-new-job',
  templateUrl: './create-new-job.component.html',
  styleUrls: ['./create-new-job.component.css']
})
export class CreateNewJobComponent implements OnInit {
  // List of Indian cities
  Cities: string[] = [
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

  // Job object to hold form data
  createJobObj: any = {
    jobTitle: "",
    skills: [],
    description: "",
    workExperience: "",
    salaryRange: "",
    location: "",
    employmentType: "",
    noApplied: 0,
    postedOn: new Date().toISOString()
  };

  constructor(private jobService: JobService) {
   
  }

  ngOnInit(): void {
   
  }

  // Method to handle job creation
  createNewJob(): void {
    // Validate the form before submitting
    if (this.isFormValid()) {
      const company_id = localStorage.getItem('userid') || '';
      this.jobService.createJobPost(company_id ,this.createJobObj).subscribe(
        (response: any) => {
          console.log("Job creation response:", response);
          if (response.companyId) {
            alert("Job created successfully!");
            this.resetForm();
          } else {
            alert("Failed to create the job. Please try again.");
          }
        },
        (error: any) => {
          console.error("Error creating job:", error);
          alert("An error occurred while creating the job.");
        }
      );
    } else {
      alert("Please fill out all required fields before submitting.");
    }
  }

  // Form validation method
  isFormValid(): boolean {
    return (
      this.createJobObj.jobTitle &&
      this.createJobObj.skills &&
      this.createJobObj.description &&
      this.createJobObj.workExperience &&
      this.createJobObj.salaryRange &&
      this.createJobObj.location &&
      this.createJobObj.employmentType &&
      this.createJobObj.postedOn
    );
  }

  // Add this method to convert comma-separated string to array
  handleSkillsInput(event: any): void {
    const skillsString = event.target.value;
    this.createJobObj.skills = skillsString
      .split(',')
      .map((skill: string) => skill.trim())
      .filter((skill: string) => skill !== '');
  }

  // Update resetForm method
  resetForm(): void {
    this.createJobObj = {
      jobTitle: "",
      skills: [],
      description: "",
      workExperience: "",
      salaryRange: "",
      location: "",
      employmentType: "",
      noApplied: 0,
      postedOn: new Date().toISOString()
    };
  }
}
