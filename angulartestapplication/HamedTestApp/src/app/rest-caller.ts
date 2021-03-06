
import { HttpClient, HttpErrorResponse, HttpHeaders , HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';


export class RestCaller {
    params = [];
    constructor (private httpClient : HttpClient){
    }
    private callGetApi(path:string){
        var token;
        try{
            token = localStorage.getItem("token");
        }catch(e){
            token = '';
        }
        let headers = new HttpHeaders({
            'Content-Type' : 'application/json; charset=utf-8',
            'Authorization' : 'Bearer '+token
        });
        return this.httpClient.get('/KHADAMAT/'+path,{'headers':headers});
    }
    private callPostApi(path:string , json:any){
        var token;
        try{
            token = localStorage.getItem("token");
        }catch(e){
            token = '';
        }
        let headers = new HttpHeaders({
            'Content-Type' : 'application/json; charset=utf-8',
            'Authorization' : 'Bearer '+token
        });
        let re = this.httpClient.post('/KHADAMAT/'+path,json,{'headers':headers});
        return re;
    }



    callApi(type:string , path:string , json:any ){
        var result ;
        if (type.toUpperCase() == 'GET'){
            result = this.callGetApi(path);
        }
        else if (type.toUpperCase() == 'POST'){
            result = this.callPostApi(path , json);
        }
        return result ;
    }
}
