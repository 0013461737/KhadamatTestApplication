import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { RestCaller } from '../rest-caller'
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent implements OnInit {
  rc : RestCaller;
  token : any ;
  constructor(public hc: HttpClient, private router: Router, private toast: ToastrService) { }

  ngOnInit(): void {
    this.rc = new RestCaller(this.hc);
    this.token = localStorage.getItem("token");
  }

}
