import { Component } from '@angular/core';
import { AxiosService } from '../../services/axios.service';

@Component({
  selector: 'app-content',
  templateUrl: './content.component.html',
  styleUrl: './content.component.css'
})
export class ContentComponent {
  componentToShow: string = "welcome";
  constructor(private axiosSvc: AxiosService) {}

  showComponent(componentToShow: string): void {
    this.componentToShow = componentToShow;
  }

  // methods
  onLogin(event: any) {
    this.axiosSvc.request(
      "POST",
      "/login",
      event
	  ).then(
		    response => {
		        this.axiosSvc.setAuthToken(response.data.token);
		        this.componentToShow = "messages";
		    }).catch(
		    error => {
		        this.axiosSvc.setAuthToken(null);
		        this.componentToShow = "welcome";
		    }
		);
  }

  onRegister(event: any): void {
    console.log('event: ', event)
		this.axiosSvc.request(
		    "POST",
		    "/register",
		    event
			).then(
		    response => {
		        this.axiosSvc.setAuthToken(response.data.token);
		        this.componentToShow = "messages";
		    }).catch(
		    error => {
		        this.axiosSvc.setAuthToken(null);
		        this.componentToShow = "welcome";
		    }
		);
	}
}
