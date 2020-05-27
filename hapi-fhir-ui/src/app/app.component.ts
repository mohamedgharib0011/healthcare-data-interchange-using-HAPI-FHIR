import { Component } from '@angular/core';
import { ApiCallService } from './api-call.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  patients:any;
  title = 'hapi-fhir-ui';
   constructor(private apiCall:ApiCallService){
    apiCall.searchPatients().subscribe(res=>{
      this.patients=res.entry;
      console.log(this.patients[0]);
    });
  }


}
