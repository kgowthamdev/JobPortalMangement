import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { JobService } from 'src/app/Services/job.service';

@Component({
  selector: 'app-job-details',
  templateUrl: './job-details.component.html',
  styleUrls: ['./job-details.component.css']
})
export class JobDetailsComponent implements OnInit {

  jobId: any;
  jobList: any;
  isUserJobSeeker: any;
  userId : any;
  companyId: any;
  isApplied: boolean = false;
  sendjobObj : any = {
    "jobId":'',
    "status": ''
  }
  isUserEmployer: boolean = false;

  constructor(
    private Service: JobService,
    private ActivateRoute: ActivatedRoute,
    private router: Router
  ) {
    // Get navigation state
    const navigation = this.router.getCurrentNavigation();
    if (navigation?.extras?.state) {
      this.companyId = navigation.extras.state['companyId'];
      console.log('CompanyId in constructor:', this.companyId);
    }

    // Get route params
    this.ActivateRoute.params.subscribe((response: any) => {
      console.log('Route params:', response);
      this.jobId = response.jobId;
    });

    this.isUserEmployer = localStorage.getItem('user') === 'EMPLOYER';
  }

  ngOnInit(): void {
    this.isUserJobSeeker = localStorage.getItem('user') === 'USER';
    this.userId = localStorage.getItem('userid');
    this.jobId = this.router.url.split('/').pop(); // Extract jobId from URL
    this.GetJobListingById();
    this.IsJobApplied();
  }

  GetJobListingById() {
    this.Service.GetJobListingById(this.companyId, this.jobId).subscribe((response: any) => {
      console.log(response);
      this.jobList = response;
    })
  }

  IsJobApplied() {
    this.Service.GetAllAppliedJobsByApplicant(this.userId).subscribe((response: any) => {
      console.log(response);
      // Check if any application exists for current job
      this.isApplied = response.some((application: any) => 
        application.jobId.toString() === this.jobId.toString()
      );
    });
  }

  SendJobApplication(){
    this.sendjobObj.jobId = this.jobId;
    this.sendjobObj.status = 'PENDING';

    this.Service.SendJobApplication(this.userId, this.sendjobObj).subscribe((response:any)=>{
      console.log(response);
      if(response.id){
        this.isApplied = true;
        alert("Success AppliedJob");
      }else{
        alert("Error appliedJob")
      }
    })
  }

  deleteJob() {
    if (confirm('Are you sure you want to delete this job?')) {
      if (this.companyId && this.jobId) {
        this.Service.DeleteJob(this.companyId, this.jobId).subscribe(
          (response: any) => {
            console.log('Job deleted:', response);
            alert('Job deleted successfully');
            this.router.navigate(['/jobs']);
          },
          (error) => {
            console.error('Error deleting job:', error);
            alert('Failed to delete job');
          }
        );
      }
    }
  }

}
