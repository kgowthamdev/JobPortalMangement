import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class JobService {

  constructor(private Http: HttpClient) { }
  path: any = 'http://localhost:8081/api/v1';

  RegisterUser(Obj: any): Observable<any> {
    return this.Http.post<any>(this.path + '/auth/register', Obj);
  }

  Login(Obj: any): Observable<any> {
    return this.Http.post<any>(this.path + '/auth/login', Obj);
  }

  getUser(username : string): Observable<any> {
    return this.Http.get<any>(this.path + `/auth/user/${username}`);
  }

  AddNewJobSeeker(application_id: string, Obj: any): Observable<any> {
    return this.Http.post<any>(this.path + `/applicants/${application_id}/profiles`, Obj, {
      headers: new HttpHeaders({
        'Authorization': 'Bearer ' + localStorage.getItem('token'),
        'Content-Type': 'application/json'
      })
    });
  }

  AddNewEmployer(company_id: string, Obj: any): Observable<any> {
    return this.Http.post<any>(this.path + `/company/${company_id}/profile`, Obj, {
      headers: new HttpHeaders({
        'Authorization': 'Bearer ' + localStorage.getItem('token'),
        'Content-Type': 'application/json'
      })
    });
  }

  GetUserProfile(application_id: string): Observable<any> {
    return this.Http.get<any>(this.path + `/applicants/${application_id}/profiles`, {
      headers: new HttpHeaders({
        'Authorization': 'Bearer ' + localStorage.getItem('token'),
        'Content-Type': 'application/json'
      })
    });
  }

  GetUserProfileById(id: string): Observable<any> {
    return this.Http.get<any>(this.path + `/applicants/0/profiles/id/${id}`, {
      headers: new HttpHeaders({
        'Authorization': 'Bearer ' + localStorage.getItem('token'),
        'Content-Type': 'application/json'
      })
    });
  }

  GetCompanyProfile(company_id: string): Observable<any> {
    return this.Http.get<any>(this.path + `/company/${company_id}/profile`, {
      headers: new HttpHeaders({
        'Authorization': 'Bearer ' + localStorage.getItem('token'),
        'Content-Type': 'application/json'
      })
    });
  }

  SendJobApplication(applicantId: any, Obj: any): Observable<any> {
    return this.Http.post<any>(this.path + `/applicants/${applicantId}/applications`, Obj, {
      headers: new HttpHeaders({
        'Authorization': 'Bearer ' + localStorage.getItem('token'),
        'Content-Type': 'application/json'
      })
    });
  }

  UpdateJobApplicationStatus(applicantId: any, Obj: any): Observable<any> {
    return this.Http.put<any>(this.path + `/applicants/${applicantId}/applications`, Obj, {
      headers: new HttpHeaders({
        'Authorization': 'Bearer ' + localStorage.getItem('token'),
        'Content-Type': 'application/json'
      })
    });
  }

  UpdateCompanyProfile(company_id: string, profile_id: string, Obj: any): Observable<any> {
    return this.Http.put<any>(this.path + `/company/${company_id}/profile/${profile_id}`, Obj, {
      headers: new HttpHeaders({
        'Authorization': 'Bearer ' + localStorage.getItem('token'),
        'Content-Type': 'application/json'
      })
    });
  }

  UpdateUserProfile(user_id: string, profile_id: string, Obj: any): Observable<any> {
    return this.Http.put<any>(this.path + `/applicants/${user_id}/profiles/${profile_id}`, Obj, {
      headers: new HttpHeaders({
        'Authorization': 'Bearer ' + localStorage.getItem('token'),
        'Content-Type': 'application/json'
      })
    });
  }

  createJobPost(company_id: string, Obj: any): Observable<any> {
    return this.Http.post<any>(this.path + `/companies/${company_id}/job_posts`, Obj, {
      headers: new HttpHeaders({
        'Authorization': 'Bearer ' + localStorage.getItem('token'),
        'Content-Type': 'application/json'
      })
    });
  }

  GetActiveJobListing(): Observable<any> {
    return this.Http.get<any>(this.path + '/companies/jobs', {
      headers: new HttpHeaders({
        'Authorization': 'Bearer ' + localStorage.getItem('token'),
        'Content-Type': 'application/json'
      })
    });
  }

  GetJobListingById(company_id: any, jobId: any): Observable<any> {
    return this.Http.get<any>(this.path + `/companies/${company_id}/job_posts/${jobId}`, {
      headers: new HttpHeaders({
        'Authorization': 'Bearer ' + localStorage.getItem('token'),
        'Content-Type': 'application/json'
      })
    });
  }

  GetJobsByCompanyId(company_id: any): Observable<any> {
    return this.Http.get<any>(this.path + `/companies/${company_id}/job_posts`, {
      headers: new HttpHeaders({
        'Authorization': 'Bearer ' + localStorage.getItem('token'),
        'Content-Type': 'application/json'
      })
    });
  }

  GetJobsById(jobId: any): Observable<any> {
    return this.Http.get<any>(this.path + `/companies/job_posts/${jobId}`, {
      headers: new HttpHeaders({
        'Authorization': 'Bearer ' + localStorage.getItem('token'),
        'Content-Type': 'application/json'
      })
    });
  }

  GetAllAppliedJobsByApplicant(applicantId: any): Observable<any> {
    return this.Http.get<any>(this.path + `/applicants/${applicantId}/applications`, {
      headers: new HttpHeaders({
        'Authorization': 'Bearer ' + localStorage.getItem('token'),
        'Content-Type': 'application/json'
      })
    });
  }

  GetApplcationsByJobId(jobId: any): Observable<any> {
      return this.Http.get<any>(this.path + `/applicants/0/applications/${jobId}`, {
        headers: new HttpHeaders({
          'Authorization': 'Bearer ' + localStorage.getItem('token'),
          'Content-Type': 'application/json'
        })
      });
    }

  GetAllJobCategory(): Observable<any> {
    return this.Http.get<any>(this.path + 'GetAllJobCategory');
  }

  DeleteJob(company_id: string, job_id: string): Observable<any> {
    return this.Http.delete<any>(this.path + `/companies/${company_id}/job_posts/${job_id}`, {
      headers: new HttpHeaders({
        'Authorization': 'Bearer ' + localStorage.getItem('token'),
        'Content-Type': 'application/json'
      })
    });
  }

  getjobsSorted(userid:string): Observable<any> {
    return this.Http.get<any>('/api/v1/applicants/{applicantId}/applications', {
      headers: new HttpHeaders({
        'Authorization': 'Bearer ' + localStorage.getItem('token'),
        'Content-Type': 'application/json'
      })
    });}

}
