import { Component, OnInit } from '@angular/core';
import { AxiosService } from '../../services/axios.service';

@Component({
  selector: 'app-auth-content',
  templateUrl: './auth-content.component.html',
  styleUrl: './auth-content.component.css'
})
export class AuthContentComponent implements OnInit {
  data: string[] = [];

  constructor(private axiosSvc: AxiosService) {}

  ngOnInit(): void {
    this.axiosSvc.request(
      "GET",
      "/messages",
      {}
    ).then(
      (response) => this.data = response.data
    )
    .catch(
      (error) => {
          if (error.response.status === 401) {
              this.axiosSvc.setAuthToken(null);
          } else {
              this.data = error.response.code;
          }

      }
  );
  }
}
