import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { JobService } from 'src/app/Services/job.service';

@Component({
  selector: 'app-jobs',
  templateUrl: './jobs.component.html',
  styleUrls: ['./jobs.component.css']
})
export class JobsComponent implements OnInit {

  jobsList : any;
  isUserJobSeeker: boolean = false;
  userId: any;
  showApplied: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private jobService: JobService,
    private Router: Router
  ) {

    this.isUserJobSeeker = localStorage.getItem('user') === 'USER';
    this.userId = localStorage.getItem('userid');
    // Get query params
    this.route.queryParams.subscribe(params => {
      this.showApplied = params['showApplied'] === 'true';
      if (this.showApplied) {
        this.loadAppliedJobs();
      } else {
        this.loadAllJobs();
      }
    });
  }

  ngOnInit(): void {
  }

getjobsbysortedByExperience(){
  const userId = localStorage.getItem('userid');
  if(userId ){
    this.jobService.getjobsSorted(userId).subscribe((application:any)=>{
      
    })
  }
}






  loadAppliedJobs() {
    const userId = localStorage.getItem('userid');
    if (userId) {
      this.jobService.GetAllAppliedJobsByApplicant(userId).subscribe((applications: any) => {
        // Create array of promises for job details
        const jobPromises = applications.map(async (application: any) => {
          const job = await this.jobService.GetJobsById(application.jobId).toPromise();
          return {
            ...job,
            applicationStatus: application.status
          };
        });

        // Wait for all job details to be fetched
        Promise.all(jobPromises).then(jobs => {
          this.jobsList = jobs;
          console.log('Applied jobs with status:', this.jobsList);
        });
      });
    }
  }

  loadAllJobs() {
    this.GetActiveJobListing();
  }

  GetActiveJobListing() {
    // For User
    if (this.isUserJobSeeker) {
      this.jobService.GetActiveJobListing().subscribe((response: any) => {
        console.log(response, "all jobs list");
        this.jobsList = response.jobPosts;
      })
    } else { // For Employer
      const userid = localStorage.getItem('userid');
      this.jobService.GetJobsByCompanyId(userid).subscribe((response: any) => {
        console.log(response, "all jobs list");
        this.jobsList = response.jobPosts;
      })
    }
  }

  navigateToDetails(companyId: any, jobId: any) {
    console.log(companyId);
    this.Router.navigateByUrl(`job-details/${jobId}`, { state: { companyId: companyId } });
  }

}
