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
  rc: RestCaller;
  token: any;
  flag: boolean = false;
  values: any;
  params: any = [];
  act: boolean = false;
  size: any;
  constructor(public hc: HttpClient, private router: Router, private toast: ToastrService) { }

  ngOnInit(): void {
    this.rc = new RestCaller(this.hc);
    this.token = localStorage.getItem("token");
    
      this.values = this.rc.callApi('GET', 'OrderListFirst', '');
      this.values.forEach((element: any) => {
        this.showValsOnRefresh(element);
      });
    


  }
  goToLogin() {
    this.router.navigateByUrl('/LoginPage', { skipLocationChange: true });
    this.flag = true;


  }
  //==========================================================
  callOrderAdd(name: string, family: string, phone: string, address: string, amount: string, channelId: string, quantity: string,) {
    this.flag = true;
    let apiResult: any;
    var jsonParam = {
      name: name,
      family: family,
      phone: phone,
      address: address,
      amount: amount,
      channelId: channelId,
      quantity: quantity
    };
    
      apiResult = this.rc.callApi('POST', 'Order', jsonParam);
    


    apiResult.forEach((element: any) => {
      this.showResult(element);
    });
  }

  showResult(result: any) {
    this.flag = false;
    var messageId = result.messageId;
    var trakingValue = result.trakingValue;
    if (messageId == '1001') {
      this.toast.warning(trakingValue, ' کد رهگیری شما صادر شد ', {
        timeOut: 50000
      });
      this.toast.success('باموفقیت ثبت گردید');
    }
    else if (messageId == '1002') {
      this.toast.error('با توجه دستگاه، امکان وارد کردن بیش از 1000 تومان میسر نمی باشد');
    }
    else if (messageId == '1003') {
      this.toast.warning(trakingValue, ' کد رهگیری شما صادر شد ', {
        timeOut: 5000
      });
      this.toast.success('باموفقیت ثبت گردید',);
    }
    else if (messageId == '1004') {
      this.toast.error('با توجه دستگاه، امکان وارد کردن بیش از 1000 تومان میسر نمی باشد');
    }
    else {
      this.toast.error('نام کاربری یا رمز عبور اشتباه است', '', {
      });
    }
    this.reloadCurrentRoute();

  }
  //==========================================================
  showValsOnRefresh(result: any) {
    
      this.flag = false;
      var param = [];
      // var orders:Object = [];
      type type = Array<{ amount: string, address: string, quantity: string, phone: string, name: string, id: string, family: string, channelId: string }>;
      var orders: type = [];
      this.size = result[0].ORDER_LIST.length;
      if (this.size > 0 ){
        this.act = true;
      }
      for (var i = 0; i < this.size; i++) {
        param[i] = result[0].ORDER_LIST[i];
      }
      this.params = param;
      

  }

  reloadCurrentRoute() {
    let currentUrl = this.router.url;
    this.router.navigateByUrl('/', {skipLocationChange: true}).then(() => {
        this.router.navigate([currentUrl]);
    });
}
}
