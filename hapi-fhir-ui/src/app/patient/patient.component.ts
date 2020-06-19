import { Component, OnInit } from '@angular/core';
import { ApiCallService } from '../api-call.service';
import { ActivatedRoute } from '@angular/router';
import { catchError } from 'rxjs/operators';

@Component({
  selector: 'app-patient',
  templateUrl: './patient.component.html',
  styleUrls: ['./patient.component.css']
})
export class PatientComponent implements OnInit {
  error:String;
  patient:any;
  constructor(apiCall: ApiCallService, route:ActivatedRoute ) { 
    let id=route.snapshot.queryParamMap.get("id");
    apiCall.getPatient(id).pipe(
      catchError(s=>{console.log(s); return this.error=s.status}))
    .subscribe(p=>{console.log(p); return this.patient=p});
  }

  

  ngOnInit(): void {

  }

}
