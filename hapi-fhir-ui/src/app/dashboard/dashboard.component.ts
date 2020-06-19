import { Component, OnInit } from '@angular/core';
import { ApiCallService } from '../api-call.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  isLoggedIn:boolean;
  patients: any;
  orginzations: any;
  title = 'hapi-fhir-ui';
  constructor(private apiCall: ApiCallService) {
    this.isLoggedIn = this.apiCall.checkCredentials();

    apiCall.searchPatients().subscribe(res => {
      this.patients = res.entry;
      console.log(this.patients[0]);
    });
  }

  ngOnInit(): void {
  }

}
