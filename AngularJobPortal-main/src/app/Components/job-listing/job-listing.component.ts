import { Component, OnInit } from '@angular/core';
import { JobService } from 'src/app/Services/job.service';

interface Job {
  id: number;
  companyId: number;
  jobTitle: string;
  description: string;
  employmentType: string;
  location: string;
  noApplied: number;
  postedOn: string;
  salaryRange: string;
  skills: string[];
  workExperience: string;
  applications?: Application[];  // Add this to store nested applications
}

interface Application {
  id: number;
  applicantId: number;
  jobId: number;
  status: string;
}

@Component({
  selector: 'app-job-listing',
  templateUrl: './job-listing.component.html',
  styleUrls: ['./job-listing.component.css']
})
export class JobListingComponent implements OnInit {
  userId: any;
  jobApplications: Job[] = [];
  filteredJobApplications: Job[] = [];
  applications: { [key: number]: Application[] } = {};  // Store applications by jobId

  constructor(private Service: JobService) {
    this.userId = localStorage.getItem('userid');
  }

  ngOnInit(): void {
    this.GetAllJobApplcations();
  }

  GetAllJobApplcations() {
    this.Service.GetJobsByCompanyId(this.userId).subscribe((response: any) => {
      console.log(response);
      this.jobApplications = response.jobPosts;

      if (response.jobPosts) {
        response.jobPosts.forEach((job: Job) => {
          this.GetApplcationsByJobId(job.id);
        });
      } else {
        alert("Error get all job applications");
      }
      //this.jobApplications = this.filteredJobApplications;
    });
  }

  GetApplcationsByJobId(jobId: number) {
    this.Service.GetApplcationsByJobId(jobId).subscribe((applications: any) => {
      const job = this.jobApplications.find(j => j.id === jobId);
      if (job) {
        job.applications = applications;
        if (!applications || applications.length === 0) {
          // Remove this specific job if it has no applications
          this.jobApplications = this.jobApplications.filter(j => j.id !== jobId);
        }
      }
    });
  }

  updateApplicationStatus(applicationId: number, id: number, jobId: number,status: string) {
    // Find the application in the nested structure
    let applicationToUpdate: Application | null = null;
    
    for (const job of this.jobApplications) {
      if (job.applications) {
        const foundApp = job.applications.find(app => app.applicantId === applicationId);
        if (foundApp) {
          applicationToUpdate = foundApp;
          break;
        }
      }
    }

    if (applicationToUpdate) {
      // const updateObj = {
      //   id: applicationToUpdate.id,
      //   jobId: applicationToUpdate.jobId,
      //   applicantId: applicationToUpdate.applicantId,
      //   status: status
      // };
      
      const updateObj = {
        id: id,
        jobId: jobId,
        applicantId: applicationId,
        status: status
      };

      this.Service.UpdateJobApplicationStatus(applicationId, updateObj).subscribe(
        (response: any) => {
          console.log('Status updated:', response);
          // Refresh the applications list
          this.GetAllJobApplcations();
        },
        (error) => {
          console.error('Error updating status:', error);
          alert('Failed to update application status');
        }
      );
    } else {
      console.error('Application not found');
      alert('Application not found');
    }
  }
}
