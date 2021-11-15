import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { RestCaller } from '../rest-caller'
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';


@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit {
  rc : RestCaller;
  rs : number;
  flag : boolean;
  posts : any;
  constructor(public hc:HttpClient , private router: Router, private toast: ToastrService) {
  }

  ngOnInit(): void {
    this.rc = new RestCaller(this.hc);
    this.flag = false;
  }
  onSubmit(token:string) {
    localStorage.setItem("token",token);
    this.router.navigateByUrl('/HomePage', { skipLocationChange: true });
  }
  //==========================================================
  callLoginService(username: string, password: string) {
    this.flag = true;
    let apiResult:any;
    var jsonParam = { username: username, password: password };

      apiResult = this.rc.callApi('POST', 'Login', jsonParam);

    apiResult.forEach((element:any) => {
      this.showResult(element);
    });
  }

  showResult(result:any) {
    this.flag = false;
    var token = result.token;
    if (token != 'wrong username or password') {
      this.onSubmit(token);
    } else {
      this.toast.error('نام کاربری یا رمز عبور اشتباه است', '', {
      });
    }

  }
  //==========================================================
  calltestGetservice(path: string) {
    var apiResult:any;
    apiResult = this.rc.callApi('GET', path, '');
    apiResult.forEach((element: any) => {
      console.log(element);
    });
  }


}
